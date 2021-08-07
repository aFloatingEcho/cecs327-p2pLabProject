import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;

import Network.NetworkManager;
import chatThings.ChatClient;
import chatThings.ChatServer;

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

	/** Nodes that this object has a direct connection to */
	private ArrayList<Node> neighboringNodes;
	private ArrayList<String> localReachableIPs;
	// Hardcoded Port Used for all addresses
	private int mainPort = 5555;
//	private ArrayList<InetAddress> localReachableIPs;

	/**Timestamp for the purposes of determining which node is the oldest.*/
	private Timestamp timestamp;

	/**
	 * Creates and initializes a new Node with a NetworkManager and files local to it.  Currently, local files default to 
	 * the project folder
	 */
	public Node()
	{
		this.networkManager = new NetworkManager();
		neighboringNodes = new ArrayList<>();
		try {
			this.localReachableIPs = (ArrayList<String>) networkManager.getAllLocalIPAddresses();
//			this.localReachableIPs = (ArrayList<InetAddress>) networkManager.getAllLocalIPAddresses();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new ChatServer(this.mainPort)).start();
		// use for the initial user input, then never ask for user input again
//		new Thread(new ChatServer(port, "sync\\")).start();
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Connects a new Client and a new Server for communication.
	 * @throws IOException 
	 */
	public void connect(String host, int port) throws IOException {
			new Thread(new ChatClient(host, port)).start();
//			for(InetAddress address : localReachableIPs) {
//				new Thread(new ChatClient(address.getHostName(), port)).start();
//			}
	}
	
	/**
	 * Leave the network, closing any open connections and ports.
	 */
	public void leaveNetwork() {
		
	}
	
	/**
	 * Finds neighbors to directly connect to, perhaps utilizing some structure or a mesh.  
	 * Search for any node also running this program, then ask it for any nodes it knows and add to the list.
	 */
	public void findNeighbors(int port)
	{
		/*This method needs to broadcast to the network looking for nodes also running this program.  When it finds one, it should ask
		//that node for the details of other nodes connected to the network.   */
		for(String eachAddress: this.localReachableIPs) {
			try {
				this.connect(eachAddress, port);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Connect and send a message to a targeted node
	 * @param targetID The ID of the target node; the intended recipient
	 */
	public void connectAndSendMessage(String targetID) {}
	/**
	 * Connect to a peer for the purposes of file sharing
	 */
	public void connectToPeer(Node targetNode) {}

	/**
	 * Listens for additional nodes joining, files updating, or messages being passed through(?)
	 */
	public void listenForEvents() {}

	/**
	 * Figure out if there are any files on the network that the node is missing, then download them, adding them to the localFiles list.
	 * Conflict handling may occur here as helper functions.
	 * This should happen as the result of an event.
	 */
	public void updateLocalFiles() {}
	
	public Timestamp getTimeStamp()
	{
		return this.timestamp;
	}
}
