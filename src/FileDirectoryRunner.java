
import java.util.ArrayList; // Import Java ArrayList in order to handle file lists.
import java.io.*; // Import Java library to handle directories
import java.nio.file.Files;

public class FileDirectoryRunner {

	public static void main(String[] args) {
		FileDirectory syncFolder = new FileDirectory("sync");
		ArrayList<String> fileList = syncFolder.getFileList();
		for(String files: fileList) {
			System.out.println(files);
		}
	}

}
