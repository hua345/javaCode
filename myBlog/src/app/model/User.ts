export class User{
    constructor(
        public name: string,
        private age: number,
        private email: string,
        public password: string,
    ){}
}