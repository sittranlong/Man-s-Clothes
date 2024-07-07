import axios from "axios";

export class ProductService {
    private url = '/v2/product';
    private adminUrl = '/product'
    private store: any;

    constructor(store) {
        this.store = store;
    }

    public async findBySearch(model: any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async findByFilter(model: any) {
        const response = await axios.post(`${this.url}/findByFilter`, model);
        return response.data;
    }

    public async getById(id: string) {
        const response = await axios.get(`${this.adminUrl}/get/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async get(id: string) {
        const response = await axios.get(`${this.url}/get/${id}` );
        return response.data;
    }

    public async delete(id: string) {
        const response = await axios.delete(`${this.adminUrl}/delete/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async save(model: any) {
        const response = await axios.post(`${this.adminUrl}/save`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    public async update(id: string, model: any) {
        const response = await axios.put(`${this.adminUrl}/update/${id}`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }
}