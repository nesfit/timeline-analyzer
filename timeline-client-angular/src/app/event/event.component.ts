import { Component, OnInit, Input } from '@angular/core';
import { Event } from '../model/event';
import { TAObject } from '../model/taobject';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  @Input() event: Event;
  @Input() eventObjects: TAObject[];

  constructor() { }

  ngOnInit() {
  }

}
