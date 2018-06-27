import { TestBed, inject } from '@angular/core/testing';

import { Rdf4jService } from './rdf4j.service';

describe('Rdf4jService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [Rdf4jService]
    });
  });

  it('should be created', inject([Rdf4jService], (service: Rdf4jService) => {
    expect(service).toBeTruthy();
  }));
});
