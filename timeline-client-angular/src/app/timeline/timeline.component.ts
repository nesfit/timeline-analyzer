import { Rdf4jService } from '../rdf4j.service';
import { Timeline } from './timeline';
import { Component, OnInit } from '@angular/core';
import { Network, DataSet, Node, Edge, IdType, Timeline as TL, DataGroup, DataItem } from 'vis';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  timelines: Timeline[];
  selected: Timeline[];
  tlview: TL;
  tldata: DataSet<DataItem>;
  tlgroups: DataSet<DataGroup>;

  ngOnInit() {
    this.timelines = [];
    this.selected = [];
    this.rdf.getTimelines().subscribe(data => this.setTimelines(data));
    this.createTimeline();
  }

  constructor(private rdf: Rdf4jService) { }

  updateSelected(): Timeline[] {
    this.selected = [];
    for (const t of this.timelines) {
      if (t.selected) {
        this.selected.push(t);
      }
    }
    return this.selected;
  }

  changeSelection() {
    console.log('SEL');
    this.updateSelected();
    this.updateTimeLine();
  }

  setTimelines(data: Timeline[]) {
    this.timelines = data;
    this.changeSelection();
  }

  createTimeline() {
    const container = document.getElementById('visualization');

    // Create a DataSet (allows two way data-binding)
    this.tldata = new DataSet([
        {id: 1, group: 1, content: 'item 1', start: '2013-04-20'},
        {id: 2, group: 1, content: 'item 2', start: '2013-04-14'},
        {id: 3, group: 1, content: 'item 3', start: '2013-04-18'},
        {id: 4, group: 1, content: 'item 4', start: '2013-04-16', end: '2013-04-19'},
        {id: 5, group: 1, content: 'item 5', start: '2013-04-25'},
        {id: 6, group: 1, content: 'item 6', start: '2013-04-27'}
    ]);
    this.tlgroups = new DataSet([{id: 1, content: 'all'}]);

    // Configuration for the Timeline
    const options = {};

    // Create a Timeline
    this.tlview = new TL(container, this.tldata, this.tlgroups, options);

  }

  updateTimeLine() {
    const iid = this.tldata.length + 100;
    console.log('adding ' + iid);
    this.tldata.add({id: iid, group: 1, content: 'XXX', start: '2013-04-' + (iid - 90)});
    // TODO: create groups from selected time lines, add the data (caching?)
  }

}
