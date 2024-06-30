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
            
            socket.on("callback_id", (data) -> {
                System.out.println("Your id: " + data.toType(int.class));
            });
            
            socket.on("callback_player_position", (data) -> {
                int[] array = data.toType(int[].class);
                System.out.println(Arrays.toString(array));
            });

            socket.on("disconnect", () -> {
                System.err.println("Socket déconnectée: [id=" + socket.getID() + "]");
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

                if (!socket.isConnect()) {
                    break;
                }
                
                socket.emit(userInput);
            }
        } catch (Exception e) {
            System.err.println("Exception occured for scanner: " + e.getMessage());
        }
    }
    
}
