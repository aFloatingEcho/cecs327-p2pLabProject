import java.io.File;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;

import Network.NetworkManager;

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
	private NetworkManager networkManager;
	/** The files local to this machine we wish to sync.  Directory should be specified in the constructor.  */ 
	//Perhaps change this to a List<File>?
	private ArrayList<File> localFiles;
	
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
	 * Creates and initializes a new Node with a NetworkManager to identify it
	 * @param id This node's identifier
	 */
	public Node()
	{	
		this.networkManager = new NetworkManager();
		localFiles = null;
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
		//Create a new ServerSocket for outbound traffic, binding it to a port.
		try 
		{
			//There is another constructor that can limit how many incoming transmissions can be queued, if we deem that necessary.
			pushSocket = new ServerSocket(pushPort);
		} 
		catch (IOException e) 
		{
			System.out.println("Error opening port");
		}
		this.findNeighbors();
		//Upon joining, download new files from nodes
		//Then, push files to other nodes
	}
	
	/**
	 * Leave the network, closing any open connections and ports.
	 */
	public void leaveNetwork()
	{
		//close sockets
		try {
			pushSocket.close();
			pullSocket.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Error closing port");
		}
		
		//eliminate neighbors
		neighboringNodes = null;
		
	}
	
	/**
	 * Finds neighbors to directly connect to, perhaps utilizing some structure or a mesh.  
	 * Search for any node also running this program, then ask it for any nodes it knows and add to the list.
	 */
	public void findNeighbors()
	{
		
	}
	
	/**
	 * Connect and send a message to a targeted node
	 * @param targetID The ID of the target node; the intended recipient
	 */
	public void connectAndSendMessage(String targetID)
	{
		connectToPeer(targetID);
	}
	/**
	 * Connect to a peer for the purposes of file sharing
	 */
	public void connectToPeer(String targetID)
	{
		try 
		{
			pullSocket = pushSocket.accept();
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
	public void listenForEvents()
	{
		
	}
	/**
	 * Figure out if there are any files on the network that the node is missing, then download them, adding them to the localFiles list.
	 * Conflict handling may occur here as helper functions.
	 * This should happen as the result of an event.
	 */
	public void updateLocalFiles()
	{
		
	}
	
	public Timestamp getTimeStamp()
	{
		return this.timestamp;
	}
	
}
