package com.github.muyundefeng.utils;
import java.util.ArrayList;
import java.util.List;

//存放网页的基本信息
import com.github.muyundfeng.parseHtml.Html;

public class Page {
	
	private String source;
	private String rawString;
	private String url;
	private String charSet;
	private Html html;
	private String method;
	private List<Request> requests;
	private List<String> list;
	private Request request;
	private String addUrl;
	private List<String> addUrls;
	private List<String> pageUrls = new ArrayList<String>();
	
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String defaultMethod = GET;
	
	public void addTargetRequests(List<Request> requests){
		this.requests = requests;
	}
	public List<Request> getRequests(){
		return requests;
	}
	
	public void addTargetRequest(Request request){
		this.request = request;
	}
	public Request getRequest(){
		return request;
	}
	
	public void addTargetStrings(List<String> addUrls){
		this.addUrls = addUrls;
	}
	public List<String> getaddUrls(){
		return addUrls;
	}
	
	public void addTargetString(String url){
		pageUrls.add(url);
		//this.addUrl = url;
	}
	public List<String> getaddUrl(){
		return pageUrls;
	}
	
	
	public Html getHtml() {
		return new Html(source,charSet,url);
	}
	public void setHtml(Html html) {
		this.html = html;
	}
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
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Page(String source, String rawString, String url, String charSet,String method) {
		super();
		this.source = source;
		//this.header = header;
		this.rawString = rawString;
		this.url = url;
		this.charSet = charSet;
		if(GET.equals(method))
			this.method = GET;
		else
			if(POST.equals(method))
				this.method = POST;
			else 
				this.method = defaultMethod;
		this.html = new Html(source,charSet,url);
	}
	public Page(String source,String url,String charset){
		this.source = source;
		this.url = url;
		this.charSet = charset;
		this.rawString = getText(source);
		this.method = defaultMethod;
		this.html = new Html(source,charSet,url);
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
	
}
