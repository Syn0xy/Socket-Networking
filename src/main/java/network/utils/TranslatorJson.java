package network.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TranslatorJson {
    
    private static final ObjectMapper MAPPER = new ObjectMapper();
        
    public static String stringify(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static <T> T parse(String json, Class<T> classType) {
        try {
            if (json == null) {
                throw new NullPointerException();
            }
            
            return MAPPER.readValue(json, classType);
        } catch (NullPointerException  | JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
