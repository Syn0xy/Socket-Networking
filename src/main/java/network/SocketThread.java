package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.model.SocketAction;
import network.model.Message;

public abstract class SocketThread extends SocketAction<Object> {

    private Socket socket;

    private ObjectOutputStream output;

    private Map<String, List<Runnable>> runnableActions;

    public SocketThread(Socket socket) throws IOException {
        this.socket = socket;
        this.output = this.getObjectOutput();
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

    public ObjectOutputStream getObjectOutput() throws IOException {
        return new ObjectOutputStream(this.getOutputStream());
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(this.getInputStream())) {
            while (true) {
                Object inputObject = input.readObject();
                
                this.receive((Message) inputObject);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error occured in socket thread: " + e.getMessage());
        }
    }
    
    protected void receive(Message inputMessage) {
        String key = inputMessage.getMessage();
        Object object = inputMessage.getObject();
        
        if (this.runnableActions.containsKey(key)) {
            this.runnableActions.get(key).forEach(Runnable::run);
        }
        
        if (object != null) {
            this.on(key, object);
        }
    }

    public void emit(String message, Object object) {
        try {
            this.output.writeObject(new Message(message, object));
        } catch (IOException e) {
            System.err.println("Error occured on socket object output: " + e.getMessage());
        }
    }

    public void emit(String message) {
        this.emit(message, null);
    }

    public void on(String message, Runnable action) {
        if (!this.runnableActions.containsKey(message)) {
            this.runnableActions.put(message, new ArrayList<>());
        }
        this.runnableActions.get(message).add(action);
    }
    
}
