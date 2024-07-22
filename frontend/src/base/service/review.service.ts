import axios from "axios";

export class ReviewService {
    private url = '/v2/review'
    private adminUrl = '/review'
    private store: any

    constructor(store: any) {
        this.store = store
    }

    async create(review: any) {
        const response = await axios.post(`${this.url}/create`, review, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }

    async findByProductId(productId: string) {
        const response = await axios.get(`${this.url}/findByProductId?productId=${productId}`);
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

    async approve(id: string) {
        const response = await axios.put(`${this.adminUrl}/approve/${id}`, {}, {
            headers: {
                Authorization: `${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }
}