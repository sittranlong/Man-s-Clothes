import {Category} from "@/base/model/category.model";
import {Brand} from "@/base/model/brand.model";

export class Product {
    id?: number;
    name?: string;
    description?: string;
    price?: number;
    discountPercent?: number;
    image?: string;
    status?: string;
    category?: Category = new Category();
    brand?: Brand = new Brand();
    metaConfig?: MetaConfig;
    productDetails?: ProductDetail[];
    categoryId?: number;
    brandId?: number;
    imageBase64?: string;
}

export class ProductCard {
    id?: number;
    title?: string;
    price?: number;
    description?: string;
    image?: string;
    rating?: {
        rate: number;
        count: number;
    }
}

export class Size {
    id?: number;
    name?: string;
    description?: string;
}

export class Color {
    id?: number;
    name?: string;
    code?: string;
}

export class MetaConfig {
    id?: number;
    title?: string;
    description?: string;
    keywords?: string;
    slug?: string;
    image?: string;
    imageBase64?: string;
}

export class ProductDetail {
    id?: number;
    size?: Size;
    color?: Color;
    product?: Product;
    quantity?: number;
    image?: string;
    sizeId?: number;
    colorId?: number;
    imageBase64?: string;
}
