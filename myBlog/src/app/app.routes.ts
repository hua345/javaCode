import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppNavBarComponent } from './components/app-navbar/app.my-navbar';
import { AppLoginFormComponent } from './components/app-loginform/app.loginform';
import { AppPagenotfoundComponent } from './components/app-pagenotfound/app-pagenotfound.component';
import { AppIndexComponent } from './components/app-index/app-index.component';


const appRoutes: Routes = [
  { path: 'login', component: AppLoginFormComponent },
  {
    path: 'index',
    component: AppIndexComponent,
    data: { title: 'Index' }
  },
  { path: '', redirectTo: '/index', pathMatch: 'full' },
  { path: '**', component: AppPagenotfoundComponent }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
    // other imports here
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutesModule { }