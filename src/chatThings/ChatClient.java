package chatThings;

import fileDirectory.FileDirectory;

import java.io.*;
import java.net.*;

public class ChatClient implements Runnable
{
	private Socket clientSocket;
	private PrintWriter out; 
	private BufferedReader socketIn;
	private BufferedReader systemIn;
	private chatClientParser parser;
	private volatile boolean running = true;
	
	String hostName;
	int portNum;

	/**
	 * Overloaded constructor for a specific host and port number
	 * @param hostName String
	 * @param portNum int
	 * @throws IOException
	 */
	public ChatClient(String hostName, int portNum) throws IOException
	{
		this.hostName = hostName;
		this.portNum = portNum;

		systemIn = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/**
	 * Begins a while loop that maintains this client's connection to the server (another peer), displaying messages.
	 * When the client types "Exit" the connection will close, and the server will be able to make a new connection.  
	 * @throws IOException
	 */
	public void chatWithOther() throws IOException
	{
		String fromServer;
		String fromClient;
		
		//Create a socket, and attempt to connect to the targeted host's port number.  
		clientSocket = new Socket(hostName, portNum);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		running = true;
		//While the exit command has not been entered, display messages from both the user and the server.
		System.out.println("Connection established!");
		while (!(fromServer = socketIn.readLine()).equals("QUIT") && running)
		{
			//Display a message from the server.
			System.out.println("Server: " + fromServer);
			/* Parser */
			// NOTE: the intended source directory to update should be the first argument
			String sourceDirectory = fromServer.substring(0, fromServer.indexOf("::"));
			parser = new chatClientParser(new FileDirectory("sync\\"), hostName);
			// the 2 gives the parser the rest of the arguments
			fromClient = parser.command(fromServer);

			//Take user input, and display it.  out.println() finally sends the message to the server via the socket.
//		    fromClient = systemIn.readLine();
		    if (fromClient != null) 
		    {
		        System.out.println("Client: " + fromClient);
		        out.println(fromClient);
		    }
		}
		
		//If we get here, the exit command should have been entered, so we can close the socket to free the server for 
		//additional connections.
		System.out.println("closing connection"); //debug
		clientSocket.close();
	}

	 /*
	  * For use in threads; called with the Thread.start() method.
	  */
	@Override
	public void run() 
	{
		try 
		{
			System.out.println("Chatting thread! with " + hostName);
			this.chatWithOther();
		}
		catch(ConnectException e) {
			System.out.println("Failed to connect to:" + this.hostName);
			System.out.println("CE: " + Thread.currentThread().getState());
			running = false;
			System.out.println("CE2: " + Thread.currentThread().getState());
			Thread.currentThread().stop();
			System.out.println("CE3: " + Thread.currentThread().getState());
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("Chat thread Broke :/");
			System.out.println("IO: " + Thread.currentThread().getState());
			running = false;
			System.out.println("IO2: " + Thread.currentThread().getState());
			Thread.currentThread().stop();
			System.out.println("IO3: " + Thread.currentThread().getState());
			e.printStackTrace();
		}
		
	}

	/**
	 * Returns the parser for this Client
	 * @return chatClientParser
	 */
	public chatClientParser getChatClientParser() {
		return parser;
	}
	
}
