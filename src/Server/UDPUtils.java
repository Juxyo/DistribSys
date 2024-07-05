package Server;

import java.io.IOException;
import java.net.*;

/**
 * 
 */
public class UDPUtils {

    /**
     * Static method to send data
     */
    public static void sendData(String localHost,String message,String conversation,String clock, String host) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        message=localHost+";"+conversation+";"+message+";"+clock;
        byte[] buffer = message.getBytes();
        String[] h=host.split(":");

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, getInetAddressFromString(h[0]),Integer.parseInt(h[1]));
        socket.send(packet);
        socket.close();
    }
    public static void sendAuth(String localHost,String login,String password, String host) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String message=localHost+";"+login+";"+password;
        byte[] buffer = message.getBytes();
        String[] h=host.split(":");

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, getInetAddressFromString(h[0]),Integer.parseInt(h[1]));
        socket.send(packet);
        socket.close();
    }
    /**
     * Static method to receive data
     */
    public static String receiveData(int port) throws Exception {
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        socket.close();
        return received;
    }

    public static int getAvailablePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new RuntimeException("Unable to find an available port", e);
        }
    }

    public static InetAddress getInetAddressFromString(String ipAddress) throws UnknownHostException {
        String[] parts = ipAddress.split("\\.");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid IP address format");
        }

        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            int intVal = Integer.parseInt(parts[i]);
            if (intVal < 0 || intVal > 255) {
                throw new IllegalArgumentException("Invalid IP address format");
            }
            bytes[i] = (byte) intVal;
        }

        return InetAddress.getByAddress(bytes);
    }

}