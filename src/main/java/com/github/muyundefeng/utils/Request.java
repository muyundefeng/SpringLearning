package com.github.muyundefeng.utils;

import java.util.Map;

public class Request {

	public String url;
	public Map<String, String> header;
	private String method;
	
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String defaultMethod = GET;
	public Request(String url){
		this(url, null);
	}
	
	public Request(String url,Map<String, String> header) {
		super();
		this.url = url;
		this.header = header;
		this.method = defaultMethod;
	}
	
	public Request(String url,Map<String, String> header,String method){
		super();
		this.url = url;
		this.header = header;
		String temp = method.toLowerCase();
		if(GET.equals(temp))
			this.method = GET;
		else
			if(POST.equals(temp))
				this.method = POST;
			else 
				this.method = defaultMethod;
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
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
}
