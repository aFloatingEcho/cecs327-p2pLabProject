package Peer;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.MulticastSocket;

import java.util.ArrayList;

public class Server {
    // NOTE: may not need this port should be kept at small scale right now
//    private final int PORT;
    private MulticastSocket mcGroup;   // need for multiple peers connected to one socket

    /**
     * Default constructor
     */
    public Server() {}

    /**
     * Overload constructor for a specified port number
     * @param port int
     */
    public Server(int port) {}

    /**
     * Overload constructor for a specified port number and pre-existing MulticastSocket
     * @param port int
     * @param mcsocket MulticastSocket
     */
    public Server(int port, MulticastSocket mcsocket) {}

    /**
     * Joins all available IP addresses to the MulticastSocket
     * @param localIPs ArrayList<String>
     */
    public void join(ArrayList<String> localIPs) {}

    /**
     * Receives any commands sent from another peer
     */
    public void receive() {}
}
