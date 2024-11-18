import java.io.*;
import java.net.*;

public class Client_diff_tcp {
    public static void main(String[] args) {
        // Change "server-ip-address" to the actual server machine's IP address
        String serverAddress = "server-ip-address";
        int port = 6789;

        try (Socket socket = new Socket(serverAddress, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server at " + serverAddress + ":" + port);
            System.out.println("Type a message to send to the server: ");
            
            String userInput;
            // Read user input and send to server
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput); // Send message to the server
                System.out.println("Server response: " + in.readLine()); // Print server's reply
                System.out.println("Type another message: ");
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
