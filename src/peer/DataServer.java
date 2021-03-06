package peer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServer {
	
	public static void main(String[] args) throws IOException {
		DataServer test = new DataServer(5555, "sync\\file.png");
		test.transferFile();
	}

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
		// Stream used to output to the socket.
		OutputStream output = null;
		boolean running = false;
		while(!running) {
			try {
				this.connection = this.dataServer.accept();
				System.out.println("Connection Accepted: " + this.connection);
				// Sending File
				File fileToSend = new File(this.fileName);
				long fileLength = fileToSend.length();
				System.out.println("Trasfering:" + fileName + " (" + fileLength + " byte(s))");
				byte[] brokenUp = new byte[(int)(fileLength)]; // Break up the file
				// Wrap the file in buffer before sending.
				input = new FileInputStream(fileToSend);
				inputBuffer = new BufferedInputStream(input);
				output = this.connection.getOutputStream();
				while((currentPosition = inputBuffer.read(brokenUp)) > 0) {
					output.write(brokenUp, 0, currentPosition);
				}
				output.flush();
				running = true;
				System.out.println("Finished Output.");
				
			} catch (IOException e) {
				System.out.println("Connection failed");
				e.printStackTrace();
			}
			finally {
				try {
					if(running) {
						this.dataServer.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return running;
	}
	
	public void status() {
		
	}
}
