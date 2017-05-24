import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { AppNavBarComponent } from './app.my-navbar';
import { AppLoginFormComponent } from './app.loginform';

@NgModule({
  declarations: [
    AppComponent,
    AppNavBarComponent,
    AppLoginFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule
  ],
  providers: [],
  bootstrap: [
    AppComponent,
    AppNavBarComponent,
    AppLoginFormComponent
  ]
})
export class AppModule { }
