import axios from "axios";

export class CartService {
    public url = '/v2/cart'
    private store: any;

    constructor(store: any) {
        this.store = store
    }

    async add(model: any) {
        const response = await axios.post(`${this.url}/add`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async get(model: any) {
        const response = await axios.post(`${this.url}/get`,model ,{
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async update(model: any) {
        const response = await axios.put(`${this.url}/update`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async delete(id) {
        const response = await axios.delete(`${this.url}/delete/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

}