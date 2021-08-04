/**
 * FileDirectory Class intended to sync file directories together.
 */

import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.
import java.io.*; // Import Java library to handle directories
import java.nio.file.Files;

public class FileDirectory {
	// To make things easier, we refer location in the main scope
	// and directory when setting up arguments.
	private ArrayList<File> fileList;
	private ArrayList<String> listToScan;
	private String sourceLocation;
	
	public FileDirectory(String sourceDirectory){
		this.sourceLocation = sourceDirectory;
		this.fileList = this.getFileList(this.sourceLocation);
		this.listToScan = this.processFileList(this.fileList);
	}
	
	/**
	 * convertFileList converts a Filepath into an ArrayList of 
	 * files inside of that directory that can be used.
	 * @param listFile
	 * @return
	 */
	public ArrayList<File> convertFileList(File listFile){
		ArrayList<File> listOfFiles = new ArrayList<>();
		File[] temp = listFile.listFiles();
		for(File files : temp){
			listOfFiles.add(files);
		}
		return listOfFiles;
	}

	/**
	 * Scans a directory via passed fileString
	 * @param directoryToScan
	 * @return
	 */
	public ArrayList<File> getFileList(String directoryToScan){
		File toScan = new File(directoryToScan);
		return convertFileList(toScan);
	}

	/**
	 * Outputs an agnostic file list that is useful for examining the files
	 * in the source/target sections.
	 * @param directoryList
	 * @return
	 */
	public ArrayList<String> processFileList(ArrayList<File> directoryList){
		ArrayList<String> listOfFiles = new ArrayList<>();
		for(File files: directoryList) {
			listOfFiles.add(files.getAbsolutePath().substring(this.sourceLocation.length()));
		}
		return listOfFiles;
	}
	
	/**
	 * Determines if a file needs to be transfered from the source to the destination.
	 * @param replacementAge long variable that indicates the time since the file has been replaced
	 * @param destinationLocation
	 * @return boolean value, true if it needs to be replaced, else false if it doesn't.
	 */
	public boolean determineShouldBeReplaced(long replacementAge, String destinationFile) {
		// Attempt to check if a preexisting file exists.
		try {
			if(new File(destinationFile).exists()) {
				// Determine if the destination file is older than the source location.
				if(replacementAge > new File(destinationFile).lastModified()) {
					return true;
				}
				else {
					return false;
				}
			}
		} catch(Exception IOError){
			return true;
		}
		return true;
	}
	
	public boolean doesFileExist(String FileName) {
		return(new File(sourceLocation + FileName).exists());
	}
	
	public BufferedOutputStream returnFile(String FileName) {
		File fileToCopy = null;
		FileOutputStream fileToReturn = null;
		BufferedOutputStream returnContents = null;
		try {
			fileToCopy = new File(this.sourceLocation + FileName);
			fileToReturn = new FileOutputStream(fileToCopy);
			returnContents = new BufferedOutputStream(fileToReturn);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return returnContents;
	}
	
	/**
	 * Getters of information of the files in the source directory.
	 * @return
	 */
	public ArrayList<String> getFileList(){
		return this.listToScan;
	}
	/**
	 * Getters of information of the source directory.
	 * @return
	 */
	public String getSourceDirectory() {
		return this.sourceLocation;
	}
}
