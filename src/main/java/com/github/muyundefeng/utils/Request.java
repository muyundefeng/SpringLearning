package com.github.muyundefeng.utils;

import java.util.Map;

public class Request {

	public String url;
	public Map<String, String> header;
	public Request(String url){
		this(url, null);
	}
	public Request(String url,Map<String, String> header) {
		super();
		this.url = url;
		this.header = header;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	
}
