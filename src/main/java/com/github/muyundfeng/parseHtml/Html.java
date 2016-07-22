package com.github.muyundfeng.parseHtml;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.muyundefeng.downloader.myHttpClientDownloader;
import com.github.muyundefeng.utils.Request;

import us.codecraft.xsoup.Xsoup;

public class Html {

	private String sourceText;
	private String charSet;
	private String baseUrl; 
	private final String Defaultencoding = "UTF-8";
	private String xpathExpr;
	
	public String toString(){
		return sourceText;
	}
	
	public Html(String source) {
		// TODO Auto-generated constructor stub
		this.sourceText = source;
		charSet = Defaultencoding;
	}
	
	public Html(String source,String charset) {
		// TODO Auto-generated constructor stub
		this.sourceText = source;
		this.charSet = charset;
	}
	
	public Html(String source,String charset,String baseUrl) {
		// TODO Auto-generated constructor stub
		this.sourceText = source;
		this.charSet = charset;
		this.baseUrl = baseUrl;
	}
	/*
	 * gerate document
	 */
	public Document getDoc(){
		if(sourceText != null)
			return Jsoup.parse(sourceText);
		else
			return null;
	}
	
	public void setXpathExpr(String xpath){
		this.xpathExpr = xpath;
	}
	
	public List<String> all(){
		Document document = getDoc();
		if (document == null)
		{
			throw new IllegalArgumentException("no source");
			//return null;
		}
		else{
		    List<String> results = Xsoup.select(document, xpathExpr).list();
		    return results;
		}
		//return null;
	}
	
	public List<String> links(){
		Document document = getDoc();
		if (document == null)
		{
			throw new IllegalArgumentException("no source");
		}
		else{
		    List<String> results = Xsoup.select(document, "//a/@href").list();
		    return results;
		}
		
	}
	
	public String xpath(String str){
		xpathExpr = str;
		Document document = getDoc();
		if (document == null)
		{
			throw new IllegalArgumentException("no source");
			//return null;
		}
		else{
		    String result = Xsoup.select(document, xpathExpr).get();
		    return result;
		}
		//return null;
	}
	
	public  String getText(){
		String string = "";
		if(sourceText != null)
		{
			string = sourceText.replaceAll("<[^>]*>", "");
			string = string.replaceAll(" ", "");
			return string;
		}
		return null;
	}
	
	public static Html createHtml(String txt){
		return new Html(txt);
	}
	public static void main(String[] args) {
		String html = new myHttpClientDownloader(null).requestUrl(new Request("http://news.sina.com.cn/china/"));
		Html html2 = new Html(html);
		//System.out.println(html);
		System.out.println(html2.xpath("//span[@class='time-source']/text()").toString());
	}
	
}
