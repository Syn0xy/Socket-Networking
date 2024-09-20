package network.client;

import java.io.IOException;
import java.net.Socket;

import network.model.Packet;
import network.model.PacketObject;
import network.model.SocketThread;

public class Client extends SocketThread {

    private static final String CONNECTION_ACTION = "connection";

    private static final String DEFAULT_ID = null;

    private String ID;

    private boolean isConnect;
    
    public Client(final String serverName, final int serverPort) throws IOException {
        super(new Socket(serverName, serverPort));
        this.ID = Client.DEFAULT_ID;
    }

    @Override
    public String getID() {
        return this.ID;
    }

    public boolean isConnect() {
        return this.isConnect;
    }
    
    @Override
    public void destroy() {
        this.isConnect = false;
        super.destroy();
    }

    @Override
    protected void receive(final Packet inputPacket) {
        final String token = inputPacket.getToken();
        final PacketObject data = inputPacket.getData();
        
        // System.out.println("Client received: " + inputPacket);
        if (token.equals(Client.CONNECTION_ACTION) && data != null) {
            this.ID = data.toType(String.class);
            this.isConnect = true;
        }
        super.receive(inputPacket);
    }

}
