package network.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class SocketAction<T> extends Thread {

    private Map<String, List<Consumer<T>>> actions;

    public SocketAction() {
        this.actions = new HashMap<>();
    }

    public abstract void emit(String message);

    public abstract void emit(String message, Object object);

    public void on(String message, Consumer<T> action) {
        if (!this.actions.containsKey(message)) {
            this.actions.put(message, new ArrayList<>());
        }
        this.actions.get(message).add(action);
    }

    protected void on(String message, T type) {
        if (!this.actions.containsKey(message)) {
            return;
        }
        
        this.actions.get(message).forEach(action -> {
            action.accept(type);
        });
    }
    
}
