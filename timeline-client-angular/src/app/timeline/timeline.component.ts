import { Rdf4jService } from '../rdf4j.service';
import { Timeline } from './timeline';
import { Component, OnInit } from '@angular/core';
import { Network, DataSet, Node, Edge, IdType, Timeline as TL } from 'vis';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  timelines: Timeline[];
  selected: Timeline[];
  tlview: TL;

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
  }

  setTimelines(data: Timeline[]) {
    this.timelines = data;
    this.changeSelection();
  }

  createTimeline() {
    const container = document.getElementById('visualization');

    // Create a DataSet (allows two way data-binding)
    const items = new DataSet([
        {id: 1, content: 'item 1', start: '2013-04-20'},
        {id: 2, content: 'item 2', start: '2013-04-14'},
        {id: 3, content: 'item 3', start: '2013-04-18'},
        {id: 4, content: 'item 4', start: '2013-04-16', end: '2013-04-19'},
        {id: 5, content: 'item 5', start: '2013-04-25'},
        {id: 6, content: 'item 6', start: '2013-04-27'}
    ]);

    // Configuration for the Timeline
    const options = {};

    // Create a Timeline
    this.tlview = new TL(container, items, options);

  }
  
}
