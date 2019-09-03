import { Component, OnInit, Input } from '@angular/core';
import { Event } from '../model/event';
import { TAObject } from '../model/taobject';
import { Rdf4jService } from '../rdf4j.service';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css']
})
export class EventComponent implements OnInit {

  @Input() event: Event;
  @Input() eventObjects: TAObject[] = null;

  constructor(private rdf: Rdf4jService, private shared: SharedService) { }

  ngOnInit() {
    if (this.eventObjects === null) {
      this.rdf.getReferredObjects(this.event.uri).subscribe(data => this.eventObjects = data);
    }
  }

  selectEvent() {
    console.log('Selecting ' + this.event.uri);
    this.shared.timeline.selectEvent(this.event);
  }

}
