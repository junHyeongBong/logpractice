package kr.home.log.category;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Url {
	
	private String mainUrl;
	private String apiServiceId;
	private String apiKey;
	private String apiQuery;
	
	private static final List<String> apiServiceIds = Arrays.asList("blog", "book", "image", "knowledge", "news", "vclip");
	private static final String API_KEY = "apikey";
	private static final String QUERY = "q";
	private static final String SEARCH = "/search/";
	
	private Url(String mainUrl, String apiServiceId, String apiKey, String apiQuery) {
		this.mainUrl = mainUrl;
		this.apiServiceId = apiServiceId;
		this.apiKey = apiKey;
		this.apiQuery = apiQuery;
	}
	
	public static Url info(String urlInfo) {
		
		String mainUrl = "";
		String apiServiceId = "";
		String apiKey = "";
		String apiQuery = "";
		
		try {
			
			URL url = new URL(urlInfo);
			
			apiServiceId = serviceId(apiServiceId, url.getPath().replaceFirst("/", "").split("/")[1]);
			
			String query = url.getQuery();
			
			if(query != null) {
				query = query.toLowerCase();
				if(query.contains("&")) {
					String[] querys = query.split("&");
					apiKey = querys[0].split("=")[1];
					apiQuery = querys[1].split("=")[1];
				}else {
					if(query.contains(API_KEY.toLowerCase())) {
						apiKey = query.split("=")[1];
					}else if(query.contains(QUERY.toLowerCase())) {
						apiQuery = query.split("=")[1];
					}
					mainUrl = urlInfo.replace(url.getPath(), "").replace(url.getQuery(), "").replace("?", "") + SEARCH;
				}
			}else {
				mainUrl = urlInfo.replace(url.getPath(), "") + SEARCH;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new Url(mainUrl, apiServiceId, apiKey, apiQuery);
	}
	
	/*
	 * apiServiceIds 목록들과 일치하면 리턴
	 * 
	 */
	public static String serviceId(String apiServiceId, String selectService) {
		
		String temp = selectService.toLowerCase();
		
		if(apiServiceIds.contains(temp)) {
			apiServiceId = temp;
		}
		
		return apiServiceId;
	}
	
	public String getMainUrl() {
		return mainUrl;
	}
	
	public String getApiServiceId() {
		return apiServiceId;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public String getApiQuery() {
		return apiQuery;
	}

}
