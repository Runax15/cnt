import java.io.*;
import java.net.*;

public class server_tcp{
public static void main(String[] args){
    int port = 6789;

    try (ServerSocket serversocket = new ServerSocket(port)) {
        System.out.println("Server is running and waiting for client connections on port "+port);
        
        while (true){
            Socket clientsocket = serversocket.accept();
            System.out.println("Client connected: "+clientsocket.getInetAddress());
            
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(),true))
            {

                String clientMessage;
                while((clientMessage = in.readLine()) != null){
                    System.out.println("Received form client: "+clientMessage);

                    out.println("Server received: "+clientMessage);
                }
            }catch(IOException e){
                    System.err.println("Error communicating with client: "+e.getMessage());
                }    
        }
    }catch (IOException e){
        System.err.println("Could not start server: "+e.getMessage());
    }
}
}