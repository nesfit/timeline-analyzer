import { Rdf4jService } from '../rdf4j.service';
import { SharedService } from '../shared.service';
import { Entry } from '../timeline/entry';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  // url search
  urlPrefixes: string[];
  urlFilter: string;
  urlPrefix: string;
  urls: string[];
  selectedUrl: string;

  // entry display
  entries: Entry[];
  timelineUris: number[]; // indices by uris
  timelineIds: string[]; // IDs by index
  timelineLabels: string[]; // labels by index
  contents: any[];

  constructor(private rdf: Rdf4jService, private shared: SharedService) {}

  ngOnInit() {
    this.rdf.getURLPrefixes().subscribe(data => this.urlPrefixes = data);
    this.urlFilter = '';
    this.urlPrefix = '*';
    this.timelineUris = [];
    this.timelineIds = [];
    this.timelineLabels = [];
  }

  filterChanged(): void {
    if (this.urlFilter.length >= 1) {
      console.log('try');
      this.rdf.getURLsFiltered(this.urlPrefix, this.urlFilter).subscribe(data => this.urls = data);
    } else {
      this.urls = [];
    }
  }

  showURL(url: string): void {
    this.selectedUrl = url;
    this.rdf.getEntriesForURL(url).subscribe(data => this.showEntries(data));
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
      const uri = entries[i].sourceTimeline.uri;
      if (this.timelineUris[uri] === undefined) {
        this.timelineIds[last] = entries[i].sourceTimeline.uri;
        this.timelineLabels[last] = entries[i].sourceTimeline.label;
        this.timelineUris[uri] = last++;
      }
    }
    console.log(this.timelineUris);
    console.log(this.timelineLabels);
    // create contents
    this.contents = [];
    for (let i = 0; i < entries.length; i++) {
      const cont = {col: 0, time: null, contains: []};
      cont.col = this.timelineUris[entries[i].sourceTimeline.uri];
      cont.time = entries[i].timestamp;
      this.rdf.getContentsForEntry(entries[i]).subscribe(data => cont.contains = data);
      this.contents.push(cont);
    }
  }

  showInTimeline(): void {
    console.log('show');
    console.log(this.timelineIds);
    console.log(this.shared.timeline.selected);
    this.shared.timeline.selectTimelinesByUris(this.timelineIds);
  }

}
