import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface FileWriting {

	public static void writingEncryptedFile(ArrayList<String> list) throws IOException {
		
		
		java.io.File file2 = new java.io.File("outencrypted.txt");
		
		java.io.PrintWriter output = new java.io.PrintWriter(file2);
		
		
		for (String o : list) {
			
			output.print(o.toString()+ " ");
		
		}
		
		output.close();
		
	}
	
	
	
	
}
