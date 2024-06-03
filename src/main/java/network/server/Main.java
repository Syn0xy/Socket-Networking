package network.server;

import java.util.Scanner;

public class Main {

    private static final int PORT = 2000;

    public static void main(String[] args) {
        Server io = new Server(PORT);

        io.on("connection", (socket) -> {
            System.out.println("Nouvelle connexion - id: " + socket.getID());

            socket.on("get_id", () -> {
                socket.emit("callback_id", socket.getID());
                System.out.println("callback_id: [" + socket.getID() + "]");
            });
            
            socket.on("get_player_position", () -> {
                int[] position = new int[] { 5, 10 };
                socket.emit("callback_player_position", position);
                System.out.println("callback_player_position: [" + position + "]");
            });
        });
        
        new Thread(() -> chat(io)).start();
    }

    private static void chat(Server io) {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;
            String userMessage = "(Server): ";

            while (true) {
                System.out.print(userMessage);
                userInput = scanner.nextLine();
                io.emit(userInput);
            }
        } catch (Exception e) {
            System.err.println("Exception occured for scanner: " + e.getMessage());
        }
    }

}
