package Network;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InetAddress;

// NOTE: this class is specifically used for transferring files to peers over UDP
import java.net.MulticastSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.net.NetworkInterface;

public class NetworkManager {
	
	private Enumeration<NetworkInterface> INET;
	
	/**
	 * Default Constructor for this machine's host name and address
	 */
	public NetworkManager() {
		try {
			this.INET = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function that cycles through all itnerfaces of a System.
	 * @return
	 * @throws InterruptedException
	 */
	public List<String> getAllLocalIPAddresses() throws InterruptedException{
		List<String> allLocalIPs = new ArrayList<>();
		for(NetworkInterface eachAddress : Collections.list(this.INET)) {
			Enumeration<InetAddress> NetworkAddresses = eachAddress.getInetAddresses();
			System.out.println("Finding All Addresses on " + eachAddress.getDisplayName() + " on " + eachAddress);
			for(InetAddress networkAddress : Collections.list(NetworkAddresses)) {
				allLocalIPs.addAll(this.getLocalIPAddresses(networkAddress));
			}
		}
		return allLocalIPs;
	}
	
	/**
	 * Returns list of all local IP addresses for a particular interface
	 * @return List<String>
	 */
	public List<String> getLocalIPAddresses(InetAddress networkAddress) throws InterruptedException {
		List<String> localIPs = new ArrayList<>();
		final byte[] ip = networkAddress.getAddress();
		ExecutorService es = Executors.newFixedThreadPool(255);   // 255 = threads per network address
		for(int i = 1; i < 255; ++i) {
			int finalI = i;   // need temp variable for inside the thread b/c can't modify the iterating variable
			es.submit(new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ip[3] = (byte) finalI;   // changes byte address to iterate through local addresses
						InetAddress address = InetAddress.getByAddress(ip);   // instantiates an address
						String localIP = address.toString().substring(1);   // instantiates the string value of the address
						if (address.isReachable(5000)) {
							localIPs.add(localIP);
							System.out.println(localIP + " is on the network");
						}
					} catch (Exception e) {
//						e.printStackTrace();
					}
				}
			}));
		}

		es.awaitTermination(10000L, TimeUnit.MILLISECONDS);   // timeout may miss some thread completions, but avoids waiting forever
//		es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);   // waits forever
		es.shutdown();
		return localIPs;
	}
	
}
