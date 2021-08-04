package Peer;

import java.io.IOException;
import java.net.*;

import java.util.List;

public class Server {
    // NOTE: may not need this port should be kept at small scale right now
    private final int PORT;
    private MulticastSocket mcSocketGroup;   // need for multiple peers connected to one socket

    /**
     * Default constructor
     */
    public Server() {
        this.PORT = 5555;
        try {
            mcSocketGroup = new MulticastSocket(5555); // or PORT
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overload constructor for a specified port number
     * @param port int
     */
    public Server(int port) {
        this.PORT = port;
        try {
            mcSocketGroup = new MulticastSocket(PORT);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Overload constructor for a pre-existing MulticastSocket
     * @param mcSocket MulticastSocket
     */
    public Server(MulticastSocket mcSocket) {
        this.PORT = mcSocket.getPort();
        mcSocketGroup = mcSocket;
    }

    /**
     * Joins all available IP addresses to the MulticastSocket
     * @param localIPs ArrayList<String>
     */
    public void join(List<String> localIPs) throws IOException {
        for(String address : localIPs) {
            // NOTE: (IDK) this does not cover for Gavin's multiple addresses for one machine solution
            // deprecated
            mcSocketGroup.joinGroup(InetAddress.getByName(address));
            // alternative, joinGroup(SocketAddress, NetworkInterface), InetSocketAddress extends SocketAddress
            mcSocketGroup.joinGroup(new InetSocketAddress(InetAddress.getByName(address), 5555), // or PORT
                    NetworkInterface.getByInetAddress(InetAddress.getByName(address)));
        }
    }

    /**
     * Receives any commands sent from another peer
     */
    public void receive() throws IOException {
        // NOTE: this code is temporary and is from the Java MulticastSocket documentation
        //       its purpose for being here to display an example of DatagramPackets
        //       possibly useful for message to a peer that files were sent
        byte[] buffer = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buffer, buffer.length);
        // NOTE: receive is an inherited method from DatagramSocket
        mcSocketGroup.receive(recv);
    }

    public MulticastSocket getMcSocketGroup() {
        return mcSocketGroup;
    }
}
