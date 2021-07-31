/**
 * FileDirectory Class intended to sync file directories together.
 */

import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.
import java.io.*; // Import Java library to handle directories

public class FileDirectory {
	private ArrayList<File> FileList;
	
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
}
