package network.server;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    private static final int PORT = 2000;

    public static void main(String[] args) {
        Server io = new Server(PORT);

        io.on("connection", (socket) -> {
            System.out.println("Nouvelle connexion - id: " + socket.getID());
            
            socket.on("all", () -> {
                System.out.println("pour all [" + socket.getID() + "]");
            });

            socket.on("test", () -> {
                System.out.println("pour moi [" + socket.getID() + "]");
            });

            socket.emit("test");
        });

        timeout(20 * 1000, () -> io.emit("all"));
    }

    private static void timeout(long delay, Runnable runnable) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, delay);
    }
    
}
