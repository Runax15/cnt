import java.io.*;
import java.net.*;
public class Server_diff_tcp {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6789)) {
            System.out.println("Server started and listening on port 6789");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("Client connected");
                    String inputLine;
                    // Read data from the client and send responses
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Received: " + inputLine);
                        out.println("Echo: " + inputLine);
                    }
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


