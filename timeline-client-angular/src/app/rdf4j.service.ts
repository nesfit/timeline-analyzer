import { Entry } from './timeline/entry';
import { Timeline } from './timeline/timeline';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

const httpOptionsQuery = {
  headers: new HttpHeaders({
    'Content-Type': 'application/sparql-query',
    'Accept': 'application/sparql-results+json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class Rdf4jService {

  private ns = {
      ta: 'http://nesfit.github.io/ontology/ta.owl#',
      rdf: 'http://www.w3.org/1999/02/22-rdf-syntax-ns#',
      rdfs: 'http://www.w3.org/2000/01/rdf-schema#',
      tares: 'http://nesfit.github.io/resource/ta#'
    };

  private endpointUrl = 'http://localhost:8080/rdf4j-server';
  private repositoryName = 'test';

  constructor(private http: HttpClient) { }

  query(query: string): Observable<any[]> {
    const url = this.getRepositoryUrl();
    return this.http.post(url, query, httpOptionsQuery).pipe(map(res => this.bindingsToArray(res)));
  }

  getTimelines(): Observable<Timeline[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes()
      + 'SELECT ?uri ?sourceId ?label WHERE {?uri rdf:type ta:Timeline . ?uri ta:sourceId ?sourceId . ?uri rdfs:label ?label}';
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToTimelines(res)));
  }

  getEntries(t: Timeline, startDate: Date, limit: number): Observable<Entry[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes()
      + 'SELECT ?uri ?time ?id ?label'
      + ' WHERE { ?uri ta:timestamp ?time . ?uri rdf:type ta:Entry . ?uri ta:sourceTimeline <' + t.uri + '>'
      + ' . ?uri ta:sourceId ?id'
      + ' . OPTIONAL { ?uri rdfs:label ?label } }'
      + ' ORDER BY ASC(?time)'
      + ' LIMIT ' + limit;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToEntries(res, t)));
  }

  // =========================================================================

  private getRepositoryUrl(): string {
    return this.endpointUrl + '/repositories/' + this.repositoryName;
  }

  private getPrefixes(): string {
    let ret = '';
    for (const ns in this.ns) {
      if (this.ns.hasOwnProperty(ns)) {
        ret += 'PREFIX ' + ns + ': <' + this.ns[ns] + '>\n';
      }
    }
    return ret;
  }

  // =========================================================================

  private bindingsToTimelines(res): Timeline[] {
    const bindings = res.results.bindings;
    const ret = new Array<Timeline>();
    console.log(bindings);
    for (let i = 0; i < bindings.length; i++) {
      const item = bindings[i];
      const newitem = new Timeline();
      newitem.label = item.label.value;
      newitem.sourceId = item.sourceId.value;
      newitem.uri = item.uri.value;
      ret.push(newitem);
    }
    return ret;
  }

  private bindingsToEntries(res, src: Timeline): Entry[] {
    const bindings = res.results.bindings;
    const ret = new Array<Entry>();
    console.log(bindings);
    for (let i = 0; i < bindings.length; i++) {
      const item = bindings[i];
      const newitem = new Entry();
      newitem.uri = item.uri.value;
      newitem.sourceTimeline = src;
      newitem.timestamp = new Date(item.time.value);
      ret.push(newitem);
    }
    return ret;
  }

  private bindingsToArray(res): any[] {
    const bindings = res.results.bindings;
    // console.log('RESULTS');
    // console.log(bindings);
    const ret = [];
    for (let i = 0; i < bindings.length; i++) {
      const item = bindings[i];
      const newitem = {};
      for (const prop in item) {
        if (item.hasOwnProperty(prop)) {
          let value;
          if (item[prop].type === 'uri') {
            value = {uri: item[prop].value};
          } else {
            value = item[prop].value;
          }
          newitem[prop] = value;
        }
      }
      ret.push(newitem);
    }
    return ret;
  }

}
