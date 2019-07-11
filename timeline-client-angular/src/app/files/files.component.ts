import { Component, OnInit } from '@angular/core';
import { Entry } from '../model/entry';
import { Event } from '../model/event';
import { Rdf4jService } from '../rdf4j.service';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-files',
  templateUrl: './files.component.html',
  styleUrls: ['./files.component.css']
})
export class FilesComponent implements OnInit {

  // file search
  nameFilter: string;
  pathPrefix: string;
  paths: string[];
  selectedPath: string;

  // event display
  events: Event[];

  // entry display
  entries: Entry[];
  timelineUris: number[]; // indices by uris
  timelineIds: string[]; // IDs by index
  timelineLabels: string[]; // labels by index
  contents: any[];

  constructor(private rdf: Rdf4jService, private shared: SharedService) {}

  ngOnInit() {
    this.shared.files = this;
    this.events = [];
    this.nameFilter = '';
    this.pathPrefix = '';
    this.timelineUris = [];
    this.timelineIds = [];
    this.timelineLabels = [];
    if (this.shared.pathToShow !== null) {
      this.showFile(this.shared.pathToShow);
    }
  }

  filterChanged(): void {
    if (this.pathPrefix.length >= 1 || this.nameFilter.length >= 1) {
      console.log('try');
      this.rdf.getPathsFiltered(this.pathPrefix, this.nameFilter).subscribe(data => this.paths = data);
    } else {
      this.paths = [];
    }
  }

  showFile(path: string): void {
    this.selectedPath = path;
    this.events = [];
    this.rdf.getResourcesForPath(path).subscribe(data => this.showResources(data));
    this.shared.pathToShow = null;
  }

  showResources(uris: string[]): void {
    console.log(uris);
    for (let i = 0; i < uris.length; i++) {
      this.rdf.getEventsForObject(uris[i]).subscribe(data => this.addEvents(data));
    }
  }

  addEvents(data: Event[]): void {
    for (let j = 0; j < data.length; j++) {
          this.events.push(data[j]);
    }
  }

  showEntries(entries: Entry[]): void {
    this.entries = entries;
    console.log(entries);
    // find different timelines
    this.timelineUris = [];
    this.timelineIds = [];
    this.timelineLabels = [];
    let last = 0;
    for (let i = 0; i < entries.length; i++) {
      /*const uri = entries[i].sourceTimeline.uri;
      if (this.timelineUris[uri] === undefined) {
        this.timelineIds[last] = entries[i].sourceTimeline.uri;
        this.timelineLabels[last] = entries[i].sourceTimeline.label;
        this.timelineUris[uri] = last++;
      }*/
    }
    console.log(this.timelineUris);
    console.log(this.timelineLabels);
    // create contents
    this.contents = [];
    for (let i = 0; i < entries.length; i++) {
      /*const cont = {col: 0, time: null, contains: []};
      cont.col = this.timelineUris[entries[i].sourceTimeline.uri];
      cont.time = entries[i].timestamp;
      this.rdf.getContentsForEntry(entries[i]).subscribe(data => cont.contains = data);
      this.contents.push(cont);*/
    }
  }

  showInTimeline(): void {
    console.log('show');
    /*console.log(this.timelineIds);
    console.log(this.shared.timeline.selected);
    this.shared.timeline.setFilteredResources([this.selectedUrl]);
    this.shared.timeline.selectTimelinesByUris(this.timelineIds);*/
  }

}
