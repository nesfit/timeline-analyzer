import { Entry } from './model/entry';
import { Event } from './model/event';
import { Timeline } from './model/timeline';
import { HttpHeaders } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { TAObject } from './model/taobject';
import { LocalFile } from './model/localfile';
import { WebResource } from './model/webresource';

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
  private repositoryName = 'test2';

  constructor(private http: HttpClient) { }

  query(query: string): Observable<any[]> {
    const url = this.getRepositoryUrl();
    return this.http.post(url, query, httpOptionsQuery).pipe(map(res => this.bindingsToArray(res)));
  }

  getTimelines(): Observable<Timeline[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes() +
      `SELECT DISTINCT ?uri ?sourceId ?label
       WHERE {
           ?uri rdf:type ta:Timeline .
           ?uri ta:sourceId ?sourceId .
           ?uri rdfs:label ?label
        }`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToTimelines(res)));
  }

  getMinDate(t: Timeline[]): Observable<Date> {
    const url = this.getRepositoryUrl();
    let values = 'VALUES ?src {';
    for (let i = 0; i < t.length; i++) {
      values += '<' + t[i].uri + '> ';
    }
    values += '}';
    const q = this.getPrefixes() +
        `SELECT ?o
         WHERE {
             ${values}
             ?s ta:timestamp ?o .
             ?s ta:sourceTimeline ?src .
             ?s rdf:type ?etype .
             ?etype rdfs:subClassOf ta:Event .
         }
         ORDER BY ASC(?o)
         LIMIT 1`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToDate(res)));
  }

  getMaxDate(t: Timeline[]): Observable<Date> {
    const url = this.getRepositoryUrl();
    let values = 'VALUES ?src {';
    for (let i = 0; i < t.length; i++) {
      values += '<' + t[i].uri + '> ';
    }
    values += '}';
    const q = this.getPrefixes() +
        `SELECT ?o
         WHERE {
             ${values}
             ?s ta:timestamp ?o .
             ?s ta:sourceTimeline ?src .
             ?s rdf:type ?etype .
             ?etype rdfs:subClassOf ta:Event .
         }
         ORDER BY DESC(?o)
         LIMIT 1`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToDate(res)));
  }

  getEvents(t: Timeline, startDate: Date, limit: number): Observable<Event[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes() +
        `SELECT ?uri ?time ?label
          WHERE {
                ?etype rdfs:subClassOf ta:Event .
                ?uri ta:sourceTimeline <${t.uri}> .
                ?uri rdf:type ?etype .
                ?uri ta:timestamp ?time .
                OPTIONAL {?uri rdfs:label ?label}
          } ORDER BY ASC(?time)
          LIMIT ${limit}`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToEvents(res, t)));
  }

  getReferredObjectUris(eventUri: string): Observable<string[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes() +
        `SELECT ?s
         WHERE {
            <${eventUri}> ta:refersTo ?s
         }`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToStrings(res)));
  }

  getReferredObjects(eventUri: string): Observable<TAObject[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes() +
        `SELECT ?o ?type ?sourceId ?fileName ?path ?refUrl ?refTitle
            WHERE {
            VALUES ?src { <${eventUri}> }
            ?src ta:refersTo ?o .
            ?o rdf:type ?type .
            OPTIONAL { ?o ta:sourceId ?sourceId }
            OPTIONAL { ?o ta:fileName ?fileName . ?o ta:path ?path }
            OPTIONAL { ?o ta:sourceUrl ?refUrl . OPTIONAL {?o ta:resourceTitle ?refTitle} }
         }`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToObjects(res)));
  }

  getObjectData(objectUri: string): Observable<TAObject[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes() +
        `SELECT ?o ?type ?sourceId ?fileName ?path ?refUrl ?refTitle
            WHERE {
            VALUES ?o { <${objectUri}> }
            ?o rdf:type ?type .
            OPTIONAL { ?o ta:sourceId ?sourceId }
            OPTIONAL { ?o ta:fileName ?fileName . ?o ta:path ?path }
            OPTIONAL { ?o ta:sourceUrl ?refUrl . OPTIONAL {?o ta:resourceTitle ?refTitle} }
         }`;
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToObjects(res)));
  }

  getContentsForEntry(entry: Entry): Observable<any[]> {
    const repo = this.getRepositoryUrl();
    const q = this.getPrefixes() +
      `SELECT ?s ?type ?text ?resUri
        WHERE {
         <${entry.uri}> ta:contains ?s .
           ?s rdf:type ?type .
           OPTIONAL { ?s ta:text ?text }
           OPTIONAL { ?s ta:linksResource ?resUri }
       }`;
    return this.http.post(repo, q, httpOptionsQuery).pipe(map(res => this.bindingsToArray(res)));
  }

  getContentsForEntryById(entryId: string): Observable<any[]> {
    const repo = this.getRepositoryUrl();
    const q = this.getPrefixes()
      + 'SELECT ?s ?text ?resUri ?type'
      + ' WHERE {'
      + '   ?entry ta:contains ?s'
      + '   . ?entry ta:sourceId ?id'
      + '   . ?entry rdf:type ?type'
      + '   . OPTIONAL { ?s ta:text ?text }'
      + '   . OPTIONAL { ?s ta:linksResource ?resUri }'
      + '   FILTER( ?id = "' + entryId + '" )'
      + ' }';
    return this.http.post(repo, q, httpOptionsQuery).pipe(map(res => this.bindingsToArray(res)));
  }

  getURLPrefixes(): Observable<string[]> {
    const url = this.getRepositoryUrl();
    const q = this.getPrefixes()
      + 'SELECT DISTINCT (STRBEFORE(?url,":") as ?s)'
      + ' WHERE { ?u rdf:type ta:URLContent . ?u ta:sourceUrl ?url }';
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToStrings(res)));
  }

  getURLsFiltered(prefix: string, contents: string): Observable<string[]> {
    const url = this.getRepositoryUrl();
    let filter = 'contains(?s, "' + contents + '")';
    if (prefix !== '*') {
      filter += ' && strStarts(?s, "' + prefix + '")';
    }
    const q = this.getPrefixes()
      + 'SELECT DISTINCT ?s'
      + ' WHERE { ?u rdf:type ta:URLContent . ?u ta:sourceUrl ?s'
      + ' FILTER(' + filter + ')}'
      + ' LIMIT 20';
    return this.http.post(url, q, httpOptionsQuery).pipe(map(res => this.bindingsToStrings(res)));
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

  private bindingsToStrings(res: any): string[] {
    const bindings = res.results.bindings;
    const ret = new Array<string>();
    for (let i = 0; i < bindings.length; i++) {
      ret.push(bindings[i].s.value);
    }
    return ret;
  }

  private bindingsToDate(res: any): Date {
    const bindings = res.results.bindings;
    if (bindings.length > 0) {
      return new Date(bindings[0].o.value);
    } else {
      return new Date();
    }
  }

  private bindingsToTimelines(res: any): Timeline[] {
    const bindings = res.results.bindings;
    const ret = new Array<Timeline>();
    // console.log(bindings);
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

  private bindingsToEvents(res: any, src: Timeline): Event[] {
    const bindings = res.results.bindings;
    const ret = new Array<Event>();
    // console.log(bindings);
    for (let i = 0; i < bindings.length; i++) {
      const item = bindings[i];
      const newitem = new Event();
      newitem.uri = item.uri.value;
      newitem.timestamp = new Date(item.time.value);
      if (item.label !== undefined) {
        newitem.label = item.label.value;
      }
      if (src) {
        newitem.sourceTimeline = src;
      } else {
        const tl = new Timeline();
        tl.uri = item.timeline.value;
        tl.label = item.tlid.value;
        newitem.sourceTimeline = tl;
      }
      ret.push(newitem);
    }
    return ret;
  }

  private bindingsToObjects(res: any): TAObject[] {
    const bindings = res.results.bindings;
    const ret = new Array<TAObject>();
    for (let i = 0; i < bindings.length; i++) {
      const item = bindings[i];
      const type = item.type.value;
      const iuri = item.o.value;
      switch (type) {
        case 'http://nesfit.github.io/ontology/ta.owl#Entry':
            const entry = new Entry();
            entry.uri = iuri;
            entry.sourceId = item.sourceId.value;
            ret.push(entry);
            break;
        case 'http://nesfit.github.io/ontology/ta.owl#LocalFile':
            const file = new LocalFile();
            file.uri = iuri;
            file.fileName = item.fileName.value;
            file.path = item.path.value;
            ret.push(file);
            break;
        case 'http://nesfit.github.io/ontology/ta.owl#WebResource':
            const wres = new WebResource();
            wres.uri = iuri;
            wres.url = item.refUrl.value;
            if (item.refTitle !== undefined) {
              wres.title = item.refTitle.value;
            }
            ret.push(wres);
            break;
      }
    }
    return ret;
  }

  private bindingsToArray(res: any): any[] {
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
