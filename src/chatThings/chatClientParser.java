package chatThings;
import java.io.IOException;

import fileDirectory.FileDirectory;
import peer.DataClient;

public class chatClientParser {

	public static void main(String[] args) {
		String test = "DELETE::help .txt::658415";
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
	 * @param syncDirectory
	 * @param hostName
	 */
	public chatClientParser(FileDirectory syncDirectory, String hostName) {
		this.syncDirectory = syncDirectory;
		this.hostName = hostName;
	}
	
	public String command(String commandInput, String fileInput, String portNumber) {
		String reply = null;
		switch(commandInput) {
		case "CHECK":
			if(this.syncDirectory.determineShouldBeReplaced(Long.parseLong(portNumber), fileInput)) {
				reply = "TRUE";
			}
			reply = "FALSE";
		case "DELETE":
			this.syncDirectory.deleteFile(fileInput);;
			reply = "DONE";
		case "SEND":
			try {
				DataClient getFile = new DataClient(Integer.parseInt(portNumber), fileInput, this.hostName);
				getFile.acceptFile();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reply = "DONE";
		}
		return reply;
	}

}
