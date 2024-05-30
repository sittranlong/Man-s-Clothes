import axios from "axios";

export class UserService {
    public url = "/v2/user"
    private store: any;

    constructor(store: any) {
        this.store = store;
    }

    public async profile() {
        const response = await axios.get(`${this.url}/profile`, {
            headers: {
                Authorization: `Bearer ${this.store?.getters.getToken}`
            }
        });
        return response.data;
    }
}