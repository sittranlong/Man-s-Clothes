import { Socket } from "socket.io-client";
import { io } from "socket.io-client";

class SocketIOService {
    socket: Socket<any, any>;
    constructor() {
        this.socket = io("ws://localhost:9090" );
        this.socket.on("connect", () => {
            console.log("connected");
        });
    }
}

// create an instance/connection here
export const socket = new SocketIOService().socket;