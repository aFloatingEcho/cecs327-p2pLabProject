package peer;

import java.io.IOException;
import java.net.ServerSocket;

public class DataServer {

	private ServerSocket dataServer = null;
	private String fileName = null;
	
	/**
	 * Constructor for Data Server
	 * @param portNo a selected number in the 55xxx range to begin copying.
	 * @param fileName fileName should be the absolute path where the file will be copied
	 * @throws IOException
	 */
	public DataServer(int portNo, String fileName) throws IOException {
		this.dataServer = new ServerSocket(portNo);
		this.fileName = fileName;
	}
}
