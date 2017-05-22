import { Component } from '@angular/core';
import { Logger } from './service/logger.service';
import { User } from './model/User';
import { UserService } from './service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './app.my-navbar.html',
  styleUrls: ['./app.my-navbar.css'],
  providers:[
    Logger,
    UserService
  ]
})

export class AppNavBarComponent {
  title:'Angular Demo';
  users: User[];

  constructor( 
    private Log:Logger,
    private userService:UserService
  ){
    this.users = userService.getUsers();
  }
  navLinks = [{href:"#", name:"FEATURES"},
  {href:"#", name:"DOCS"}];
  changeTitle = function(event:any){
    this.title = event.target.value;
  }
}