import {ProductDetail} from "@/base/model/product.model";

export class Cart {
    id?: string;
    quantity?: number;
    price?: number;
    productDetail?: ProductDetail;
}