package network.model;

import network.utils.TranslatorJson;

public class PacketObject {

    private String json;

    public PacketObject(final String json) {
        this.json = json;
    }

    public PacketObject() {}

    public String getJson() {
        return json;
    }

    public <T> T toType(final Class<T> classType) {
        return TranslatorJson.parse(this.json, classType);
    }

    @Override
    public String toString() {
        return "PacketObject [json=" + json + "]";
    }

}