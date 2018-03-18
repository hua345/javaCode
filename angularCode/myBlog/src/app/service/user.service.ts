import {Injectable} from '@angular/core';
import {Users} from '../mock/user-data.mock';
import {Logger} from './logger.service';

@Injectable()
export class UserService {
    constructor(private Log: Logger){

    }
    getUsers(){
        this.Log.log('Get User!');
        return Users;
    }
}
