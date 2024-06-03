package network.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import network.SocketThread;
import network.model.SocketAction;

public class Server extends SocketAction<SocketThread> {

    private static final String CONNECTION_ACTION = "connection";
    
    private int port;
    
    private List<SocketThread> sockets;

    public Server(int port) {
        this.port = port;
        this.sockets = new ArrayList<>();
        this.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new ServerThread(socket);
                sockets.add(socketThread);
                
                this.on(CONNECTION_ACTION, socketThread);
                
                socketThread.emit("connection", socketThread.getID());
            }
        } catch (Exception e) {
            System.err.println("Error occured in server main: " + e.getMessage());
        }
    }

    public void emit(String message) {
        this.sockets.forEach(socket -> {
            socket.emit(message);
        });
    }

    public void emit(String message, Object object) {
        this.sockets.forEach(socket -> {
            socket.emit(message, object);
        });
    }

}
