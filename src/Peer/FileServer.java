package Peer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import Network.NetworkManager;

public class FileServer {
	
	private DatagramSocket serverBroadcast;
	private String hostName;
	private ArrayList<String> fileList;
	private int portNumber;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		NetworkManager network = new NetworkManager();
		List<String> allIPAddresses = network.getAllLocalIPAddresses();
		FileDirectory allFiles = new FileDirectory("sync\\");
		List<String> submit = allFiles.getFileList();
		FileServer manager = new FileServer("230.0.113.0", 5555, (ArrayList<String>) submit);
		int i = 0;
		while(i != 250000) {
			manager.broadCast();
			i++;
		}
		manager.shutdown();
	}
	
	public FileServer(String name, int portNumber, ArrayList<String> fileList) throws IOException{
		this.serverBroadcast = new MulticastSocket();
		this.portNumber = portNumber;
		this.hostName = name;
		this.fileList = fileList;
	}
	
	public void broadCast() {
		for(String eachFile : this.fileList) {
			String message = eachFile;
			byte[] brokenUp = message.getBytes();
			try {
				System.out.println("Broadcasting: " + message);
				serverBroadcast.send(new DatagramPacket(brokenUp, brokenUp.length, InetAddress.getByName(this.hostName), this.portNumber));
			} catch (IOException e) {
				System.out.println(message + " failed to send!");
				e.printStackTrace();
			}
		}
		try {
			String message = "Close";
			byte[] brokenUp = message.getBytes();
			serverBroadcast.send(new DatagramPacket(brokenUp, brokenUp.length, InetAddress.getByName(this.hostName), this.portNumber));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		this.serverBroadcast.close();
		System.out.println(hostName + " Multicast Server Shutdown!");
	}

}
