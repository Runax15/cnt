import java.net.*;


public class udp_server_diff {
    public static void main(String[] args) {
        DatagramSocket serverSocket = null;
        try {
            // Initialize server socket
            serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];


            System.out.println("UDP Server started and listening on port 9876");


            while (true) {
                // Prepare packet to receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);


                // Extract data from received packet
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("RECEIVED: " + receivedMessage);


                // Prepare response data
                String responseMessage = "Echo: " + receivedMessage;
                byte[] sendData = responseMessage.getBytes();
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();


                // Send response to client
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure the socket is closed
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }
}


