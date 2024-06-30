package network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import network.model.Packet;
import network.model.SocketThread;

public class ServerThread extends SocketThread {

    protected static final Map<Integer, SocketThread> SERVER_THREADS = new ConcurrentHashMap<>();

    private static int countID = 0;

    private final int ID;

    public ServerThread(Socket socket) throws IOException {
        super(socket);
        this.ID = countID++;
        SERVER_THREADS.put(this.ID, this);
    }

    public int getID() {
        return ID;
    }

    @Override
    protected void receive(Packet inputPacket) {
        // System.out.println("Server received: " + inputPacket);
        super.receive(inputPacket);
    }
    
    @Override
    public void destroy() {
        SERVER_THREADS.remove(this.ID);
        super.destroy();
    }

}