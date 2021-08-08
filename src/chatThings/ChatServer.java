package chatThings;

import fileDirectory.FileDirectory;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ChatServer implements Runnable
{
	 ServerSocket serverSocket;
	 PrintWriter out;
	 BufferedReader in;
	 chatServerParser parser;
	 String directoryToWatch;
	 private ExecutorService commandThreads;

	/**
	 * Overloaded constructor assigns the server socket to a port number
	 * @param portNum
	 * @throws IOException
	 */
	public ChatServer(int portNum, ExecutorService command) throws IOException
	 {
		 serverSocket = new ServerSocket(portNum);
		 this.commandThreads = command;
	 }

	/**
	 * Overloaded constructor assigns the server socket to a port number and watches a specific directory
	 * @param portNum int
	 * @param directoryToWatch String
	 * @throws IOException
	 */
	public ChatServer(int portNum, String directoryToWatch, ExecutorService command) throws IOException
	{
		serverSocket = new ServerSocket(portNum);
		this.directoryToWatch = directoryToWatch;
		this.commandThreads = command;
	}
	 
	 /**
	  * Method that lets the server listen for incoming connections, and once a connection is made, facilitates communication back and forth.
	  * A lot of the system.out text is meant as debugging, will likely not be necessary for the final product.  
	  * Current implementation involves repeating an incoming message to the connected client.
	  * @throws IOException
	  */
	 public void listenAndProvideService(Socket clientSocket) throws IOException
	 {
		 //debug
		 System.out.println("Listening for incoming connection: ");
		 
		 //Accept an incoming connection, and create writers and readers.
		 out = new PrintWriter(clientSocket.getOutputStream(), true);
		 in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 
		 //Process input and output
		 String input;
		 String output;
		 System.out.println("Server Active");

		 /* Parser */
		 // NOTE: the intended source directory to update should be the first argument
		 String sourceDirectory = "sync\\";
		 // or
//		 String sourceDirectory = directoryToWatch;

		 // NOTE: the assumption is that the InetAddress host name is the same as the one that was inputted in the Node class
		 parser = new chatServerParser(new FileDirectory(sourceDirectory), serverSocket.getInetAddress().getHostName());

		 while((parser.currentPosition() != -1))
		 {
			 output = parser.getNextFile();   // command generated
			 //When input is recieved, echo it back to the client.
			 System.out.println("Server command: " + output);
			 out.println(output);
			 String response = in.readLine();
			 System.out.println("Return:" + response);
			 if(response.equals("TRUE")) {
				 int portToSend = (parser.currentPosition() % 50) + 55000; // random port to make sure that all ports are clear
				 out.println(parser.sendSingleFileTransferDetails(portToSend));
				 System.out.println("Command sent!");
				 parser.sendSingleFile(output.split("::")[1], portToSend);
			 }
			 else {
				 System.out.println("File does not need to be sent");
			 }
		 }
		 output = parser.sendQuit();
		 out.println(output);
		 
	 }

	 /*
	  * For use in threads; called with the Thread.start() method.
	  */
	@Override
	public void run() 
	{
		while(true) {
			 try {
				Socket newConnection = serverSocket.accept();
				this.commandThreads.submit(new Thread(new chatThread(newConnection, this)));
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	/**
	 * Returns the parser for this Server
	 * @return chatServerParser
	 */
	public chatServerParser getChatServerParser() {
		return parser;
	}
	
	public class chatThread implements Runnable{
		Socket input;
		ChatServer application;

		public chatThread(Socket input, ChatServer application) {
			this.input = input;
			this.application = application;
//			this.run(input, application);
		}
		
//		public void run(Socket input, ChatServer application) {
//			System.out.println("Connected to: " + input.getInetAddress());
//			try {
//				application.listenAndProvideService(input);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		@Override
		public void run() {
			System.out.println("Connected to: " + input.getInetAddress());
			try {
				application.listenAndProvideService(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
