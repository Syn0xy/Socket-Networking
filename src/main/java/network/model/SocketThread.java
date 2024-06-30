package network.model;

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

import network.utils.Destroyable;
import network.utils.TranslatorJson;

public abstract class SocketThread extends SocketAction<PacketObject> implements Destroyable {

    private static final String DISCONNECT_ACTION = "disconnect";

    private Socket socket;

    private PrintWriter output;

    private Map<String, List<Runnable>> runnableActions;

    public SocketThread(Socket socket) throws IOException {
        this.socket = socket;
        this.output = this.getPrinter();
        this.runnableActions = new HashMap<>();
        this.start();
    }

    public abstract int getID();
    
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }
    
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public PrintWriter getPrinter() throws IOException {
        return new PrintWriter(this.getOutputStream(), true);
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(this.getInputStream()))) {
            while (true) {
                String inputString = input.readLine();
                
                this.receive(TranslatorJson.parse(inputString, Packet.class));
            }
        } catch (IOException e) {
            System.err.println("Error occured in socket thread: " + e.getMessage());
            e.printStackTrace();
        } finally {
            this.destroy();
        }
    }

    @Override
    public void destroy() {
        this.receive(new Packet(DISCONNECT_ACTION));
    }
    
    protected void receive(Packet inputPacket) {
        String token = inputPacket.getToken();
        PacketObject data = inputPacket.getData();
        
        if (this.runnableActions.containsKey(token)) {
            this.runnableActions.get(token).forEach(Runnable::run);
        }
        
        if (data != null) {
            this.on(token, data);
        }
    }

    public void emit(Packet outputPacket) {
        try {
            String jsonPacket = TranslatorJson.stringify(outputPacket);
            this.output.println(jsonPacket);
        } catch (Exception e) {
            System.err.println("Error occured on socket object output: " + e.getMessage());
        }
    }

    public void emit(String token, Object data) {
        this.emit(new Packet(token, data));
    }

    public void emit(String token) {
        this.emit(new Packet(token));
    }

    public void on(String token, Runnable action) {
        if (!this.runnableActions.containsKey(token)) {
            this.runnableActions.put(token, new ArrayList<>());
        }
        this.runnableActions.get(token).add(action);
    }
    
}
