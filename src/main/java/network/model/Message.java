package network.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String message;

    private Object object;

    public Message(String message, Object object) {
        this.message = message;
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Message [message=" + message + ", object=" + object + "]";
    }
    
}
