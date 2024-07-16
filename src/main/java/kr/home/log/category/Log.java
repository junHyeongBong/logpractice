package kr.home.log.category;

import kr.home.log.category.type.Browser;
import kr.home.log.category.type.Status;

public class Log {
	
	private Status status;
	private Url url;
	private Browser browser;
	
	public Log(Status status, Url url, Browser browser) {
		this.status = status;
		this.url = url;
		this.browser = browser;
	}
	
	public Url getUrl() {
		return url;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Browser getBrowser() {
		return browser;
	}
}
