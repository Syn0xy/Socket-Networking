package network.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class SocketAction<T> extends Thread {

    private final Map<String, List<Consumer<T>>> actions;

    public SocketAction() {
        this.actions = new ConcurrentHashMap<>();
    }

    public abstract void emit(final String token);

    public abstract void emit(final String token, final Object data);

    public void on(final String token, final Consumer<T> action) {
        this.actions
            .computeIfAbsent(token, s -> new ArrayList<>())
            .add(action);
    }

    protected void on(final String token, final T type) {
        if (!this.actions.containsKey(token)) {
            return;
        }
        
        this.actions
            .get(token)
            .forEach(action -> action.accept(type));
    }
    
}
