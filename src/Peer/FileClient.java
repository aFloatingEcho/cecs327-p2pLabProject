package Peer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class FileClient {

	private MulticastSocket clientConnection = null;
	private String hostName = null;
	private ArrayList<String> fileList;
	private NetworkInterface networkAdapter;
	
	public static void main(String[] args) {
		
	}
	
	public FileClient(String name, int portNumber, ArrayList<String> fileList, NetworkInterface networkAdapter) throws IOException {
		this.clientConnection = new MulticastSocket(portNumber);
		this.hostName = name;
		this.fileList = fileList;
		this.networkAdapter = networkAdapter;
	}
	
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
            clientConnection.joinGroup(new InetSocketAddress(InetAddress.getByName(address), 5555), // or PORT
                    NetworkInterface.getByInetAddress(InetAddress.getByName(address)));
        }
    }
}
