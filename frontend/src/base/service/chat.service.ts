import axios from "axios";

export class ChatService {
    public url = '/v2/chat';
    public adminUrl = '/chat';

    async sendMessage(message: any) {
        const response = await axios.post(`${this.url}/send`, message, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

    async getMessages(from : string | undefined){
        const response = await axios.get(`${this.url}/messages?from=${from}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        });
        return response.data;
    }

}