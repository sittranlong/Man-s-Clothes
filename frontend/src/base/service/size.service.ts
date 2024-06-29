import axios from "axios";

export class SizeService {
    private url = '/v2/size';
    private adminUrl = '/size';
    private store: any;

    constructor(store) {
        this.store = store;
    }

    async update(model: any) {
        const response = await axios.put(`${this.adminUrl}/update/${model.id}`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async save(model: any) {
        const response = await axios.post(`${this.adminUrl}/save`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async delete(id: string) {
        const response = await axios.delete(`${this.adminUrl}/delete/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async get(id: string) {
        const response = await axios.get(`${this.adminUrl}/get/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findAll() {
        const response = await axios.get(`${this.adminUrl}/findAll`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findBySearch(model: any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    // for client

    async findAllCategoryClient() {
        const response = await axios.get(`${this.url}/findAll`);
        return response.data;
    }
}