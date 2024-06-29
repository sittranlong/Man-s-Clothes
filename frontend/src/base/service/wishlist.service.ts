import {Wishlist} from "@/base/model/wishlist.model";
import axios from "axios";
import {ProductCard} from "@/base/model/product.model";

export class WishlistService {
    public url = '/v2/wishlist';
    private store : any;

    constructor(store: any) {
        this.store = store;
    }

    public async getWishlist(): Promise<Wishlist[]> {
        const response = await axios.get(this.url);
        return response.data;
    }

    public async add(productId: string | undefined): Promise<string> {
        const response = await axios.post(`${this.url}/add/${productId}`, {}, {
            headers: {
                Authorization : `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async remove(id: string): Promise<void> {
        await axios.delete(`${this.url}/remove/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
    }

    public async findAll(): Promise<Wishlist[]> {
        const response = await axios.get(`${this.url}/findAll`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async removeMultiple(ids: string[]): Promise<void> {
        await axios.post(`${this.url}/removeMultiple`, ids, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
    }

    public async countAll(): Promise<number> {
        const response = await axios.get(`${this.url}/countAll`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }
}