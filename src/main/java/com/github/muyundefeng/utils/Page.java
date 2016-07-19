package com.github.muyundefeng.utils;
//存放网页的基本信息

import java.net.URL;

import com.github.muyundefeng.downloader.HttpDownloader;

public class Page {
	
	private String source;
	private String rawString;
	private String url;
	private String charSet;
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getRawString() {
		return rawString;
	}
	public void setRawString(String rawString) {
		this.rawString = rawString;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public Page(String source, String rawString, String url, String charSet) {
		super();
		this.source = source;
		//this.header = header;
		this.rawString = rawString;
		this.url = url;
		this.charSet = charSet;
	}
	public Page(String url){
		this(null, null, url, null);
	}
	public Page(String source,String url,String charset){
		this.source = source;
		this.url = url;
		this.charSet = charset;
		this.rawString = getText(source);
	}
	
	public  String getText(String htmlsource){
		if(htmlsource != null)
		{
			htmlsource = htmlsource.replaceAll("<[^>]*>", "");
			htmlsource = htmlsource.replaceAll(" ", "");
			return htmlsource;
		}
		return null;
	}
	public static void main(String[] args) {
		Request request = new Request("http://www.baidu.com");
		//String string = HttpDownloader.download(request);
		
		//System.out.println(getText(string));
	}
}
