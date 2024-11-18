import java.net.*;
import java.io.*;


public class udp_client_diff {
    public static void main(String[] args) {
        DatagramSocket clientSocket = null;
        BufferedReader stdIn = null;
        try {
            clientSocket = new DatagramSocket();
            InetAddress serverIPAddress = InetAddress.getByName("localhost");
            byte[] receiveData = new byte[1024];


            stdIn = new BufferedReader(new InputStreamReader(System.in));
            String sentence;
            System.out.println("Type a message: ");


            while ((sentence = stdIn.readLine()) != null) {
                // Send data to the server
                byte[] sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, 9876);
                clientSocket.send(sendPacket);


                // Receive response from the server
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);


                // Process received data
                String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("FROM SERVER: " + modifiedSentence);
                System.out.println("Type a message: ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are properly closed
            try {
                if (stdIn != null) stdIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }
}
