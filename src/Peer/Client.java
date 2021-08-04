package Peer;

import java.io.IOException;
import java.net.*;

import java.util.ArrayList;

public class Client {
    // NOTE: may not need these b/c socket is the same and port should be kept at small scale right now
    private final int PORT;   // port used for sending commands
    private final Socket SOCKET;   // socket use for transferring commands

    /**
     * Default constructor
     */
    public Client() {
        this.PORT = 5555;
        this.SOCKET = new Socket();
    }

    // NOTE: may not need all these constructors
    /**
     * Overloaded constructor for specified port number
     * @param port int
     */
    public Client(int port) {
        this.PORT = port;
        this.SOCKET = new Socket();
    }

    /**
     * Overloaded constructor for specified port number and Socket
     * @param port int
     * @param socket Socket
     */
    public Client(int port, Socket socket) {
        this.PORT = port;
        this.SOCKET = socket;
    }

    /**
     * Join to the specified MulticastSocket
     * @param mcSocket MulticastSocket
     */
    public void join(MulticastSocket mcSocket) throws IOException {
        // NOTE: this does not cover for Gavin's multiple addresses for one machine solution
        // deprecated
        mcSocket.joinGroup(InetAddress.getLocalHost());
        // alternative, joinGroup(SocketAddress, NetworkInterface), InetSocketAddress extends SocketAddress
        mcSocket.joinGroup(new InetSocketAddress(InetAddress.getLocalHost(), 5555),
                NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
    }

    /**
     * Send commands to other peers
     * Returns true if successful, else false
     * @return boolean
     */
    public boolean send(MulticastSocket mcSocket) {
        // NOTE: FileDirectory/Stream stuff should be called here then the respective classes will do the magic
        //       and may want to use Threads in here or whatever class will be calling this method
        return false;
    }

    /**
     * The current client leaves the MulticastSocket group
     * Return true if successful, else false
     * @return boolean
     */
    public boolean leave(MulticastSocket mcSocket) {
        try {
            // deprecated
            mcSocket.leaveGroup(InetAddress.getLocalHost());
            // alternative, leaveGroup(SocketAddress, NetworkInterface), InetSocketAddress extends SocketAddress
            mcSocket.leaveGroup(new InetSocketAddress(InetAddress.getLocalHost(), 5555),
                    NetworkInterface.getByInetAddress(InetAddress.getLocalHost()));
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

//    public int getPORT() {
//        return PORT;
//    }

//    public Socket getSOCKET() {
//        return SOCKET;
//    }
}
