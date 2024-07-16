package kr.home.log;

import java.util.List;

import kr.home.log.category.Log;
import kr.home.log.category.type.Status;
import kr.home.log.service.Conversion;
import kr.home.log.service.Parse;
import kr.home.log.service.impl.ConversionImpl;
import kr.home.log.service.impl.ParseImpl;

public class App {

	public static void main(String[] args) {
		String path = "";
		Parse parse = new ParseImpl<Status, List<Log>>();
		Conversion conversion = new ConversionImpl();
		new DoAnalyzer(conversion, parse).analyze(path);
		
	}

}
