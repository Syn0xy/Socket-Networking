package network.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;

import network.model.Packet;
import network.model.SocketAction;
import network.model.SocketThread;

public class Server extends SocketAction<SocketThread> {

    private static final String CONNECTION_ACTION = "connection";
    
    private final int port;
    
    private final Map<String, SocketThread> indexedSockets;

    private final Collection<SocketThread> sockets;

    public Server(final int port) {
        this.port = port;
        this.indexedSockets = ServerThread.SERVER_THREADS;
        this.sockets = this.indexedSockets.values();
        this.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                final SocketThread socketThread = new ServerThread(socket);
                
                socketThread.emit(Server.CONNECTION_ACTION, socketThread.getID());
                this.on(Server.CONNECTION_ACTION, socketThread);
            }
        } catch (final Exception e) {
            System.err.println("Error occured in server main: " + e.getMessage());
        }
    }

    public void emit(final String token) {
        final Packet outputPacket = new Packet(token);
        this.sockets.forEach(socket -> socket.emit(outputPacket));
    }

    public void emit(final String token, final Object data) {
        final Packet outputPacket = new Packet(token, data);
        this.sockets.forEach(socket -> socket.emit(outputPacket));
    }

}
