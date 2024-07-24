import axios from "axios";

export class PageConfigService {
    private url = '/v2/page-config';
    private adminUrl = '/page-config';
    private store: any;

    constructor(store: any) {
        this.store = store;
    }

    async create(model: any) {
        const response = await axios.post(`${this.adminUrl}/save`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async update(model: any) {
        const response = await axios.put(`${this.adminUrl}/update/${model.id}`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async delete(model: any) {
        const response = await axios.post(`${this.adminUrl}/delete`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findBySearch(model : any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`,model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async get(id: any) {
        const response = await axios.get(`${this.adminUrl}/get/${id}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findByCode(model: any) {
        const response = await axios.post(`${this.url}/findByCode`, model);
        return response.data;
    }
}