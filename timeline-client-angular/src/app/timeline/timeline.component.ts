import { Rdf4jService } from '../rdf4j.service';
import { Timeline } from './timeline';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  timelines: Timeline[];
  selected: Timeline[];

  ngOnInit() {
    this.timelines = [];
    this.selected = [];
    this.rdf.getTimelines().subscribe(data => this.setTimelines(data));
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

}
