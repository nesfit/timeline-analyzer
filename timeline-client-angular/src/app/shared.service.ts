import { TimelineComponent } from './timeline/timeline.component';
import { Injectable } from '@angular/core';
import { FilesComponent } from './files/files.component';
import { ResourcesComponent } from './resources/resources.component';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  timeline: TimelineComponent;
  files: FilesComponent;
  resources: ResourcesComponent;

  constructor() { }
}
