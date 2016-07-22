package com.github.muyundefeng.downloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.github.muyundefeng.utils.Page;
import com.github.muyundefeng.utils.Request;
import com.github.muyundefeng.utils.reasonalUrl;
import com.github.muyundefeng.utils.rmDumplicate;
import com.github.muyundfeng.getProcess.Property;

import sun.net.www.protocol.http.HttpURLConnection;

public class HttpDownloader implements Downloader{
	
	private static BlockingQueue<Request> urlqueuque = new LinkedBlockingQueue<Request>();
	private static String baseUrl = "http://www.pku.edu.cn/";
	private static String homeUrl;
	private static int flag = 1;
	
	public static Page getRequest(){
		Request request = null;
		try {
			request = urlqueuque.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(request.getUrl());
		return preProcess(request);
	}
	
	public static Page preProcess(Request request){
//		if(rmDumplicate.isRmove(request.getUrl())){
//				//&&request.getUrl().startsWith(baseUrl))
			return download(request);
	//	}
		
	//	return null;
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
		boolean useProxy= Property.getUseProxy();
		String proxy = Property.getProxy();
		Map<String,String> header = getHeader(request);
		try {
			url = new URL(request.getUrl());
			HttpURLConnection connection = null;
			System.out.println("the proxy this" +proxy);
			if(useProxy == true)
				connection = (HttpURLConnection)url.openConnection(getProxy(proxy));
			//connection = (HttpURLConnection)url.openConnection();
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
		extractUrl(page);
		return page;
	}
	
	public static void extractUrl(Page page){
		//System.out.println(page.getSource());
		Document doc = Jsoup.parse(page.getSource(),baseUrl);
		Elements links = doc.select("a[href]"); 
		for (Element link : links) {
			  String linkHref = link.attr("href");
			 // System.out.println(linkHref);
			 // linkHref = linkHref.startsWith("http")?linkHref:baseUrl+linkHref;
			  Request request = null;
			  if(reasonalUrl.isReasonalUrl(linkHref)&&rmDumplicate.isRmove(getRealPath(linkHref,page.getUrl())))
			  { 
				  request = new Request(getRealPath(linkHref,page.getUrl()));
				  System.out.println(getRealPath(linkHref,page.getUrl()));
			  }
			  try {
				if(request!=null)
					urlqueuque.put(request);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void startRequest(Request request){
		
		if(!urlqueuque.contains(request)){
			//System.out.println(request.getUrl());
			try {
				urlqueuque.put(request);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			homeUrl = request.getUrl();
		}
	}
	
	public static Proxy getProxy(String hostAndPort){
		String hostName = hostAndPort.split(":")[0];
		int port = Integer.parseInt(hostAndPort.split(":")[1]);
		return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostName, port));
	}
	//相对链接补全
	public static String getRealPath(String url,String currentUrl){
		String absouluteUrl="";
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			URL url1 = new URL(currentUrl);
			URL url2 = new URL(url1,url);
			absouluteUrl = url2.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(absouluteUrl);
		return absouluteUrl;
	}

	@Override
	public String requestUrl(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
}
