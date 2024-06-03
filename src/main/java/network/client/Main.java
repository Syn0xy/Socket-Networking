package network.client;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final String SERVER_NAME = "localhost";

    private static final int SERVER_PORT = 2000;

    public static void main(String[] args) throws Exception {
        Client socket = new Client(SERVER_NAME, SERVER_PORT);
        
        socket.on("connection", () -> {
            System.out.println("Socket connectée: [id=" + socket.getID() + "]");
            
            socket.on("callback_id", (object) -> {
                System.out.println("Your id: " + (int)object);
            });
            
            socket.on("callback_player_position", (object) -> {
                int[] array = (int[])object;
                System.out.println(Arrays.toString(array));
            });
            
            new Thread(() -> chat(socket)).start();
        });
    }

    private static void chat(Client socket) {
        try (Scanner scanner = new Scanner(System.in)) {
            String userInput;
            String userMessage = "(client" + socket.getID() + "): ";

            while (true) {
                System.out.print(userMessage);
                userInput = scanner.nextLine();
                socket.emit(userInput);
            }
        } catch (Exception e) {
            System.err.println("Exception occured for scanner: " + e.getMessage());
        }
    }
    
}
