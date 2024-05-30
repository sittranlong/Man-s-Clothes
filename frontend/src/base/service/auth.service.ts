import axios from "axios";

export class AuthService {
    public url = "/v2"
    public store: any;

    constructor(store: any) {
        this.store = store;
    }

    public register(data: any) {
        return axios.post(`${this.url}/register`, data);
    }

    public login(data: any) {
        return axios.post(`${this.url}/login`, data);
    }

    public verifyEmail(data: any) {
        return axios.post(`${this.url}/verifyEmail`, data);
    }

    public logout() {
        return axios.post(`${this.url}/logout`, {}, {
            headers: {
                Authorization: `Bearer ${this.store?.getters.getToken}`
            }
        });
    }
}