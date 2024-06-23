import type {User} from "@/base/model/user.model";

export class Chat {
    public id?: string;
    public from?: User;
    public to?: User;
    public messages?: string;
    public timestamp?: number;
    public createdAt?: Date;
    public updatedAt?: Date;
}