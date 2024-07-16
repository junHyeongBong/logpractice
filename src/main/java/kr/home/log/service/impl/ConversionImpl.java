package kr.home.log.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import kr.home.log.category.Log;
import kr.home.log.category.type.Status;
import kr.home.log.service.Conversion;

public class ConversionImpl implements Conversion{
	
	private final static int RANK = 3;
	
	private interface State<T> {
		String getState(T t);
	}

	@Override
	public String doConversion(Map<Status, List<Log>> paramMap) {
		
		//200인 애들만 들고와서
		List<Log> logs = paramMap.get(Status.SUCCESS);
		
		String mostApi = apiKeyMore(logs);
		Map<String, Integer> rankService = rankService(logs);
		Map<String, Double> rateBrowser = rateBrowser(logs);
		
		
		return writeFile(mostApi, rankService, rateBrowser);
	}
	
	/*
	 * FIXME
	 * output에 해당하는 쓰기형식 서비스
	 */
	private String writeFile(String maxApi, Map<String, Integer> rankedServiceId, Map<String, Double> rateBrowser) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("최다호출 API KEY\n" + maxApi);
		stringBuilder.append("\n\n");
		stringBuilder.append("상위 3개의 API ServiceID와 각각의 요청 수\n");
		
		Iterator<Map.Entry<String, Integer>> serviceIdIter = rankedServiceId.entrySet().iterator();
		
		while (serviceIdIter.hasNext()) {
			Map.Entry<String, Integer> next = serviceIdIter.next();
			stringBuilder.append(String.format("%s : %d\n", next.getKey(), next.getValue()));
		}
		stringBuilder.append("\n웹브라우저별 사용비율\n");
		Iterator<Map.Entry<String, Double>> rateBrowserIter = rateBrowser.entrySet().iterator();
		while(rateBrowserIter.hasNext()) {
			Map.Entry<String, Double> next = rateBrowserIter.next();
			stringBuilder.append(String.format("%s : %.0f%%\n", next.getKey(), next.getValue()));
		}
		
		return stringBuilder.toString();
		
		
	}
	
	/*
	 * 최다 key
	 */
	private String apiKeyMore(List<Log> logs) {
		Map<String, Integer> urlApiCount = countApi(logs, new State<Log>() {

			@Override
			public String getState(Log t) {
				// TODO Auto-generated method stub
				return t.getUrl().getApiKey();
			}
			
		});
		
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String,Integer>>(urlApiCount.entrySet());
		list.sort(comparator);
		String maxApiKey = list.get(0).getKey();
		
		return maxApiKey;
	}
	
	/*
	 * 상위 3개 서비스
	 */
	private Map<String, Integer> rankService(List<Log> logs) {
		Map<String, Integer> serviceIdRank = countApi(logs, new State<Log>() {

			@Override
			public String getState(Log t) {
				// TODO Auto-generated method stub
				return t.getUrl().getApiServiceId();
			}
			
		});
		
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String,Integer>>(serviceIdRank.entrySet());
		Collections.sort(list, comparator);
		Map<String, Integer> result = new LinkedHashMap<String, Integer>();
		
		//내림차순으로 정렬된 상위3개(RANK=3)까지 넣는다.
		for(int i=0; i<RANK; i++) {
			result.put(list.get(i).getKey(), list.get(i).getValue());
		}
		
		return result;
	}
	
	/*
	 * 웹브라우저비율
	 */
	private Map<String, Double> rateBrowser(List<Log> logs) {
		int totalCount = logs.size();
		
		Map<String, Double> result = new LinkedHashMap<String, Double>();
		Map<String, Integer> browerCount = countApi(logs, new State<Log>() {

			@Override
			public String getState(Log t) {
				// TODO Auto-generated method stub
				return t.getBrowser().toString();
			}
			
		});
		
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String,Integer>>(browerCount.entrySet());
		Collections.sort(list, comparator);
		
		for(Map.Entry<String, Integer> entry : list) {
			double rate = ((double)entry.getValue()) / ((double)totalCount) * 100;
			result.put(entry.getKey(), (double) Math.round(rate));
		}
		
		return result;
	}
	
	/*
	 * FIXME
	 * api key 현황
	 */
	private Map<String, Integer> countApi(List<Log> logs, State state) {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		
		for(Log log : logs) {
			String key = state.getState(log);
			int count = paramMap.getOrDefault(key, 0) + 1;
			paramMap.put(key, count);
		}
		
		return paramMap;
	}
	
	
	
	private Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Map.Entry<String,Integer>>() {
		public int compare(Map.Entry<String, Integer> first, Map.Entry<String, Integer> second) {
			return first.getValue() > second.getValue() ? -1 : 1;
		}
	};

}
