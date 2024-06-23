import axios from "axios";
import type {Tour} from "@/base/model/tour.model";

export class TourService {
    public url = '/v2/tour'
    public adminUrl = '/tour'

    public async getById(id: string): Promise<Tour> {
        const response = await axios.get(`${this.url}/get/${id}`);
        return response.data;
    }

    public async findBySearch(model: any)  {
        const response = await axios.post(`${this.url}/findBySearch`, model);
        return response.data;
    }

    public async findByNewest(model : any) {
        const response = await axios.post(`${this.url}/findByNewest`, model);
        return response.data;
    }

    public async bookTour(model: any) {
        const authorization = localStorage.getItem('token');
        const response = await axios.post(`${this.url}/bookTour`, model, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async getQRCodePurchase(id: string) {
        const authorization = localStorage.getItem('token');
        const response = await axios.get(`${this.url}/getQRCodePurchase/${id}`, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async successPurchase(id: string) {
        const authorization = localStorage.getItem('token');
        const response = await axios.post(`${this.url}/successPurchase/${id}`, {}, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async save(model: any) {
        const authorization = localStorage.getItem('token');
        const response = await axios.post(`${this.adminUrl}/save`, model, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async get(id: string) {
        const authorization = localStorage.getItem('token');
        const response = await axios.get(`${this.adminUrl}/get/${id}`, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async update(model: any) {
        const authorization = localStorage.getItem('token');
        const response = await axios.put(`${this.adminUrl}/update/${model.id}`, model, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async delete(id: string) {
        const authorization = localStorage.getItem('token');
        const response = await axios.delete(`${this.adminUrl}/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async findAll() {
        const authorization = localStorage.getItem('token');
        const response = await axios.get(`${this.adminUrl}/findAll`, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }

    public async findBySearchAdmin(model: any) {
        const authorization = localStorage.getItem('token');
        const response = await axios.post(`${this.adminUrl}/findBySearch`, model, {
            headers: {
                Authorization: `Bearer ${authorization}`
            }
        });
        return response.data;
    }
}