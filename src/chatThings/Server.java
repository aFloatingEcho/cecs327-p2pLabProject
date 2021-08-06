package chatThings;

import java.io.*;
import java.net.*;

public class Server implements Runnable
{
	 ServerSocket serverSocket;
	 Socket clientSocket;
	 PrintWriter out;
	 BufferedReader in;
	  
	 public Server(int portNum) throws IOException
	 {
		 serverSocket = new ServerSocket(portNum);
	 }
	 
	 public void listenAndProvideService() throws IOException
	 {
		 //Accept an incoming connection
		 clientSocket = serverSocket.accept();
		 out = new PrintWriter(clientSocket.getOutputStream(), true);
		 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 
		 //Process input and output
		 String input;
		 String output;
		 
		 while((input = in.readLine()) != null)
		 {
			 output = "Server received: " + input;
			 out.println(output);
			 
		 }
		 
	 }

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
