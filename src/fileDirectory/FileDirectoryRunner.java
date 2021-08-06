package fileDirectory;

import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.

import java.io.*; // Import Java library to handle directories
import java.nio.file.Files;

public class FileDirectoryRunner {

	public static void main(String[] args) {
		FileDirectory syncFolder = new FileDirectory("sync\\");
		ArrayList<String> fileList = syncFolder.getFileList();
		ArrayList<File> actualFiles = syncFolder.getActualFileList();
		for(File files: actualFiles) {
			try {
				System.out.println(files.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(String files: syncFolder.getFileList()) {
			if(syncFolder.doesFileExist(files)){
				System.out.println("true");
			}
		}
	}

}
