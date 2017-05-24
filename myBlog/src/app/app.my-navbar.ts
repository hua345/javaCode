import { Component } from '@angular/core';
import { Logger } from './service/logger.service';
import { User } from './model/User';

@Component({
  selector: 'app-navbar',
  templateUrl: './app.my-navbar.html',
  styleUrls: ['./app.my-navbar.css'],
  providers: [
    Logger
  ]
})

export class AppNavBarComponent {

  title = '神农园';

  changeTitle = function(event: any){
    this.title = event.target.value;
  }
}