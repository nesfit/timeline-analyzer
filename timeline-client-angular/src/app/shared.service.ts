import { TimelineComponent } from './timeline/timeline.component';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  timeline: TimelineComponent;

  constructor() { }
}
