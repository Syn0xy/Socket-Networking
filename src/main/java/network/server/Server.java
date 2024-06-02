package network.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import network.SocketThread;

public class Server extends Thread {

    private static final String CONNECTION_ACTION = "connection";
    
    private int port;
    
    private List<SocketThread> sockets;

    private Map<String, List<Consumer<SocketThread>>> actions;

    public Server(int port) {
        this.port = port;
        this.sockets = new ArrayList<>();
        this.actions = new HashMap<>();
        this.start();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                sockets.add(serverThread);

                this.actions.get(CONNECTION_ACTION).forEach(consumer -> {
                    consumer.accept(serverThread);
                });
            }
        } catch (Exception e) {
            System.err.println("Error occured in server main: " + e.getMessage());
        }
    }

    public void on(String message, Consumer<SocketThread> action) {
        if (!this.actions.containsKey(message)) {
            this.actions.put(message, new ArrayList<>());
        }
        this.actions.get(message).add(action);
    }

    public void emit(String message) {
        this.sockets.forEach(socket -> {
            socket.emit(message);
        });
    }

}
