import { Rdf4jService } from '../rdf4j.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  data: any[];

  ngOnInit() {
    console.log('Query!');
    this.data = [ 'ahoj' ];
    const q = 'PREFIX ta: <http://nesfit.github.io/ontology/ta.owl#> ' +
            'SELECT ?s ?p ?o ?e ?time ' +
            'WHERE {?s ?p ?o . ?s ?p "???" . ?e ta:contains ?s . ?e ta:timestamp ?time } ';
    this.rdf.query(q).subscribe(data => this.data = data);
  }

  constructor(private rdf: Rdf4jService) { }

}
