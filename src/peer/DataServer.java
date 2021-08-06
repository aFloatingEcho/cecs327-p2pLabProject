package peer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServer {

	private ServerSocket dataServer = null;
	private Socket connection = null;
	private String fileName = null;
	private long startTime = 0;
	
	/**
	 * Constructor for Data Server
	 * @param portNo a selected number in the 55xxx range to begin copying.
	 * @param fileName fileName should be the absolute path where the file will be copied
	 * @throws IOException
	 */
	public DataServer(int portNo, String fileName) throws IOException {
		this.dataServer = new ServerSocket(portNo);
		this.fileName = fileName;
		this.startTime = System.currentTimeMillis();
	}
	
	public boolean transferFile() {
		// Set variables for the current position as well as the position read
		int currentPosition = 0, readPosition;
		// Stream used to input the file (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileInputStream.html)
		FileInputStream input = null;
		// Stream used to buffer the input without calling the system (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/BufferedInputStream.html)
		BufferedInputStream inputBuffer = null;
		boolean running = false;
		while(!running) {
			try {
				this.connection = this.dataServer.accept();
				System.out.println("Connection Accepted: " + this.connection);
				// Sending File
				File fileToSend = new File(this.fileName);
				byte[] brokenUp = new byte[(int)(fileToSend.length())]; // Break up the file
				input = new FileInputStream(fileToSend);
				
			} catch (IOException e) {
				System.out.println("Connection failed");
				e.printStackTrace();
			}
		}
		
		return running;
	}
	
	public void status() {
		
	}
}
