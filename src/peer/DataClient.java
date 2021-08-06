
package peer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;

public class DataClient {

	private Socket clientConnection = null;
	private String fileName = null;
	private int temp_size = 1000000;
	
	/**
	 * Default constructor (do not use, it defaults a port number that is already in use)
	 * @param fileName
	 * @param serverName
	 * @throws IOException
	 */
	public DataClient(String fileName, String serverName) throws IOException{
		this.clientConnection = new Socket(serverName, 55555);
	}
	
	/**
	 * Creates a new data client to handle a file in question.
	 * @param portNo a selected number in the 55xxx range to begin copying.
	 * @param fileName should be the absolute path where the file will be copied
	 * @param serverName location of the server that has the file
	 * @throws IOException
	 */
	public DataClient(int portNo, String fileName, String serverName) throws IOException{
		this.clientConnection = new Socket(serverName, portNo);
	}
	
	/**
	 * Accepts a file transfer from the server.
	 * @return
	 * @throws IOException
	 */
	public boolean acceptFile() throws IOException {
		// Set variables for the current position as well as the position read
		int currentPosition = 0, readPosition;
		// Set up streams for accepting a file
		InputStream input = null;
		FileOutputStream output = null;
		BufferedOutputStream outputBuffer = null;
		System.out.println("Attempt to recieve: " + this.fileName);
		// Attempt to write the files.
		byte[] brokenUp = new byte[temp_size];
		try {
			input = this.clientConnection.getInputStream();
			output = new FileOutputStream(this.fileName);
			outputBuffer = new BufferedOutputStream(output);
			readPosition = input.read(brokenUp, 0, brokenUp.length);
			currentPosition = readPosition;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		do {
			readPosition = input.read(brokenUp, currentPosition, (brokenUp.length - currentPosition));
			if(readPosition >= 0) {
				currentPosition += readPosition;
			}
		} while(readPosition > -1);
		
		// Write the file out, and close all buffers on completion.
		try {
			outputBuffer.write(brokenUp, 0, currentPosition);
			outputBuffer.flush();
			output.close();
			outputBuffer.close();
			this.clientConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
