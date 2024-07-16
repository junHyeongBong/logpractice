package kr.home.log.service;

import java.util.List;
import java.util.Map;

import kr.home.log.category.Log;
import kr.home.log.category.type.Status;

public interface Conversion {
	String doConversion(Map<Status, List<Log>> paramMap);
}
