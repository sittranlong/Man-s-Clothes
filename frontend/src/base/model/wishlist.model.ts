import {User} from "@/base/model/user.model";
import {Product} from "@/base/model/product.model";

export class Wishlist {
    id?: string;
    user?: User;
    product?: Product;
}