import java.io.*;
import java.net.*;
import java.util.Scanner;


public class UDP_Client_file {
    private static final String IP_ADDRESS = "127.0.0.1"; // localhost
    private static final int PORT_NO = 15050;
    private static final int NET_BUF_SIZE = 32;
    private static final char cipherKey = 'S';


    // function to clear buffer
    private static void clearBuf(byte[] buffer) {
        for (int i = 0; i < buffer.length; i++)
            buffer[i] = 0;
    }


    // function for decryption
    private static byte Cipher(byte ch) {
        return (byte) (ch ^ cipherKey);
    }


    // function to receive file
    private static boolean recvFile(byte[] buffer, int size) {
        for (int i = 0; i < size; i++) {
            byte ch = Cipher(buffer[i]);
            if (ch == -1)
                return true;
            else
                System.out.print((char) ch);
        }
        return false;
    }


    // driver code
    public static void main(String[] args) {
        DatagramSocket sockfd = null;
        try {
            sockfd = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(IP_ADDRESS);
            Scanner scanner = new Scanner(System.in);


            byte[] net_buf = new byte[NET_BUF_SIZE];


            while (true) {
                System.out.println("Please enter file name to receive:");
                String filename = scanner.nextLine().trim();
                if (filename.isEmpty()) {
                    System.out.println("Invalid file name. Please try again.");
                    continue;
                }
               
                net_buf = filename.getBytes();


                DatagramPacket sendPacket = new DatagramPacket(net_buf, net_buf.length, ip, PORT_NO);
                sockfd.send(sendPacket);


                System.out.println("---------Data Received---------");


                while (true) {
                    clearBuf(net_buf);
                    DatagramPacket receivePacket = new DatagramPacket(net_buf, net_buf.length);
                    sockfd.receive(receivePacket);


                    if (recvFile(net_buf, NET_BUF_SIZE)) {
                        break;
                    }
                }


                System.out.println("\n-------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sockfd != null && !sockfd.isClosed()) {
                sockfd.close();
            }
        }
    }
}
