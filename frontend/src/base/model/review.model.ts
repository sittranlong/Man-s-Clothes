import {User} from "@/base/model/user.model";
import {Product} from "@/base/model/product.model";

export class Review {
    id: string;
    content: string;
    rating: number;
    image: string;
    imageBase64: string;
    user: User;
    product: Product;
    status: string;
    productId: string;
}