package Peer;

import java.net.Socket;
import java.net.MulticastSocket;

import java.util.ArrayList;

public class Client {
    // NOTE: may not need these b/c socket is the same and port should be kept at small scale right now
//    private final int PORT;   // port used for sending commands
//    private final Socket SOCKET;   // socket use for transferring commands

    /**
     * Default constructor
     */
    public Client() {}

    // NOTE: may not need all these constructors
    /**
     * Overloaded constructor for specified port number
     * @param port int
     */
    public Client(int port) {}

    /**
     * Overloaded constructor for specified port number and Socket
     * @param port int
     * @param socket Socket
     */
    public Client(int port, Socket socket) {}

    /**
     * Join to the specified MulticastSocket
     * @param mcSocket MulticastSocket
     */
    public void join(MulticastSocket mcSocket) {}

    /**
     * Send commands to other peers
     */
    public void send() {}

//    public int getPORT() {
//        return PORT;
//    }

//    public Socket getSOCKET() {
//        return SOCKET;
//    }
}
