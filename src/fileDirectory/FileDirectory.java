package fileDirectory;
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
	private File sourceLocation;
	
	public FileDirectory(String sourceDirectory){
		this.sourceLocation = new File(sourceDirectory);
//		sourceLocation.mkdir();
		this.fileList = this.convertFileList(this.sourceLocation);
		this.listToScan = this.processFileList(this.fileList);
	}
	
	/**
	 * convertFileList converts a Filepath into an ArrayList of 
	 * files inside of that directory that can be used.
	 * @param listFile File
	 * @return ArrayList<File>
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
	 * Outputs an agnostic file list that is useful for examining the files
	 * in the source/target sections.
	 * @param directoryList ArrayList<File>
	 * @return ArrayList<String>
	 */
	public ArrayList<String> processFileList(ArrayList<File> directoryList){
		ArrayList<String> listOfFiles = new ArrayList<>();
		for(File files: directoryList) {
			listOfFiles.add(files.getAbsolutePath().substring(this.sourceLocation.getAbsolutePath().length()));
		}
		return listOfFiles;
	}
	
	/**
	 * Determines if a file needs to be transfered from the source to the destination.
	 * @param replacementAge long variable that indicates the time since the file has been replaced
	 * @param destinationFile String
	 * @return boolean value, true if it needs to be replaced, else false if it doesn't.
	 */
	public boolean determineShouldBeReplaced(long replacementAge, String destinationFile) {
		String actualLocation = this.returnFilePath(destinationFile);
		// Attempt to check if a preexisting file exists.
		try {
			if(new File(actualLocation).exists()) {
				// Determine if the destination file is older than the source location.
				if(replacementAge > new File(actualLocation).lastModified()) {
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
	
	/**
	 * Get the last modified info of a string
	 * @param FileName String
	 * @return String
	 */
	public String getFileAge(String FileName) {
		long age = new File(this.returnFilePath(FileName)).lastModified();
		return Long.toString(age);
	}
	
	/**
	 * Wrapper to reutrn if a file exists or not
	 * @param FileName String
	 * @return boolean
	 */
	public boolean doesFileExist(String FileName) {
		File toTest = new File(this.returnFilePath(FileName));
		boolean toReturn = toTest.exists();
		return toReturn;
	}
	
	/**
	 * Wrapper to check if there is a blank function.
	 * <p> This should not be called if the file doesn't exist.
	 * @param FileName String
	 * @return boolean
	 */
	public boolean checkTombstone(String FileName) {
		File toTest = new File(this.returnFilePath(FileName));
		if(toTest.length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a specific deliverable when a file is named.
	 * @param FileName String
	 * @return BufferedOutputStream
	 */
	public BufferedOutputStream returnFile(String FileName) {
		File fileToCopy = null;
		FileOutputStream fileToReturn = null;
		BufferedOutputStream returnContents = null;
		try {
			fileToCopy = new File(this.returnFilePath(FileName));
			fileToReturn = new FileOutputStream(fileToCopy);
			returnContents = new BufferedOutputStream(fileToReturn);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return returnContents;
	}
	
	/**
	 * Funciton that deletes a file and creates a blank file in its tombstone.
	 * @param FileName String
	 */
	public void deleteFile(String FileName) {
		String fileToDelete = this.returnFilePath(FileName);
		File deleteFile = new File(fileToDelete);
		try {
			if(deleteFile.exists()) {
				if(!deleteFile.delete()) throw new IOException("File not deleted.");
			}
			if(!deleteFile.createNewFile()) throw new IOException("File not created.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return absolute file path of a location file.
	 * @param fileName String
	 * @return String
	 */
	public String returnFilePath(String fileName) {
		return (this.sourceLocation.getAbsolutePath() + fileName);
	}
	
	/**
	 * Getters of information of the files in the source directory.
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getFileList(){
		return this.listToScan;
	}
	/**
	 * Getters of information of the source directory.
	 * @return String
	 */
	public String getSourceDirectory() {
		return this.sourceLocation.getAbsolutePath();
	}
	/**
	 * Getters of the actual Filelist.
	 * @return ArrayList<File>
	 */
	public ArrayList<File> getActualFileList(){
		return this.fileList;
	}
}
