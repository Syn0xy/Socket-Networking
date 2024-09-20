package network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import network.model.Packet;
import network.model.SocketThread;
import network.utils.RandomIdentifier;

public class ServerThread extends SocketThread {

    protected static final Map<String, SocketThread> SERVER_THREADS = new ConcurrentHashMap<>();

    private final String ID;

    public ServerThread(final Socket socket) throws IOException {
        super(socket);
        this.ID = RandomIdentifier.random();
        ServerThread.SERVER_THREADS.put(this.ID, this);
    }
    
    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    protected void receive(final Packet inputPacket) {
        // System.out.println("Server received: " + inputPacket);
        super.receive(inputPacket);
    }
    
    @Override
    public void destroy() {
        ServerThread.SERVER_THREADS.remove(this.ID);
        super.destroy();
    }

}