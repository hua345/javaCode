import {User} from '../model/User';
import { InMemoryDbService } from 'angular-in-memory-web-api';

export class UserDataMemoryMock implements InMemoryDbService{
  createDb() {
    const users: User[] = [
        new User('1', 'fangfang', 21, '2290910211@qq.com', '123456'),
        new User('2', 'chenjianhua_b', 22, '2290910211@qq.com', '123456'),
        new User('3', 'chenjianhua_c', 23, '2290910211@qq.com', '123456'),
        new User('4', 'chenjianhua_d', 24, '2290910211@qq.com', '123456'),
        new User('5', 'chenjianhua_e', 25, '2290910211@qq.com', '123456'),
        new User('6', 'chenjianhua_f', 26, '2290910211@qq.com', '123456'),
    ];
    return {users};
  }
}
