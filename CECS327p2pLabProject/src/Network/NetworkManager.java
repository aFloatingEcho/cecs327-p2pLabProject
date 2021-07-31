package Network;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetAddress;

// NOTE: this class is specifically used for transferring files to peers over UDP
import java.net.MulticastSocket;

public class NetworkManager {
	
	private final String HOST;
	
	public NetworkManager() {
		HOST = InetAddress.getHostName();
	}
	
	public NetworkManager(String host) {
		this.HOST = host;
//		NetworkManager();
	}
	
	public List<String> getLocalIPAddresses() {
		InetAddress host_ip = InetAddress.getByName(HOST);
		for(int i = 1; i < 10; ++i) {
//			try {
//                ip[3] = i;
//                InetAddress address = InetAddress.getByAddress(ip);
//                String output = address.toString().substring(1);
//                if (address.isReachable(5000)) {
//                    System.out.println(output + " is on the network");
//                } else {
//                    System.out.println("Not Reachable: "+output);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
		}
		
		return null;
	}
	
}
