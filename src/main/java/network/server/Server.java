package network.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import network.model.Packet;
import network.model.SocketAction;
import network.model.SocketThread;

public class Server extends SocketAction<SocketThread> {

    private static final String CONNECTION_ACTION = "connection";
    
    private int port;
    
    private Map<Integer, SocketThread> sockets;

    public Server(int port) {
        this.port = port;
        this.sockets = ServerThread.SERVER_THREADS;
        this.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new ServerThread(socket);
                
                socketThread.emit(CONNECTION_ACTION, socketThread.getID());
                this.on(CONNECTION_ACTION, socketThread);
            }
        } catch (Exception e) {
            System.err.println("Error occured in server main: " + e.getMessage());
        }
    }

    public void emit(String token) {
        Packet outputPacket = new Packet(token);
        this.sockets.values().forEach(socket -> {
            socket.emit(outputPacket);
        });
    }

    public void emit(String token, Object data) {
        Packet outputPacket = new Packet(token, data);
        this.sockets.values().forEach(socket -> {
            socket.emit(outputPacket);
        });
    }

}
