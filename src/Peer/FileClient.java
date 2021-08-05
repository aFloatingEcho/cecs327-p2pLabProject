package Peer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import Network.NetworkManager;
import Peer.FileDirectory;

public class FileClient {

	private MulticastSocket clientConnection = null;
	private ArrayList<String> fileList;
	private NetworkInterface networkAdapter;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		NetworkManager network = new NetworkManager();
		List<String> allIPAddresses = network.getAllLocalIPAddresses();
		FileDirectory allFiles = new FileDirectory("sync\\");
		List<String> submit = allFiles.getFileList();
		FileClient manager = new FileClient(5555, (ArrayList<String>) submit);
		manager.join(allIPAddresses);
		manager.recieve();
		manager.closed();
	}
	
	public FileClient(int portNumber, ArrayList<String> fileList) throws IOException {
		this.clientConnection = new MulticastSocket(portNumber);
		this.fileList = fileList;
	}
	
	 /**
     * Joins all available IP addresses to the MulticastSocket
     * @param localIPs ArrayList<String>
     * @throws InterruptedException 
     */
    public void join(List<String> localIPs) throws InterruptedException {
    	ExecutorService es = Executors.newFixedThreadPool(localIPs.size());
        for(String address : localIPs) {
        	es.submit(new Thread(new Runnable() {
				@Override
				public void run() {
					// NOTE: (IDK) this does not cover for Gavin's multiple addresses for one machine solution
		            // deprecated
		            // mcSocketGroup.joinGroup(InetAddress.getByName(address));
		            // alternative, joinGroup(SocketAddress, NetworkInterface), InetSocketAddress extends SocketAddress
		            try {
						clientConnection.joinGroup(new InetSocketAddress(InetAddress.getByName(address), 5555), // or PORT
						        NetworkInterface.getByInetAddress(InetAddress.getByName(address)));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SocketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
        		
        	}));
        }
        es.awaitTermination(10000L, TimeUnit.MILLISECONDS);
        es.shutdown();
    }
    
    public void recieve() {
    	String recieved = null;
    	while (!recieved.equals("Closed")) {
    		DatagramPacket inbound = null;
    	    byte[] buf = new byte[256];
            inbound = new DatagramPacket(buf, buf.length);
            try {
				clientConnection.receive(inbound);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String decode = new String(inbound.getData(), 0, inbound.getLength());
            recieved = decode;
            System.out.println(recieved);
    	}
    }
    
    public void closed() {
    	this.clientConnection.close();
    }
}
