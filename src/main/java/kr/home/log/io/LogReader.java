package kr.home.log.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogReader {
	
	private final static String INPUT_LOG = "input.log";
	
	public String readInputLog(String path) {
		
		StringBuilder input = new StringBuilder();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(new File(path + INPUT_LOG)));
			
			String str = "";
			
			while ((str = reader.readLine()) != null) {
				input.append(str + "\n");
			}
			
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		return input.toString();
	}

}
