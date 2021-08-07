package chatThings;

import java.io.*;
import java.net.*;

public class ChatServer implements Runnable
{
	 ServerSocket serverSocket;
	 Socket clientSocket;
	 PrintWriter out;
	 BufferedReader in;
	  
	 public ChatServer(int portNum) throws IOException
	 {
		 serverSocket = new ServerSocket(portNum);
	 }
	 
	 /*
	  * Method that lets the server listen for incoming connections, and once a connection is made, facilitates communication back and forth.
	  * A lot of the system.out text is meant as debugging, will likely not be necessary for the final product.  
	  * Current implementation involves repeating an incoming message to the connected client.
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
		 
		 while((input = in.readLine()) != null)
		 {
			 //When input is recieved, echo it back to the client.
			 System.out.println("Server received: " + input);
			 output = "Server received: " + input;
			 out.println(output);
			 
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
	 

	
	
	
	
	
}
