package Peer;

import java.io.*;
import java.net.*;

import java.util.List;

public class Server {
    // NOTE: may not need this port should be kept at small scale right now
    private final int PORT;
    private ServerSocket serverSocket;

//    private MulticastSocket mcSocketGroup;   // need for multiple peers connected to one socket

    /**
     * Default constructor
     */
    public Server() {
        this.PORT = 5555;
        try {
            serverSocket = new ServerSocket(PORT);

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
            serverSocket = new ServerSocket(PORT);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * Overload constructor for a pre-existing MulticastSocket
//     * @param mcSocket MulticastSocket
//     */
//    public Server(MulticastSocket mcSocket) {
//        this.PORT = mcSocket.getPort();
//        mcSocketGroup = mcSocket;
//    }

    /**
     * Joins all available IP addresses to the MulticastSocket
     * @param localIPs ArrayList<String>
     */
    public void join(List<String> localIPs) throws IOException {
        for(String address : localIPs) {
            // NOTE: (IDK) this does not cover for Gavin's multiple addresses for one machine solution
            // deprecated
            // mcSocketGroup.joinGroup(InetAddress.getByName(address));

            // alternative, joinGroup(SocketAddress, NetworkInterface), InetSocketAddress extends SocketAddress
//            mcSocketGroup.joinGroup(new InetSocketAddress(InetAddress.getByName(address), 5555), // or PORT
//                    NetworkInterface.getByInetAddress(InetAddress.getByName(address)));

            // ServerSocket implementation b/c MulticastSocket is complicated
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), serverSocket.getLocalPort()));
            // or keep accepting until no more connection requests
            receive(serverSocket.accept());
        }
    }

    /**
     * Receives any commands sent from another peer
     */
    public void receive(Socket inbound) throws IOException {
        // NOTE: input means the message coming INto the server
        FileInputStream fileInput = (FileInputStream) inbound.getInputStream();   // or can use InputStream
        BufferedInputStream bufferInput = new BufferedInputStream(fileInput);
        byte[] bufBytes = new byte[Integer.MAX_VALUE];
        int bytesRead = bufferInput.read(bufBytes, 0, bufBytes.length);

        // i'll stop here since going any further is out of the scope of this class
        // however, if we can read commands, this method can possibly be used to read a select file
        // from another peer who may or may not have the file
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
