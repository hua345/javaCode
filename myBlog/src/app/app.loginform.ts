import { Component } from '@angular/core';
import { Logger } from './service/logger.service';
import { UserService } from './service/user.service';
import { User } from './model/User';

@Component({
  selector: 'app-loginform',
  templateUrl: './app.loginform.html',
  styleUrls: ['./app.loginform.css'],
  providers: [
    Logger,
    UserService
  ]
})

export class AppLoginFormComponent {
    users: User[];
    submitted = false;
    model = new User('fangfang', 22, '2290910211@qq.com', '123456');

    constructor(
        private Log: Logger,
        private userService: UserService
    ){
        this.users = userService.getUsers();
    }


    onSubmit() {
        this.Log.log('form has submitted!');
        this.submitted = true;
    }
}