import { Rdf4jService } from '../rdf4j.service';
import { Entry } from '../timeline/entry';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  //url search
  urlPrefixes: string[];
  urlFilter: string;
  urlPrefix: string;
  urls: string[];
  
  //entry display
  entries: Entry[];
  
  constructor(private rdf: Rdf4jService) {}

  ngOnInit() {
    this.rdf.getURLPrefixes().subscribe(data => this.urlPrefixes = data);
    this.urlFilter = '';
    this.urlPrefix = '*';
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
    this.rdf.getEntriesForURL(url).subscribe(data => this.showEntries(data));
  }
  
  showEntries(entries: Entry[]): void {
    this.entries = entries;
    console.log(entries);
  }
  
}
