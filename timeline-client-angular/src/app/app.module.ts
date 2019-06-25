import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { Rdf4jService } from './rdf4j.service';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './/app-routing.module';
import { CustomReuseStrategy } from './customreusestrategy';
import { FormsModule } from '@angular/forms';
import { TimelineComponent } from './timeline/timeline.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ResourcesComponent } from './resources/resources.component';
import { RouteReuseStrategy } from '@angular/router';
import { ObjFileComponent } from './obj-file/obj-file.component';


@NgModule({
  declarations: [
    AppComponent,
    TimelineComponent,
    ResourcesComponent,
    ObjFileComponent
  ],
  imports: [
    NgbModule.forRoot(),
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    Rdf4jService,
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
