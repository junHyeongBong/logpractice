package kr.home.log.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	
	private final static String OUTPUT_LOG = "output.log";
	
	public void writeTxt(String path, String contents) {
		
		try {
			
			BufferedWriter fw = new BufferedWriter(new FileWriter(new File(path + OUTPUT_LOG)));
			fw.write(contents);
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}

}
