import { Component } from '@angular/core';
import { Logger } from './service/logger.service';
import { User } from './model/User';
import { NavLink } from './model/NavLink';
import { UserService } from './service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './app.my-navbar.html',
  styleUrls: ['./app.my-navbar.css'],
  providers: [
    Logger,
    UserService
  ]
})

export class AppNavBarComponent {

  title = 'Angular Demo';
  users: User[];
  navLinks: NavLink[];
  constructor(
    private Log: Logger,
    private userService: UserService
  ){
    this.users = userService.getUsers();
    this.navLinks = [ new NavLink('#', 'FEATURES'),
    new NavLink('#', 'DOCS')];
  }

  changeTitle = function(event: any){
    this.title = event.target.value;
  }
}