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
	
	/** The files local to this machine we wish to sync.  Directory should be specified in the constructor.  */ 
	//Perhaps change this to a List<File>?  Perhaps unneccesary?
	//private ArrayList<File> localFiles;
	
	/** Nodes that this object has a direct connection to */
	//Perhaps change this to a List<Node>?
	private ArrayList<Node> neighboringNodes;
	
	/**Timestamp for the purposes of determining which node is the oldest.*/
	private Timestamp timestamp;
	
	/** We need a socket each for Push and Pull functions.
	 * An unassigned port we could use is 55555.*/
	private ServerSocket pushSocket;
	private Socket pullSocket;
	private static int pushPort = 55555;
	
	/**
	 * Creates and initializes a new Node with a NetworkManager and files local to it.  Currently, local files default to 
	 * the project folder
	 */
	public Node()
	{
		this.networkManager = new NetworkManager();
		this.client = new Client();
		this.server = new Server();

		//localFiles = null;
		neighboringNodes = null;
		pushSocket = null;
		pullSocket = null;
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Join the network, and broadcast that this node possibly has files to share.
	 */
	public void joinNetwork()
	{
//		//Create a new ServerSocket for outbound traffic, binding it to a port.
//		try
//		{
//			//There is another constructor that can limit how many incoming transmissions can be queued, if we deem that necessary.
//			pushSocket = new ServerSocket(pushPort);
//		}
//		catch (IOException e)
//		{
//			System.out.println("Error opening port");
//		}
//		this.findNeighbors();
//		//Upon joining, push new files out to other nodes
//		//Then, download other files

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
//		//close sockets
//		try {
//			pushSocket.close();
//			pullSocket.close();
//		}
//		catch (IOException e)
//		{
//			System.out.println("Error closing port");
//		}
//
//		//eliminate neighbors
//		neighboringNodes = null;

		if(!client.leave(server.getMcSocketGroup()))
			System.err.println("Couldn't leave the network.");
	}
	
	/**
	 * Finds neighbors to directly connect to, perhaps utilizing some structure or a mesh.  
	 * Search for any node also running this program, then ask it for any nodes it knows and add to the list.
	 */
	public void findNeighbors()
	{
		// NOTE: out of scope of this class, NetworkManager handles this action
	}
	
	/**
	 * Connect and send a message to a targeted node
	 * @param targetID The ID of the target node; the intended recipient
	 */
	public void connectAndSendMessage(String targetID)
	{
		// NOTE: this should broadcast to multiple peers in the network
		//       whether a peer chooses to receive what was sent is their decision
		connectToPeer(targetID, null);
	}

	// NOTE: this may not be necessary since our goal is to be connected to devices found on the network
	//       not to a device which we must manually input
	//       nonetheless, there is a join method in the Client class to join a pre-existing network
	/**
	 * Connect to a peer for the purposes of file sharing
	 */
	public void connectToPeer(String targetID, MulticastSocket mcSocket)
	{
		// need to connect to multiple peers, or connect this machine to a network of peers
		try
		{
//			pullSocket = pushSocket.accept();

			// a client may be joined to multiple multicast sockets
			client.join(mcSocket);
		}
		catch (IOException e)
		{
			System.out.println("Connection failed.");
		}
	}

	/**
	 * Listens for additional nodes joining, files updating, or messages being passed through(?)
	 */
	//Not sure about this one, thoughts? -Tyler
	// Yea, I don't think we need this one, seems like a FileDirectory action. -James
	public void listenForEvents()
	{
		// NOTE: this action should be handled by the FileDirectory class
	}

	/**
	 * Receives any file data that may be sent
	 */
	public void receiveFile() throws IOException {
		server.receive();
	}

	/**
	 * Sends a file to the nodes subscribed to the MulticastSocket group
	 * @param inputFile File
	 */
	public void sendFile(File inputFile) {
		client.send(server.getMcSocketGroup(), inputFile);
	}


	
	public Timestamp getTimeStamp()
	{
		return this.timestamp;
	}
	
}
