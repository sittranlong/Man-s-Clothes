import axios from "axios";
import type {Order} from "@/base/model/order.model";

export class OrderService {
    public url = '/v2/order';
    public adminUrl = '/order';

    async findBySearchForUser(search: any) {
        const response = await axios.post(`${this.url}/findBySearchForUser`, search, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async get(id: string): Promise<Order> {
        const response = await axios.get(`${this.url}/get/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async delete(id: string) {
        await axios.delete(`${this.url}/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
    }

    async findBySearch(search: any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`, search, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async getDetail(id: string): Promise<Order> {
        const response = await axios.get(`${this.adminUrl}/getDetail/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async reject(model: any) {
        await axios.post(`${this.adminUrl}/reject`, model, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
    }

    async approve(id: string) {
        await axios.post(`${this.adminUrl}/approve/${id}`, {}, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
    }

    async chart() {
        const response = await axios.get(`${this.adminUrl}/chart`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }
}