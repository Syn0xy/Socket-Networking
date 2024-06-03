package network.client;

import java.io.IOException;
import java.net.Socket;

import network.SocketThread;
import network.model.Message;

public class Client extends SocketThread {

    private static final int DEFAULT_ID = -1;

    private int id;
    
    public Client(String serverName, int serverPort) throws IOException {
        super(new Socket(serverName, serverPort));
        this.id = DEFAULT_ID;
    }

    @Override
    public int getID() {
        return this.id;
    }
    
    @Override
    protected void receive(Message inputMessage) {
        String message = inputMessage.getMessage();
        Object object = inputMessage.getObject();
        
        System.out.println("Client received: '" + message + "';'" + object + "'");
        if (message.equals("connection") && object != null) {
            this.id = (int) object;
        }
        super.receive(inputMessage);
    }

}
