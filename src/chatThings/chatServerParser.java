
package chatThings;
import java.io.IOException;
import fileDirectory.FileDirectory;
import peer.DataServer;

public class chatServerParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private FileDirectory syncDirectory;
	private String hostName;
	
	/**
	 * Creates commands for files.
	 * <p>FORMAT:
	 * <p> COMMAND::[FILE_NAME]::[INT VARIABLE (see next line)]
	 * <p> INT VARIABLE: long for file age if declaring file, int of port if transferring file.
	 * 
	 * @param syncDirectory
	 * @param hostName
	 */
	public chatServerParser(FileDirectory syncDirectory, String hostName) {
		this.syncDirectory = syncDirectory;
		this.hostName = hostName;
	}
	
	/**
	 * Command to send out files.
	 * @param fileToSend
	 * @return
	 */
	public String sendFileInfo(String fileToSend) {
		String command = "CHECK::" + fileToSend + "::";
		command += this.syncDirectory.getFileAge(fileToSend);
		return command;
	}
}
