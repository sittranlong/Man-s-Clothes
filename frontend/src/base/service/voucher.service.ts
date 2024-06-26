import axios from "axios";

export class VoucherService {
    public url = '/v2/voucher'
    public adminUrl = '/voucher'

    async update(model: any) {
        const response = await axios.put(`${this.adminUrl}/update/${model.id}`, model, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async save(model: any) {
        const response = await axios.post(`${this.adminUrl}/save`, model, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async delete(id: string) {
        const response = await axios.delete(`${this.adminUrl}/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async get(id: string) {
        const response = await axios.get(`${this.adminUrl}/get/${id}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async findAll() {
        const response = await axios.get(`${this.adminUrl}/findAll`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async findBySearch(model: any) {
        const response = await axios.post(`${this.adminUrl}/findBySearch`, model, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }
}