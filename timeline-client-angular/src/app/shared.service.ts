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

  urlToShow: string;
  pathToShow: string;

  constructor() {
    this.timeline = null;
    this.files = null;
    this.resources = null;
    this.urlToShow = null;
    this.pathToShow = null;
  }

  showURL(url: string): void {
    this.urlToShow = url;
    if (this.resources !== null) {
      this.resources.showURL(url);
    }
  }

  showFile(path: string): void {
    this.pathToShow = path;
    if (this.files != null) {
      this.files.showFile(path);
    }
  }

}
