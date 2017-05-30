import {Injectable} from '@angular/core';
@Injectable()
export class Logger {
  log(msg: any) {
      console.log('From logger class: ' + msg);
  }
}
