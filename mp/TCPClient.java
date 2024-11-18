import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Connect to the server at a specific IP address and port
            Socket socket = new Socket("192.168.170.95", 65432);  
            System.out.println("Connected to the server!");

            // Send file to server
            File file = new File("sql_commands.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            OutputStream outputStream = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close resources
            fileInputStream.close();
            outputStream.close();
            socket.close();
            System.out.println("File sent successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}