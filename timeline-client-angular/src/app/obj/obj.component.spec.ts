import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjComponent } from './obj.component';

describe('ObjComponent', () => {
  let component: ObjComponent;
  let fixture: ComponentFixture<ObjComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObjComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObjComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
