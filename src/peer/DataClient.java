
package peer;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataClient {

	public static void main(String[] args) throws IOException {
		String serverName = "";
		DataClient test = new DataClient(5555, "", serverName);
		test.acceptFile();
	}

	
	private Socket clientConnection = null;
	private String fileName = null;
	// TODO: Seriously, fix that magic number with some dynamic way- perhaps in chat indicate the length in the string.
	private int temp_size = 1000000; //capped the total size to 1 mb
	
	/**
	 * Default constructor (do not use, it defaults a port number that is already in use)
	 * @param fileName
	 * @param serverName
	 * @throws IOException
	 */
	public DataClient(String fileName, String serverName) throws IOException{
		this.clientConnection = new Socket(serverName, 55555);
		this.fileName = fileName;
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
		this.fileName = fileName;
	}
	
	/**
	 * Accepts a file transfer from the server.
	 * This probably should be broken down into several programs but whatever.
	 * @returns true if successful, else return false
	 * @throws IOException
	 */
	public boolean acceptFile() throws IOException {
		// Set variables for the current position as well as the position read
		int currentPosition = 0, readPosition;
		// Set up streams for accepting a file 
		InputStream input = null;
		// Stream used to output the file (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/FileOutputStream.html)
		FileOutputStream output = null;
		output = new FileOutputStream(this.fileName);
		// Stream used to buffer the output without calling the system (https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/io/BufferedOutputStream.html)
		BufferedOutputStream outputBuffer = null;
		System.out.println("Attempt to recieve: " + this.fileName);
		// Attempt to write the files.
		byte[] brokenUp = new byte[temp_size];
		try {
			input = this.clientConnection.getInputStream();
			outputBuffer = new BufferedOutputStream(output);
			readPosition = input.read(brokenUp, 0, brokenUp.length);
			currentPosition = readPosition;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// Continuously read the input as the files are received via the socket until the connection stops.
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
			System.out.println(this.fileName + " has been recieved!");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
