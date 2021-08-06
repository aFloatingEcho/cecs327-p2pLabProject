package chatThings;

import java.io.IOException;
import java.net.UnknownHostException;

public class chatRunner {

	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		//The target port we want to connect to; this is set in the server portion.
		int port = 4444;
		//Hardcode the name of the computer you'd like to connect to for now; you can find this by 
		//running "ipconfig /all" in the cmd prompt.
		String targetHostName = "DESKTOP-DSUC2OA";
		

		Server chatServer = new Server(port);
		Client chatClient = new Client(targetHostName, port);
		
		new Thread(chatClient, "Client").start();
		new Thread(chatServer, "Server").start();
		
		
		
		/*
		chatServer.listenAndProvideService();
		chatClient.chatWithOther();
		*/


	}

}
