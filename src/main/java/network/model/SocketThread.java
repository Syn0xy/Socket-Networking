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

    private final Socket socket;

    private final PrintWriter output;

    private final Map<String, List<Runnable>> actions;

    public SocketThread(final Socket socket) throws IOException {
        this.socket = socket;
        this.output = this.getPrinter();
        this.actions = new HashMap<>();
        this.start();
    }

    public abstract String getID();
    
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
                final String inputString = input.readLine();
                
                this.receive(TranslatorJson.parse(inputString, Packet.class));
            }
        } catch (final IOException e) {
            System.err.println("Error occured in socket thread: " + e.getMessage());
        } finally {
            this.destroy();
        }
    }

    @Override
    public void destroy() {
        this.receive(new Packet(SocketThread.DISCONNECT_ACTION));
    }
    
    public void emit(final Packet outputPacket) {
        try {
            final String jsonPacket = TranslatorJson.stringify(outputPacket);
            this.output.println(jsonPacket);
        } catch (final Exception e) {
            System.err.println("Error occured on socket object output: " + e.getMessage());
        }
    }

    public void emit(final String token, final Object data) {
        this.emit(new Packet(token, data));
    }

    public void emit(final String token) {
        this.emit(new Packet(token));
    }

    public void on(final String token, final Runnable action) {
        if (!this.actions.containsKey(token)) {
            this.actions.put(token, new ArrayList<>());
        }
        this.actions.get(token).add(action);
    }

    protected void receive(final Packet inputPacket) {
        final String token = inputPacket.getToken();
        final PacketObject data = inputPacket.getData();
        
        if (this.actions.containsKey(token)) {
            this.actions.get(token).forEach(Runnable::run);
        }
        
        if (data != null) {
            this.on(token, data);
        }
    }
    
}
