import java.io.*;
import java.net.*;


public class Udp_Server_file {
    private static final int PORT_NO = 15050;
    private static final int NET_BUF_SIZE = 32;
    private static final char cipherKey = 'S';
    private static final String nofile = "File Not Found!";


    // function to clear buffer
    private static void clearBuf(byte[] buffer) {
        for (int i = 0; i < buffer.length; i++)
            buffer[i] = 0;
    }


    // function to encrypt
    private static byte Cipher(byte ch) {
        return (byte) (ch ^ cipherKey);
    }


    // function sending file
    private static boolean sendFile(FileInputStream fis, byte[] buffer, int size) throws IOException {
        int i, len;
        if (fis == null) {
            byte[] nofileBytes = nofile.getBytes();
            len = nofileBytes.length;
            System.arraycopy(nofileBytes, 0, buffer, 0, len);
            buffer[len] = (byte) -1;
            for (i = 0; i <= len; i++)
                buffer[i] = Cipher(buffer[i]);
            return true;
        }


        int ch;
        for (i = 0; i < size; i++) {
            ch = fis.read();
            if (ch == -1) {
                buffer[i] = Cipher((byte) -1);
                return true;
            }
            buffer[i] = Cipher((byte) ch);
        }
        return false;
    }


    // driver code
    public static void main(String[] args) {
        DatagramSocket sockfd = null;
        try {
            sockfd = new DatagramSocket(PORT_NO);
            System.out.println("Server started. Waiting for file name...");


            byte[] net_buf = new byte[NET_BUF_SIZE];
            DatagramPacket packet = new DatagramPacket(net_buf, net_buf.length);


            while (true) {
                clearBuf(net_buf);
                sockfd.receive(packet);


                String fileName = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println("File Name Received: " + fileName);


                File file = new File(fileName);
                FileInputStream fis = null;


                if (file.exists() && file.isFile()) {
                    try {
                        fis = new FileInputStream(file);
                        System.out.println("File Successfully opened!");
                    } catch (FileNotFoundException e) {
                        System.out.println("File open failed: " + e.getMessage());
                    }
                } else {
                    System.out.println("File not found or not a file.");
                }


                while (true) {
                    if (sendFile(fis, net_buf, NET_BUF_SIZE)) {
                        sockfd.send(new DatagramPacket(net_buf, NET_BUF_SIZE, packet.getAddress(), packet.getPort()));
                        break;
                    }
                    sockfd.send(new DatagramPacket(net_buf, NET_BUF_SIZE, packet.getAddress(), packet.getPort()));
                    clearBuf(net_buf);
                }


                if (fis != null) {
                    fis.close();
                }
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
