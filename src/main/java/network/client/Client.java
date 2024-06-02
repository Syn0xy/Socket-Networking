package network.client;

import java.net.Socket;
import java.util.Scanner;
  
 public class Client extends Thread {

    private String serverName;

    private int serverPort;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.start();
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(this.serverName, this.serverPort)) {
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();
            
            clientThread.on("test", () -> {
                System.out.println("client -> un test");
            });
            
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Enter your name:");
                String clientName = "empty";
                String userInput = scanner.nextLine();
                clientName = userInput;
                String userMessage = "(" + clientName + ")" + ": ";
                
                do {
                    System.out.print(userMessage);
                    userInput = scanner.nextLine();
                    clientThread.emit(userInput);
                } while (!userInput.equals("exit"));
            } catch (Exception e) {
                System.err.println("Exception occured for scanner: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Exception occured in client main: " + e.getMessage());
        }
    }

}