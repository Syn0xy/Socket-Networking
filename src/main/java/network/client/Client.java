package network.client;

import java.io.IOException;
import java.net.Socket;

import network.model.Packet;
import network.model.PacketObject;
import network.model.SocketThread;

public class Client extends SocketThread {

    private static final String CONNECTION_ACTION = "connection";

    private static final int DEFAULT_ID = -1;

    private int id;

    private boolean isConnect;
    
    public Client(String serverName, int serverPort) throws IOException {
        super(new Socket(serverName, serverPort));
        this.id = DEFAULT_ID;
    }

    @Override
    public int getID() {
        return this.id;
    }

    public boolean isConnect() {
        return isConnect;
    }
    
    @Override
    protected void receive(Packet inputPacket) {
        String token = inputPacket.getToken();
        PacketObject data = inputPacket.getData();
        
        // System.out.println("Client received: " + inputPacket);
        if (token.equals(CONNECTION_ACTION) && data != null) {
            this.id = data.toType(int.class);
            this.isConnect = true;
        }
        super.receive(inputPacket);
    }

    @Override
    public void destroy() {
        this.isConnect = false;
        super.destroy();
    }

}
