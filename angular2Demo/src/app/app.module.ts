import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppNavBarComponent } from './app.my-navbar';

@NgModule({
  declarations: [
    AppComponent,
    AppNavBarComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [
    AppComponent,
    AppNavBarComponent
  ]
})
export class AppModule { }