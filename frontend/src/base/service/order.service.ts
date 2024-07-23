import axios from "axios";

export class OrderService {
    public url = '/v2/order';
    public adminUrl = '/order';
    private store: any;

    constructor(store: any) {
        this.store = store;
    }

    async createOrder(model: any) {
        const response = await axios.post(`${this.url}/create`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async getDefaultContact() {
        const response = await axios.get(`${this.url}/getDefaultContact`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async getOrderInfo(model: any) {
        const response = await axios.post(`${this.url}/getOrderInfo`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async getQrCode(orderId: any) {
        const response = await axios.get(`${this.url}/getQrCode/${orderId}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async cancelOrder(model: any) {
        const response = await axios.post(`${this.url}/cancel`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findByUser(model: any) {
        const response = await axios.post(`${this.url}/findByUser`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async receive(model: any) {
        const response = await axios.post(`${this.url}/received`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async getOrder(orderId: any) {
        const response = await axios.get(`${this.url}/get/${orderId}`, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

//    for admin
    async findBySearch(model: any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async approve(model: any) {
        const response = await axios.post(`${this.adminUrl}/approve`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async reject(model: any) {
        const response = await axios.post(`${this.adminUrl}/reject`, model, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async shipping(model: any) {
        const response = await axios.post(`${this.adminUrl}/shipping`, model, {
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
}