export class User {
    id?: string;
    username?: string;
    password?: string;
    email?: string;
    createDate?: Date;
    modifiedDate?: Date;
    address?: string;
    avatar?: string;
    constructor() {
        this.id = '';
        this.username = '';
        this.password = '';
        this.email = '';
        this.createDate = new Date();
        this.modifiedDate = new Date();
        this.address = '';
        this.avatar = '';
    }
}