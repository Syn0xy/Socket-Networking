package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SocketThread extends Thread {

    private static int countID = 0;

    private final int ID;

    private Socket socket;

    private PrintWriter output;

    private Map<String, List<Runnable>> actions;

    public SocketThread(Socket socket) throws IOException {
        this.ID = countID++;
        this.socket = socket;
        this.output = this.getPrintWriter();
        this.actions = new HashMap<>();
    }
    
    public int getID() {
        return ID;
    }
    
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }
    
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public PrintWriter getPrintWriter() throws IOException {
        return new PrintWriter(this.getOutputStream(), true);
    }

    @Override
    public void run() {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(this.getInputStream()))) {
            while (true) {
                String inputString = output.readLine();

                this.receive(inputString);
            }
        } catch (IOException e) {
            System.err.println("Error occured " + e.getMessage());
        }
    }

    protected void receive(String message) {
        if (this.actions.containsKey(message)) {
            this.actions.get(message).forEach(Runnable::run);
        }
    }

    public void emit(String message) {
        this.output.println(message);
    }

    public void on(String message, Runnable action) {
        if (!this.actions.containsKey(message)) {
            this.actions.put(message, new ArrayList<>());
        }
        this.actions.get(message).add(action);
    }
    
}
