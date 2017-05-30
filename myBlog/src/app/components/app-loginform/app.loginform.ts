import { Component, OnInit } from '@angular/core';
import { Logger } from '../../service/logger.service';
import { UserService } from '../../service/user.restful.service';
import { User } from '../../model/User';
import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'app-loginform',
  templateUrl: './app.loginform.html',
  styleUrls: ['./app.loginform.css'],
  providers: [
    Logger,
    UserService
  ]
})

export class AppLoginFormComponent implements OnInit {
    users: User[];
    submitted = false;
    model = new User('1', 'fangfang', 22, '2290910211@qq.com', '123456');

    constructor(
        private Log: Logger,
        private userService: UserService
    ){}


    ngOnInit(): void{
        this.userService
        .getUsers()
        .then( users => this.users = users);
    }
    onSubmit(): void {
        this.userService.getUserByName(this.model.name)
        .then( user => {
            console.log('user.name', user[0].name);
            console.log('user.password', user[0].password);
            if(user[0].name === this.model.name
            && user[0].password === this.model.password){
                this.Log.log('login success!');
                this.submitted = true;
            }else{
                this.Log.log('login failed!');
                this.submitted = false;
            }
        })
        .catch(errorMsg => console.log(errorMsg));

    }
}