import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, JsonpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

// Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { UserDataMemoryMock } from './mock/user_data_memory_mock';

import { AppComponent } from './app.component';
import { AppNavBarComponent } from './components/app-navbar/app.my-navbar';
import { AppLoginFormComponent } from './components/app-loginform/app.loginform';
import { AppPagenotfoundComponent } from './components/app-pagenotfound/app-pagenotfound.component';
import { AppRoutesModule } from './app.routes';
import { AppIndexComponent } from './components/app-index/app-index.component';
import { AppRegisterComponent } from './components/app-register/app-register.component';

@NgModule({
  declarations: [
    AppComponent,
    AppNavBarComponent,
    AppLoginFormComponent,
    AppPagenotfoundComponent,
    AppIndexComponent,
    AppRegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    AppRoutesModule,
    InMemoryWebApiModule.forRoot(UserDataMemoryMock),
  ],
  providers: [],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
