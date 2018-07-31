import { Rdf4jService } from '../rdf4j.service';
import { Entry } from './entry';
import { Timeline } from './timeline';
import { Component, OnInit } from '@angular/core';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Network, DataSet, Node, Edge, IdType, Timeline as TL, DataGroup, DataItem } from 'vis';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  // selected dates
  fromdate: NgbDateStruct;
  todate: NgbDateStruct;
  // used time lines
  timelines: Timeline[];
  // selected time lines
  selected: Timeline[];
  mindate: Date;
  maxdate: Date;
  // displated time line and its data
  tlview: TL;
  tldata: DataSet<DataItem>;
  tlgroups: DataSet<DataGroup>;
  tloptions: any;

  ngOnInit() {
    const now = new Date();
    this.fromdate = { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
    this.todate = { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
    this.timelines = [];
    this.selected = [];
    this.mindate = new Date();
    this.maxdate = new Date();
    this.rdf.getTimelines().subscribe(data => this.setTimelines(data));
    this.createTimeline();
  }

  constructor(private rdf: Rdf4jService) { }

  /**
   * Initializes the time line.
   */
  createTimeline() {
    const container = document.getElementById('visualization');

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
      type: 'box', // point, box, range
      stack: false
    };

    // Create a Timeline
    this.tlview = new TL(container, this.tldata, this.tlgroups, this.tloptions);

  }

  /**
   * Fills the list of available time lines.
   */
  setTimelines(data: Timeline[]) {
    this.timelines = data;
    const selcnt = 5;
    for (let i = 0; i < selcnt; i++) {
      this.timelines[i].selected = true;
    }
    this.updateSelected();
    for (let i = 0; i < selcnt; i++) {
      this.addTimeLine(this.timelines[i]);
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
    this.rdf.getMinDate(this.selected).subscribe(date => this.mindate = date);
    this.rdf.getMaxDate(this.selected).subscribe(date => this.maxdate = date);
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
    this.rdf.getEntries(t, null, 500).subscribe(data => this.addEntries(t, data));
  }

  addEntries(t: Timeline, data: Entry[]) {
    console.log('adding ' + data.length + ' entries');
    for (let i = 0; i < data.length; i++) {
      const e = data[i];
      const text = '<small>' + e.timestamp.toLocaleDateString() + '</small>';
      this.tldata.add({id: e.sourceId, group: t.sourceId, content: text, start: e.timestamp.toISOString() });
    }
    // this.tldata.flush();
  }

  removeTimeLine(t: Timeline) {
    this.tlgroups.remove(t.sourceId);
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

  updateSpan() {
    console.log('update span');
    this.tloptions.start = this.toDate(this.fromdate);
    this.tloptions.end = this.toDate(this.todate);
    this.tlview.setOptions(this.tloptions);
  }

  toDate(date: NgbDateStruct) {
    return new Date(date.year, date.month - 1, date.day);
  }

  fromDate(now: Date) {
    return { day: now.getUTCDate(), month: now.getUTCMonth() + 1, year: now.getUTCFullYear()};
  }

}
