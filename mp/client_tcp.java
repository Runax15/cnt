import java.io.*;
import java.net.*;

public class client_tcp{
    public static void main(String[] args){
        try(Socket socket = new Socket("localhost", 6789);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ){
            System.out.println("Type a message: ");
            String userInput;
            while((userInput = stdIn.readLine()) != null){
                out.println(userInput);
                System.out.println("Server response: "+ in.readLine());
                System.out.println("Type a message: ");
            }
        }catch(UnknownHostException e){
            System.err.println("Unknown host: "+ e.getMessage());
        }catch(IOException e){
            System.err.println("I/O error: "+e.getMessage());
        }
    }
}