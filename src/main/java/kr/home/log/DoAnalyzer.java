package kr.home.log;

import java.util.List;
import java.util.Map;

import kr.home.log.category.Log;
import kr.home.log.category.type.Status;
import kr.home.log.io.LogReader;
import kr.home.log.io.LogWriter;
import kr.home.log.service.Conversion;
import kr.home.log.service.Parse;

public class DoAnalyzer {
	
	private Conversion conversion;
	private Parse parse;
	
	public DoAnalyzer(Conversion conversion, Parse parse) {
		this.conversion = conversion;
		this.parse = parse;
	}
	
	/*
	 * FIXME
	 */
	public void analyze(String path) {
		
		String inputLogStr = new LogReader().readInputLog(path);
		Map<Status, List<Log>> logs = parse.parse(inputLogStr.toString());
		String conver = conversion.doConversion(logs);
		
		new LogWriter().writeTxt(path, conver);
	}

}
