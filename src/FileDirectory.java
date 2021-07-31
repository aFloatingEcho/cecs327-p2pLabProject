/**
 * FileDirectory Class intended to sync file directories together.
 */

import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.
import java.io.*; // Import Java library to handle directories

public class FileDirectory {
	private ArrayList<File> FileList;
	private String SourceLocation;
	private String TargetLocation; 
	
	public FileDirectory(){
		FileList = null;
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
			listOfFiles.add(files.getAbsolutePath().substring(SourceLocation.length()));
		}
		return listOfFiles;
	}
}
