import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { D3DemoComponent } from './d3-demo/d3-demo.component';


@NgModule({
  declarations: [
    AppComponent,
    D3DemoComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
