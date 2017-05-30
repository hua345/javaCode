import {Injectable} from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { User } from '../model/User';
import { Logger } from './logger.service';

@Injectable()
export class UserService {
    private USERURL = 'api/users';
    private headers = new Headers({'Content-Type': 'application/json'});

    constructor(private Log: Logger,
    private http: Http) { }


    getUserByName(name: string): Promise<User> {
    const url = `${this.USERURL}/?name=${name}`;
    return this.http.get(url)
        .toPromise()
        .then(response => response.json().data as User)
        .catch(this.handleError);
    }

    getUsers(): Promise<User[]> {
        console.log('Get User!');
        return this.http.get(this.USERURL)
        .toPromise()
        .then(response => response.json().data as User[])
        .catch(this.handleError);
    }

    create(name: string): Promise<User> {
    return this.http
        .post(this.USERURL, JSON.stringify({name: name}), {headers: this.headers})
        .toPromise()
        .then(res => res.json().data as User)
        .catch(this.handleError);
    }
    private handleError(error: any): Promise<any>{
        console.log('An error occurred :', error);
        return Promise.reject(error.message);
    }
}
