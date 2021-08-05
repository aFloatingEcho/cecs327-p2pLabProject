package Peer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FileServer {
	
	private MulticastSocket serverBroadcast;
	private String hostName;
	private ArrayList<String> fileList;
	
	public FileServer(String name, int portNumber, ArrayList<String> fileList) throws IOException{
		this.serverBroadcast = new MulticastSocket(portNumber);
		this.hostName = name;
		this.fileList = fileList;
	}
	
	public void broadCast() {
		for(String eachFile : this.fileList) {
			String message = eachFile;
			byte[] brokenUp = message.getBytes();
			try {
				serverBroadcast.send(new DatagramPacket(brokenUp, brokenUp.length, this.serverBroadcast.getInetAddress(), this.serverBroadcast.getPort()));
			} catch (IOException e) {
				System.out.println(message + " failed to send!");
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		this.serverBroadcast.close();
		System.out.println(hostName + " Multicast Server Shutdown!");
	}

}
