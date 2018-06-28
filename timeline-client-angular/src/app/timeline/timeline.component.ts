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

  ngOnInit() {
    this.rdf.getTimelines().subscribe(data => this.timelines = data);
  }

  constructor(private rdf: Rdf4jService) { }

}
