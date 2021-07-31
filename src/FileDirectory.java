/**
 * FileDirectory Class intended to sync file directories together.
 */

import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.
import java.io.*; // Import Java library to handle directories

public class FileDirectory 
{
	private File[] FileList;
	
	public FileDirectory()
	{
		FileList = null;
	}
	
	public ArrayList<File> detectFileList(File listFile){
		ArrayList<File> listOfFiles = new ArrayList<>();
		File[] temp = listFile.listFiles();
		for(File files : temp)
		{
			listOfFiles.add(files);
		}
		return listOfFiles;
	}

}
