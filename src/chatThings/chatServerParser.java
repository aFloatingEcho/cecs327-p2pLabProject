
package chatThings;
import java.io.IOException;
import java.util.ArrayList;

import fileDirectory.FileDirectory;
import peer.DataServer;

public class chatServerParser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private FileDirectory syncDirectory;
	private String hostName;
	private int getPosition;
	
	/**
	 * Creates commands for files.
	 * <p>FORMAT:
	 * <p> COMMAND::[FILE_NAME]::[INT VARIABLE (see next line)]
	 * <p> INT VARIABLE: long for file age if declaring file, int of port if transferring file.
	 * 
	 * @param syncDirectory FileDirectory
	 * @param hostName String
	 */
	public chatServerParser(FileDirectory syncDirectory, String hostName) {
		this.syncDirectory = syncDirectory;
		this.hostName = hostName;
		this.getPosition = 0;
	}
	
	/**
	 * Returns the current filelist position
	 * @return current position, returns -1 if the list has reached the end.
	 */
	public int currentPosition() {
		return this.getPosition;
	}
	
	/**
	 * Returns command string of the next file in line.
	 * @return String
	 */
	public String getNextFile() {
		// Sets position to 0 to avoid an error
		if(this.getPosition == -1) {
			this.getPosition = 0;
		}
		String fileToReturn = this.sendSingleFileInfo(this.syncDirectory.getFileList().get(this.getPosition));
		this.getPosition++;
		// Resets the getPosition if it turns out the next position will be over 
		if(this.getPosition == this.syncDirectory.getActualFileList().size()) {
			this.getPosition = -1;
		}
		return fileToReturn;
	}
	
	/**
	 * Command to send out file info.
	 * <p> It remains public only for diagnosis purposes. You shouldn't be using it in the loop-
	 * <p> chatServerParser.getNextInfo() in conjunction with chatServerParser.currentPosition should be used.
	 * @param fileToSend String
	 * @return String
	 */
	public String sendSingleFileInfo(String fileToSend) {
		if(this.syncDirectory.checkTombstone(fileToSend)) {
			String command = "DELETE::" + fileToSend + "::00";
			return command;
		}
		String command = "CHECK::" + fileToSend + "::";
		command += this.syncDirectory.getFileAge(fileToSend);
		return command;
	}
	
	/**
	 * Command to send details on how to transfer a file.
	 * that monster of a code comes from the getNextFile systme
	 * @param fileToSend
	 * @param portNo
	 * @return
	 */
	public String sendSingleFileTransferDetails(int portNo) {
		return ("SEND::" + this.syncDirectory.getFileList().get(this.getPosition) + "::" + portNo);
	}
	
	/**
	 * Command to send out the actual file by opening a socket that the client will
	 * <p> connect to in order to obtain the broadcast.
	 * @param portNumber int
	 * @return boolean
	 */
	public boolean sendSingleFile(String fileToSend, int portNumber) {
		try {
			DataServer sendFile = new DataServer(portNumber, this.syncDirectory.returnFilePath(fileToSend));
			sendFile.transferFile();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Quit command
	 * @return String
	 */
	public String sendQuit() {
		return "QUIT";
	}

}
