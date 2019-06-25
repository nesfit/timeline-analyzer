import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ObjFileComponent } from './obj-file.component';

describe('ObjFileComponent', () => {
  let component: ObjFileComponent;
  let fixture: ComponentFixture<ObjFileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ObjFileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ObjFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
