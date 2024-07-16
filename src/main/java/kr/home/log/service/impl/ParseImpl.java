package kr.home.log.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.home.log.category.Log;
import kr.home.log.category.Url;
import kr.home.log.category.type.Browser;
import kr.home.log.category.type.Status;
import kr.home.log.service.Parse;

public class ParseImpl<K, V> implements Parse<K, V>{

	@Override
	public Map<K, List<V>> parse(String param) {
		
		Map<K, List<V>> paramMap = new HashMap<K, List<V>>();
		String[] line = param.replaceAll("\\[", "").split("\n");
		int len = line.length;
		
		for(int i=0; i<len; i++) {
			Log log = inputLog(line[i].split("\\]"));
			Status status = log.getStatus();
			List<V> logs = paramMap.getOrDefault(status, new ArrayList<>());
			logs.add((V)log);
			paramMap.put((K)status, logs);
		}
		
		
		return paramMap;
	}
	
	/*
	 * FIXME
	 */
	private Log inputLog(String[] logs) {
		
		Status status = Status.valueOf(Integer.valueOf(logs[0]));
		Url url = Url.info(logs[1]);
		Browser browser = Browser.valueOf(logs[2]);
		
		return new Log(status, url, browser);
	}

}
