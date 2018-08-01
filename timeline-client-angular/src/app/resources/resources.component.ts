import { Rdf4jService } from '../rdf4j.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.css']
})
export class ResourcesComponent implements OnInit {

  urlPrefixes: string[];
  urlFilter: string;
  urlPrefix: string;
  urls: string[];
  
  constructor(private rdf: Rdf4jService) {}

  ngOnInit() {
    this.rdf.getURLPrefixes().subscribe(data => this.urlPrefixes = data);
    this.urlFilter = '';
    this.urlPrefix = '';
  }

  filterChanged(): void {
    console.log('changed! ' + this.urlFilter + ' ' + this.urlPrefix);
    if (this.urlFilter.length >= 1) {
      console.log('try');
      this.rdf.getURLsFiltered(this.urlPrefix, this.urlFilter).subscribe(data => this.urls = data);
    } else {
      this.urls = [];
    }
  }
  
}
