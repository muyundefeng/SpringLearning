package com.github.muyundefeng.downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.muyundefeng.utils.Page;
import com.github.muyundefeng.utils.Request;
import com.github.muyundefeng.utils.rmDumplicate;

import sun.net.www.protocol.http.HttpURLConnection;

public class HttpDownloader {
	
	public static Queue<Request> urlqueuque = new LinkedBlockingQueue<Request>();
	private static String baseUrl = "http://www.bupt.edu.cn";
	private static String homeUrl;
	public static Page getRequest(){
		Request request = urlqueuque.remove();
		System.out.println(request.getUrl());
		return preProcess(request);
	}
	public static Page preProcess(Request request){
		if(rmDumplicate.isRmove(request.getUrl())
				||request.getUrl().equals(homeUrl))
				//&&request.getUrl().startsWith(baseUrl))
			return download(request);
		return null;
	}
	public static Map<String, String> getHeader(Request request){
		if(request.header == null){
			System.out.println("there is no header");
			return null;
		}
		return request.header;
	}
	
	public static Page download(Request request){
		URL url = null;
		String line = "";
		String source = "";
		Map<String,String> header = getHeader(request);
		try {
			url = new URL(request.getUrl());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
			connection.setReadTimeout(5000);
			connection.connect();
			if(header != null){
				for(Map.Entry<String, String> entry:header.entrySet())
				{
					connection.setRequestProperty(entry.getKey().toString(), entry.getValue().toLowerCase());
				}
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line=reader.readLine())!=null)
			{
				source +=line;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connet url failed!");
		} 
		Page page = new Page(source, request.getUrl() , "utf-8");
		return page;
	}
	
	public static void extractUrl(Page page){
		//System.out.println(page.getSource());
		Document doc = Jsoup.parse(page.getSource(),baseUrl);
		Elements links = doc.select("a[href]"); 
		for (Element link : links) {
			  String linkHref = link.attr("href");
			  System.out.println(linkHref);
			  Request request = new Request(linkHref);
			  urlqueuque.add(request);
			}
	}
	
	public static void startRequest(Request request){
		if(!urlqueuque.contains(request)
				&&rmDumplicate.isRmove(request.getUrl())){
			//System.out.println(request.getUrl());
			urlqueuque.add(request);
			homeUrl = request.getUrl();
		}
	}
	public static void main(String[] args) {
		Request request = new Request("http://www.bupt.edu.cn/");
		startRequest(request);
		while(true)
			getRequest();
	}
}
