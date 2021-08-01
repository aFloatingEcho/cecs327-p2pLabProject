package Network;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.net.InetAddress;

// NOTE: this class is specifically used for transferring files to peers over UDP
import java.net.MulticastSocket;

import java.util.List;

public class NetworkManager {
	
	private InetAddress INET;
	private String HOST;
	
	/**
	 * Debug
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("NETWORK");
	}
	
	/**
	 * Default Constructor for this machine's host name and address
	 */
	public NetworkManager() {
		try {
			HOST = InetAddress.getLocalHost().getHostName();
			INET = InetAddress.getByName(HOST);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Overloaded Constructor for specific host name
	 * @param host
	 */
	public NetworkManager(String host) {
		this.HOST = host;
		try {
			INET = InetAddress.getByName(HOST);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns list of all local IP addresses
	 * @return List<String>
	 */
	public List<String> getLocalIPAddresses() {
		final byte[] ip = INET.getAddress();
		for(int i = 1; i < 10; ++i) {
			try {
                ip[3] = (byte) i;   // changes byte address to iterate through local addresses
                InetAddress address = InetAddress.getByAddress(ip);   // instantiates an address
                String localIP = address.toString().substring(1);   // instantiates the string value of the address
                if (address.isReachable(5000)) {
                    System.out.println(localIP + " is on the network");   
                } else {
                    System.out.println("Not Reachable: " + localIP);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		
		return null;
	}
	
}
