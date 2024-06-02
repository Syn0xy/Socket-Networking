package network.server;

import java.io.IOException;
import java.net.Socket;

import network.SocketThread;

public class ServerThread extends SocketThread {

    public ServerThread(Socket socket) throws IOException {
        super(socket);
    }

    @Override
    protected void receive(String message) {
        System.out.println("Server received: '" + message + "'");
        super.receive(message);
    }

}