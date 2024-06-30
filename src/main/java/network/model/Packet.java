package network.model;

import java.io.Serializable;

import network.utils.TranslatorJson;

public class Packet implements Serializable {

    private String token;

    private PacketObject data;

    public Packet(String token, Object object) {
        this.token = token;
        
        if (object != null) {
            this.data = new PacketObject(TranslatorJson.stringify(object));
        } else {
            this.data = null;
        }
    }

    public Packet(String token) {
        this(token, null);
    }

    public Packet() {}

    public String getToken() {
        return token;
    }
    
    public PacketObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Packet [token=" + token + ", data=" + data + "]";
    }
    
}
