package chatThings;
import java.io.IOException;

import fileDirectory.FileDirectory;
import peer.DataClient;

public class chatClientParser {

	public static void main(String[] args) {
		String test = "DELETE::help.txt::658415";
		String[] test1 = test.split("::");
		System.out.println(test1[0].toString());
		System.out.println(test1[1].toString());
		System.out.println(test1[2].toString());
	}
	
	private FileDirectory syncDirectory;
	private String hostName;


	
	/**
	 * Parser for chat commands.
	 * <p>FORMAT:
	 * <p> COMMAND::[FILE_NAME]::[INT VARIABLE (see next line)]
	 * <p> INT VARIABLE: long for file age if declaring file, int of port if transferring file.
	 * 
	 * @param syncDirectory FileDirectory
	 * @param hostName String
	 */
	public chatClientParser(FileDirectory syncDirectory, String hostName) {
		this.syncDirectory = syncDirectory;
		this.hostName = hostName;
	}
	
	/**
	 * Parser that reads the commands directly.
	 * @param commandInput String
//	 * @param fileInput String
//	 * @param portNumber String
	 * @return String
	 */
//	public String command(String commandInput, String fileInput, String portNumber) {
	public String command(String commandInput) {
		String[] args = commandInput.split("::");
		String request = args[0];
		String fileInput = args[1];
		String portNumber = args[2];
		String reply = null;

		switch(request) {
			// Command to check the file.
			case "CHECK":
				if(this.syncDirectory.determineShouldBeReplaced(Long.parseLong(portNumber), fileInput)) {
					reply = "TRUE";
				}
				reply = "FALSE";
			// Command to delete file.
			case "DELETE":
				this.syncDirectory.deleteFile(fileInput);;
				reply = "DONE";
			// Command to accept a new file from the server.
			case "SEND":
				try {
					DataClient getFile = new DataClient(Integer.parseInt(portNumber), fileInput, this.hostName);
					getFile.acceptFile();
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}
				reply = "DONE";
			case "QUIT":
				reply = "QUIT";
		}
		return reply;
	}

}
