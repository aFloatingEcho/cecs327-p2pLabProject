package Peer;

import java.io.*;
import java.net.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    // NOTE: may not need this port should be kept at small scale right now
    private final int PORT;
    private ServerSocket serverSocket;
    private List<Socket> connectedSockets;

//    private MulticastSocket mcSocketGroup;   // need for multiple peers connected to one socket

    /**
     * Default constructor
     */
    public Server() {
        this.PORT = 5555;
        this.connectedSockets = new ArrayList<>();
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
        this.connectedSockets = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Joins all available IP addresses to the ServerSocket
     * @param localIPs ArrayList<String>
     */
    public void join(List<String> localIPs) throws IOException {
        for(String address : localIPs) {
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), serverSocket.getLocalPort()));
        }
        // or keep accepting until no more connection requests via Threads
        // NOTE: Thread may be used somewhere else
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    connectedSockets.add(serverSocket.accept());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    /**
     * Receives any commands sent from another peer and returns the parsed string input
     * @param inbound Socket
     * @return String[]
     */
    public String[] receive(Socket inbound) throws IOException {
        // NOTE: this could be threaded but a thread may as well be created wherever this method is called
//        Socket inSocket = serverSocket.accept();
        DataInputStream iStream = new DataInputStream(inbound.getInputStream());
        String input = iStream.readUTF();
        String[] msg = input.split("::");
        String command = msg[0];
        String[] params = msg[1].split(" ");
        // WARN: there may be a better way of returning the command and params to the file transfer classes
        return new String[]{command, Arrays.toString(params)};
    }

    /**
     * Returns the serverSocket
     * @return ServerSocket
     */
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
