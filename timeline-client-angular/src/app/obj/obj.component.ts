import { Component, OnInit, Input } from '@angular/core';
import { Rdf4jService } from '../rdf4j.service';
import { TAObject } from '../model/taobject';
import { LocalFile } from '../model/localfile';
import { WebResource } from '../model/webresource';
import { Entry } from '../model/entry';
import { SharedService } from '../shared.service';

@Component({
  selector: 'app-obj',
  templateUrl: './obj.component.html',
  styleUrls: ['./obj.component.css']
})
export class ObjComponent implements OnInit {

  @Input() uri: string;
  type: string;
  file: LocalFile;
  wres: WebResource;
  entry: Entry;
  entryContents: any[];

  constructor(private rdf: Rdf4jService, private shared: SharedService) {
    this.type = 'none';
    this.entryContents = [];
  }

  ngOnInit() {
    this.updateInfo();
  }

  updateInfo(): void {
    this.rdf.getObjectData(this.uri).subscribe(data => this.setObjects(data));
  }

  setObjects(objs: TAObject[]): void {
    this.type = 'none';
    this.entryContents = [];
    if (objs.length > 0) {
      const obj = objs[0];
      this.type = obj.objType();
      if (obj instanceof LocalFile) {
        this.file = obj;
      } else if (obj instanceof WebResource) {
        this.wres = obj;
      } else if (obj instanceof Entry) {
        this.entry = obj;
        this.rdf.getContentsForEntry(this.entry).subscribe(data => this.setEntryContents(data));
      }
      console.log(this.type);
    }
  }

  setEntryContents(data: any[]): void {
    this.entryContents = data;
    for (let i = 0; i < this.entryContents.length; i++) {
      // determine short type
      const item = this.entryContents[i];
      item.typeShort = this.getShortType(item.type.uri);
      console.log(item);
    }
  }

  private getShortType(uri: string): string {
    const t = String(uri);
    const pos = t.lastIndexOf('#');
    return (pos !== -1) ? t.substring(pos + 1).toString() : '';
  }

  showFile(path: string): void {
    this.shared.files.showFile(path);
  }

  showURL(url: string): void {
    this.shared.resources.showURL(url);
  }

}
