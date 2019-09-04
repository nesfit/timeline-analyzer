import { Rdf4jService } from '../rdf4j.service';
import { SharedService } from '../shared.service';
import { Event } from '../model/event';
import { Timeline } from '../model/timeline';
import { Component, OnInit } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Network, DataSet, Node, Edge, IdType, Timeline as TL, DataGroup, DataItem } from 'vis';
import { TAObject } from '../model/taobject';
import { Router } from '@angular/router';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  // all events based on the selection (uri => event)
  allEvents: Map<string, Event>;
  // loaded dates
  loadfromdate: NgbDateStruct;
  loadtodate: NgbDateStruct;
  // shown dates
  fromdate: NgbDateStruct;
  todate: NgbDateStruct;
  // used time lines
  timelines: Timeline[];
  // selected time lines
  selected: Timeline[];
  mindate: Date;
  maxdate: Date;
  dateInitialized = false;
  // displated time line and its data
  tlview: TL;
  tldata: DataSet<DataItem>;
  tlgroups: DataSet<DataGroup>;
  tloptions: any;
  selectedEvent: Event;
  eventObjects: TAObject[];
  // filtered resource
  filteredResources: string[];

  constructor(private rdf: Rdf4jService, private shared: SharedService) { }

  ngOnInit() {
    const now = new Date();
    const before = new Date();
    before.setDate(before.getDate() - 7); // 7 days before
    this.allEvents = new Map();
    this.loadfromdate = { day: before.getUTCDate(), month: before.getUTCMonth() + 1, year: before.getUTCFullYear()};
    this.loadtodate = { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
    this.fromdate = { day: before.getUTCDate(), month: before.getUTCMonth() + 1, year: before.getUTCFullYear()};
    this.todate = { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
    this.timelines = [];
    this.selected = [];
    this.mindate = new Date();
    this.maxdate = new Date();
    this.rdf.getTimelines().subscribe(data => this.setTimelines(data));
    this.createTimeline();
    this.shared.timeline = this;
    this.selectedEvent = null;
    this.eventObjects = [];
    this.filteredResources = [];
  }

  /**
   * Initializes the time line.
   */
  createTimeline() {
    const container = document.getElementById('visualization');
    while (container.firstChild) {
      container.removeChild(container.firstChild);
    }

    // Create a DataSet (allows two way data-binding)
    this.tldata = new DataSet([
        {id: 1, group: 1, content: 'item 1', start: '2018-01-20'},
        {id: 2, group: 1, content: 'item 2', start: '2018-01-14'},
        {id: 3, group: 1, content: 'item 3', start: '2018-01-18'},
        {id: 4, group: 1, content: 'item 4', start: '2018-01-16', end: '2013-04-19'},
        {id: 5, group: 1, content: 'item 5', start: '2018-01-25'},
        {id: 6, group: 1, content: 'item 6', start: '2018-01-27'}
    ]);
    this.tlgroups = new DataSet([/*{id: 1, content: 'all'}*/]);

    // Configuration for the Timeline
    this.tloptions = {
      orientation: 'both',
      type: 'point', // point, box, range
      stack: false
    };

    // Create a Timeline
    this.tlview = new TL(container, this.tldata, this.tlgroups, this.tloptions);
    const view = this;
    this.tlview.on('select', function(properties) {
      view.displayEvent(properties);
    });
  }

  /**
   * Fills the list of available time lines.
   */
  setTimelines(data: Timeline[]) {
    this.timelines = data;
    let selcnt = this.timelines.length;
    if (selcnt > 4) {
      selcnt = 4; // start with at most 4 timelines
    }
    for (let i = 0; i < selcnt; i++) {
      this.timelines[i].selected = true;
    }
    this.updateSelected();
    for (let i = 0; i < selcnt; i++) {
      this.addTimeLine(this.timelines[i]);
    }
  }

  selectTimelinesByUris(uris: string[]): void {
    for (const t of this.timelines) {
      const old = t.selected;
      t.selected = (uris.includes(t.uri));
      if (t.selected !== old) {
        this.updateTimeLine(t, t.selected);
      }
    }
    this.updateSelected();
  }

  displayEvent(properties: any): void {
    const uri = properties.items[0];
    console.log('showing ' + uri);
    this.selectedEvent = this.allEvents.get(uri);
    this.rdf.getReferredObjects(uri).subscribe(data => this.displayContents(data));
  }

  displayContents(data: TAObject[]): void {
    this.eventObjects = data;
    console.log('shown ');
    console.log(data);
  }

  setFilteredResources(filteredResources: string[]): void {
    this.filteredResources = filteredResources;
  }

  clearFilteredResources(): void {
    this.filteredResources = [];
  }

  showURL(url: string): void {
    console.log('show ' + url);
  }

  selectEvent(e: Event): void {
    console.log('Select ' + e.uri);
    console.log(this.tlview);
    const found = this.tldata.get([e.uri]);
    console.log(found);
    if (found !== null) {
      this.tlview.setSelection(e.uri, {focus: true, animation: {}});
    }
  }

  // ======================================================================================

  /**
   * Called when a timeline is selected or deselected
   */
  changeSelection(t: Timeline, newstate: boolean) {
    // console.log('SEL ' + t.sourceId + ' state ' + newstate);
    this.updateSelected();
    this.updateTimeLine(t, newstate);
  }

  /**
   * Updates the list of selected timelines
   */
  updateSelected(): Timeline[] {
    this.selected = [];
    for (const t of this.timelines) {
      if (t.selected) {
        this.selected.push(t);
      }
    }
    const parent = this;
    this.rdf.getMinDate(this.selected).subscribe(date => this.mindate = date);
    this.rdf.getMaxDate(this.selected).subscribe(function(date) {
      parent.maxdate = date;
      if (!parent.dateInitialized) {
        parent.dateInitialized = true;
        parent.initSpans();
      }
    });
    return this.selected;
  }

  /**
   * Updates the timeline data according to the selection parametres.
   * @param t the selected timeline or null for updating all
   */
  updateTimeLine(t: Timeline, newstate: boolean) {
    // TODO: caching?
    if (t != null) {
      if (newstate) {
        this.addTimeLine(t);
      } else {
        this.removeTimeLine(t);
      }
    }
  }

  addTimeLine(t: Timeline) {
    // add group
    this.tlgroups.add({id: t.sourceId, content: t.label});
    // add entries
    if (this.dateInitialized) {
      if (this.filteredResources.length === 0) {
        this.reloadTimeline(t);
      } else {
        // this.rdf.getEntriesForResource(t, null, 500, this.filteredResources[0]).subscribe(data => this.addEntries(t, data));
      }
    }
  }

  reloadTimeline(t: Timeline) {
    this.rdf.getEvents(t, this.toDate(this.loadfromdate), this.toDate(this.loadtodate), 1000).subscribe(data => this.addEvents(t, data));
  }

  addEvents(t: Timeline, data: Event[]) {
    console.log('adding ' + data.length + ' events');
    for (let i = 0; i < data.length; i++) {
      const e = data[i];
      const text = '<small>' + e.timestamp.toLocaleDateString() + ' ' + e.label + '</small>';
      this.tldata.add({id: e.uri, group: t.sourceId, content: '', title: text, start: e.timestamp.toISOString() });
      this.allEvents.set(e.uri, e);
    }
    // this.tldata.flush();
  }

  removeTimeLine(t: Timeline) {
    this.tlgroups.remove(t.sourceId);
  }

  reloadData(): void {
    this.allEvents = new Map();
    this.createTimeline();
    for (const t of this.selected) {
      this.addTimeLine(t);
    }
  }

  // ======================================================================================

  useMinDate() {
    this.fromdate = this.fromDate(this.mindate);
    this.updateSpan();
  }

  useMaxDate() {
    this.todate = this.fromDate(this.maxdate);
    this.updateSpan();
  }

  /**
   * Initialize the spans based on the maxdate and mindate -- used after the first load
   */
  initSpans() {
    console.log('init spans');
    const dto = new Date(this.maxdate);
    const dfrom = new Date(dto);
    dfrom.setDate(dfrom.getDate() - 7);
    this.loadfromdate = this.fromDate(dfrom);
    this.loadtodate = this.fromDate(dto);
    this.fromdate = this.fromDate(dfrom);
    this.todate = this.fromDate(dto);
    this.loadSpan();
  }

  loadSpan() {
    console.log('load span');
    this.reloadData();
    this.updateSpan();
    /*this.tloptions.start = this.toDate(this.fromdate);
    this.tloptions.end = this.toDate(this.todate);
    this.tlview.setOptions(this.tloptions);*/
  }

  updateSpan() {
    console.log('update span');
    this.tloptions.start = this.toDate(this.fromdate);
    this.tloptions.end = this.toDate(this.todate);
    this.tlview.setOptions(this.tloptions);
  }

  toDate(date: NgbDateStruct): Date {
    return new Date(date.year, date.month - 1, date.day);
  }

  fromDate(now: Date) {
    return { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
  }

}
