package chatThings;

import fileDirectory.FileDirectory;

import java.io.*;
import java.net.*;

public class ChatServer implements Runnable
{
	 ServerSocket serverSocket;
	 Socket clientSocket;
	 PrintWriter out;
	 BufferedReader in;
	 chatServerParser parser;
	 String directoryToWatch;

	/**
	 * Overloaded constructor assigns the server socket to a port number
	 * @param portNum
	 * @throws IOException
	 */
	public ChatServer(int portNum) throws IOException
	 {
		 serverSocket = new ServerSocket(portNum);
	 }

	/**
	 * Overloaded constructor assigns the server socket to a port number and watches a specific directory
	 * @param portNum int
	 * @param directoryToWatch String
	 * @throws IOException
	 */
	public ChatServer(int portNum, String directoryToWatch) throws IOException
	{
		serverSocket = new ServerSocket(portNum);
		this.directoryToWatch = directoryToWatch;
	}
	 
	 /**
	  * Method that lets the server listen for incoming connections, and once a connection is made, facilitates communication back and forth.
	  * A lot of the system.out text is meant as debugging, will likely not be necessary for the final product.  
	  * Current implementation involves repeating an incoming message to the connected client.
	  * @throws IOException
	  */
	 public void listenAndProvideService() throws IOException
	 {
		 //debug
		 System.out.println("Listening for incoming connection: ");
		 
		 //Accept an incoming connection, and create writers and readers.
		 clientSocket = serverSocket.accept();
		 out = new PrintWriter(clientSocket.getOutputStream(), true);
		 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 
		 //Process input and output
		 String input;
		 String output;
		 out.println("Beginning chat: >>>>>>>>>");

		 /* Parser */
		 // NOTE: the intended source directory to update should be the first argument
		 String sourceDirectory = "sync\\";
		 // or
//		 String sourceDirectory = directoryToWatch;

		 // NOTE: the assumption is that the InetAddress host name is the same as the one that was inputted in the Node class
		 parser = new chatServerParser(new FileDirectory(sourceDirectory), serverSocket.getInetAddress().getHostName());

		 while((input = in.readLine()) != null)
		 {
			 output = sourceDirectory + "::" + parser.sendSingleFileInfo(input);   // command generated
			 //When input is recieved, echo it back to the client.
			 System.out.println("Server received: " + input);
//			 output = "Server received: " + input;
			 out.println(output);   // command sent
		 }
		 
	 }

	 /*
	  * For use in threads; called with the Thread.start() method.
	  */
	@Override
	public void run() 
	{
		try 
		{
			//debug
			System.out.println("Listening Thread!");
			this.listenAndProvideService();
		}
		catch (IOException e) 
		{
			System.out.println("Listen thread broke :/");
		}
		
	}

	/**
	 * Returns the parser for this Server
	 * @return chatServerParser
	 */
	public chatServerParser getChatServerParser() {
		return parser;
	}
	
}
