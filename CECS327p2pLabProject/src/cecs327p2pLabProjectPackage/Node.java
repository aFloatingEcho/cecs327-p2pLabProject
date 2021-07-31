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
	private File[] localFiles;
	
	/** Nodes that this object has a direct connection to */
	//Perhaps change this to a List<Node>?
	private Node[] neighboringNodes;
	
	//Currently dummy constructor
	public Node()
	{
		localFiles = null;
		neighboringNodes = null;
	}
	
	
	
}
