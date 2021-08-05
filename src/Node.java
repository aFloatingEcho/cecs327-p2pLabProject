import java.io.File;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;

import Network.NetworkManager;
import Peer.*;

/**
 * A Node on our Peer to Peer Network.  Should begin with a list of local files from a given directory.
 * Needs to discover and connect to other nodes on the network, then sync files together so they all have the same contents.
 * File Operations:  Needs to send, receive, and store files.
 * Discovery: Needs to join and leave a network of an unknown number of nodes.
 * File Sync: Based on events, such as when a new node joins the network, sync files, resolving conflicts in a standardized way.
 * 
 */
public class Node 
{
	
	//private FileDirectory fileDirectory;
	private NetworkManager networkManager;

	private final Client client;
	private final Server server;

	/**Timestamp for the purposes of determining which node is the oldest.*/
	private Timestamp timestamp;

	/**
	 * Creates and initializes a new Node with a NetworkManager and files local to it.  Currently, local files default to 
	 * the project folder
	 */
	public Node()
	{
		this.networkManager = new NetworkManager();
		this.client = new Client();
		this.server = new Server();

		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Join the network, and broadcast that this node possibly has files to share.
	 */
	public void joinNetwork()
	{
		try {
			server.join(networkManager.getAllLocalIPAddresses());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Leave the network, closing any open connections and ports.
	 */
	public void leaveNetwork()
	{
		// NOTE: without MulticastSocket this is not necessary since Socket can call close()
	}

	// NOTE: this may not be necessary since our goal is to be connected to devices found on the network
	//       not to a device which we must manually input
	/**
	 * Connect to a peer for the purposes of file sharing
	 */
	public void connectToPeer(String targetID)
	{
		// need to connect to multiple peers, or connect this machine to a network of peers
		try
		{
			// a client may be joined to multiple multicast sockets
			client.join(new InetSocketAddress(InetAddress.getByName(targetID), server.getServerSocket().getLocalPort()));
		}
		catch (IOException e)
		{
			System.out.println("Connection failed.");
		}
	}

//	/**
//	 * Receives any file data that may be sent
//	 */
//	public void receiveFile() throws IOException {
//		server.receive();
//	}

//	/**
//	 * Sends a file to the nodes subscribed to the MulticastSocket group
//	 * @param inputFile File
//	 */
//	public void sendFile(File inputFile) {
//		client.send(server.getMcSocketGroup(), inputFile);
//	}

	public Timestamp getTimeStamp()
	{
		return this.timestamp;
	}
}
