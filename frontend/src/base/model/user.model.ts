export class User {
    id?: string;
    username?: string;
    password?: string;
    fullName?: string;
    phone?: string;
    email?: string;
    createDate?: Date;
    modifiedDate?: Date;
    address?: string;
    avatar?: string;
    avatarBase64?: string;
    constructor() {
        this.id = '';
        this.username = '';
        this.password = '';
        this.fullName = '';
        this.phone = '';
        this.email = '';
        this.createDate = new Date();
        this.modifiedDate = new Date();
        this.address = '';
        this.avatar = '';
        this.avatarBase64 = '';
    }
}