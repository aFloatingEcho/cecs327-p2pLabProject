package Peer;

import java.io.*;
import java.net.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Client sends commands to server(s) regarding the context of file transfers.
 * Client does NOT send files, instead it may send a request to a server node for a file.
 * Any requests are satisfied by the FileDirectory class.
 * Client is merely one half of a chat program between two nodes.
 */
public class Client {
    // NOTE: may not need these b/c socket is the same and port should be kept at small scale right now
    private final int PORT;   // port used for sending commands
    private final Socket cSocket;

    /**
     * Default constructor
     */
    public Client() {
        this.PORT = 5555;
        this.cSocket = new Socket();
    }

    // NOTE: may not need all these constructors
    /**
     * Overloaded constructor for specified port number
     * @param port int
     */
    public Client(int port) {
        this.PORT = port;
        this.cSocket = new Socket();
    }

    /**
     * Overloaded constructor for specified port number and Socket
     * @param socket Socket
     */
    public Client(Socket socket) throws IOException {
        this.PORT = socket.getPort();
        if(!socket.isClosed()) socket.close();   // WARN: will this error for getting the InetAddress if closed?
        this.cSocket = new Socket(socket.getInetAddress(), PORT);   // cannot copy sockets, must create a new one
//        this.cSocket = socket;
    }

    /**
     * Join to the specified SocketAddress server
     * SocketAddress contains the InetAddress and the Port Number
     * @param serverAddress SocketAddress
     */
    public void join(SocketAddress serverAddress) throws IOException {
        if(!cSocket.isConnected())
            cSocket.connect(serverAddress);   // connect to server endpoint
//        else {
//            cSocket.bind(socketAddress);   // bind to a different endpoint
//        }
    }

    private boolean send(Socket socket, String command) {
        try {
            DataOutputStream oStream = new DataOutputStream(socket.getOutputStream());
            // consider using this if a param array is necessary
//            byte[] bytes = command.getBytes(StandardCharsets.UTF_8);
            oStream.writeUTF(command);
            oStream.flush();
            oStream.close();
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(String directoryPath) {
        String command = new String("UPDATE::" + directoryPath);
        if(!send(cSocket, command)) new Exception("Request not sent.").printStackTrace();
    }

}
