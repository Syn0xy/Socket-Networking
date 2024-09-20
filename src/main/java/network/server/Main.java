package network.server;

import java.util.Scanner;

import network.model.SocketThread;

public class Main {

    private static final int PORT = 2000;

    public static void main(final String[] args) {
        final Server io = new Server(PORT);

        io.on("connection", Main::handleSocketConnection);
        
        new Thread(() -> Main.chat(io)).start();
    }

    private static void handleSocketConnection(final SocketThread socket) {
        System.out.println("Nouvelle connexion - id: " + socket.getID());

        socket.on("get_id", () -> {
            socket.emit("callback_id", socket.getID());
            System.out.println("callback_id: [" + socket.getID() + "]");
        });
        
        socket.on("get_player_position", () -> {
            final int[] position = new int[] { 5, 10 };
            socket.emit("callback_player_position", position);
            System.out.println("callback_player_position: [" + position + "]");
        });

        socket.on("disconnect", () -> {
            System.out.println("Socket deconnectée: " + socket.getID() + " !");
        });
    }

    private static void chat(final Server io) {
        try (Scanner scanner = new Scanner(System.in)) {
            final String userMessage = "(Server): ";
            String userInput;

            while (true) {
                System.out.print(userMessage);
                userInput = scanner.nextLine();
                io.emit(userInput);
            }
        } catch (final Exception e) {
            System.err.println("Exception occured for scanner: " + e.getMessage());
        }
    }

}
