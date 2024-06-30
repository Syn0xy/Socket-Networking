package network.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class SocketAction<T> extends Thread {

    private Map<String, List<Consumer<T>>> actions;

    public SocketAction() {
        this.actions = new ConcurrentHashMap<>();
    }

    public abstract void emit(String token);

    public abstract void emit(String token, Object data);

    public void on(String token, Consumer<T> action) {
        if (!this.actions.containsKey(token)) {
            this.actions.put(token, new ArrayList<>());
        }
        this.actions.get(token).add(action);
    }

    protected void on(String token, T type) {
        if (!this.actions.containsKey(token)) {
            return;
        }
        
        this.actions.get(token).forEach(action -> {
            action.accept(type);
        });
    }
    
}
