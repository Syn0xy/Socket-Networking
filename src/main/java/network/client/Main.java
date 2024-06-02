package network.client;

import network.SocketThread;

public class Main {

    private static final String SERVER_NAME = "localhost";

    private static final int SERVER_PORT = 2000;

    public static void main(String[] args) {
        Client socket = new Client(SERVER_NAME, SERVER_PORT);
    }
    
}
