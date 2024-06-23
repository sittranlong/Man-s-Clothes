import axios from "axios";
import type {RouteParamValue} from "vue-router";
export class ProductService {
    public url = 'https://fakestoreapi.com/products';

    public async getById(id: string | RouteParamValue[]) {
        const response = await axios.get(`${this.url}/${id}`);
        return response.data;
    }

    public async findAll() {
        const response = await axios.get(this.url);
        return response.data;
    }
}