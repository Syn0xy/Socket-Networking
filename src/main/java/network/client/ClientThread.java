package network.client;

import java.io.IOException;
import java.net.Socket;

import network.SocketThread;

public class ClientThread extends SocketThread {
    
    public ClientThread(Socket socket) throws IOException {
        super(socket);
    }
    
    @Override
    protected void receive(String message) {
        System.out.println("Client received: '" + message + "'");
        super.receive(message);
    }

}
