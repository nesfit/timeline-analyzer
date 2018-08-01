import { ResourcesComponent } from './resources/resources.component';
import { TimelineComponent } from './timeline/timeline.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: '/timeline', pathMatch: 'full' },
  { path: 'timeline', component: TimelineComponent },
  { path: 'resources', component: ResourcesComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
