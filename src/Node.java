import java.io.File;
import java.io.IOException;
import java.net.*;
import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;

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
	/** The files local to this machine we wish to sync.  Directory should be specified in the constructor.  */ 
	//Perhaps change this to a List<File>?
	private ArrayList<File> localFiles;
	
	/** Nodes that this object has a direct connection to */
	//Perhaps change this to a List<Node>?
	private ArrayList<Node> neighboringNodes;
	
	/**Timestamp for the purposes of determining which node is the oldest.*/
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
	/** We need a socket each for Push and Pull functions.
	 * An unassigned port we could use is 55555.*/
	ServerSocket pushSocket;
	Socket pullSocket;
	private int pushPort = 55555;
	
	//Currently dummy constructor
	public Node()
	{
		localFiles = null;
		neighboringNodes = null;
		pushSocket = null;
		pullSocket = null;
	}
	
	/**
	 * Join the network, and broadcast that this node possibly has files to share.
	 */
	public void JoinNetwork()
	{
		//Create a new ServerSocket for outbound traffic, binding it to a port.
		//Then create a new Socket for inbound traffic, to be connected later.  
		try 
		{
			pushSocket = new ServerSocket(pushPort);
			pullSocket = new Socket();
		} 
		catch (IOException e) 
		{
			System.out.println("Error opening port");
		}
	}
	
	/**
	 * Leave the network, closing any open connections and ports.
	 */
	public void LeaveNetwork()
	{
		//close connections
		try {
			pushSocket.close();
			pullSocket.close();
		} 
		catch (IOException e) 
		{
			System.out.println("Error closing port");
		}
		
	}
	
	/**
	 * Finds neighbors to directly connect to, perhaps utilizing some structure or a mesh.  
	 */
	public void FindNeighbors()
	{
		
	}
	
	/**
	 * Listens for additional nodes joining, files updating, or messages being passed through(?)
	 */
	//Not sure about this one, thoughts? -Tyler
	public void ListenForEvents()
	{
		
	}
	/**
	 * Figure out if there are any files on the network that the node is missing, then download them, adding them to the localFiles list.
	 * Conflict handling may occur here as helper functions.
	 * This should happen as the result of an event.
	 */
	public void UpdateLocalFiles()
	{
		
	}
	
}
