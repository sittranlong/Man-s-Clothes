import axios from "axios";

export class UserService {
    private url = '/v2/user';
    private adminUrl = '/user';
    private store: any;

    constructor(store) {
        this.store = store;
    }
    async update(model: any) {
        const response = await axios.post(`${this.url}/update`, model, {
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

    async profile() {
        const response = await axios.get(`${this.url}/profile`, {
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

    async findAdmin() {
        const response = await axios.get(`${this.url}/findAdmin`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }
}