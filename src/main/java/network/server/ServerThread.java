package network.server;

import java.io.IOException;
import java.net.Socket;

import network.SocketThread;
import network.model.Message;

public class ServerThread extends SocketThread {

    private static int countID = 0;

    private final int ID;

    public ServerThread(Socket socket) throws IOException {
        super(socket);
        this.ID = countID++;
    }

    public int getID() {
        return ID;
    }

    @Override
    protected void receive(Message inputMessage) {
        String message = inputMessage.getMessage();
        Object object = inputMessage.getObject();
        
        System.out.println("Server received: '" + message + "';'" + object + "'");
        super.receive(inputMessage);
    }

}