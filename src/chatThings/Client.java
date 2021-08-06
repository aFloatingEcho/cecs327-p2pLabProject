package chatThings;

import java.io.*;
import java.net.*;

public class Client implements Runnable
{

	
	
	
	private Socket clientSocket;
	private PrintWriter out; 
	private BufferedReader socketIn;
	private BufferedReader systemIn;
	
	String hostName;
	int portNum;
	
	
	public Client(String hostName, int portNum) throws UnknownHostException, IOException
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
		
		clientSocket = new Socket(hostName, portNum);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//While the exit command has not been entered, display messages from both the user and the server.
		while ((fromServer = socketIn.readLine()) != "Exit") 
		{
		   System.out.println("Server: " + fromServer);
		   if (fromServer.equals("Bye."))
		       break;

		    fromClient = systemIn.readLine();
		    if (fromClient != null) 
		    {
		        System.out.println("Client: " + fromClient);
		        out.println(fromClient);
		    }
		}
		
		//If we get here, the exit command should have been entered, so we can close the socket to free the server for 
		//additional connections.
		clientSocket.close();
	}

	@Override
	public void run() 
	{
		try 
		{
			this.chatWithOther();
		}
		catch (IOException e)
		{
			System.out.println("Chat thread Broke :/");
		}
		
	}
	
}
