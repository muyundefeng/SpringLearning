package com.appCrawler.pagePro.apkDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Page;
/* 这个网站的apk的下载链接是通过执行js获取的，并且执行的结果获取到的下载链接还需要重定向一次才能获取到真正的下载链接
 * （1）getDirectDownloadUrl()这个函数是模拟js执行的结果然后和"http://www.coolapk.com"组合到一起成为下载链接urlString
 * （2）urlString这个下载链接还需要重定向一次才是真正的下载地址，getRedirectDownloadUrl()是获取重定向以后的地址
 * */
import us.codecraft.webmagic.downloader.SinglePageDownloader;



public class GetTrueDownloadUrlForCoolapk {
	private static String cookies=null;
	static String proxyIpAndPort = getProxyIpAndPort();//获取代理的域名和端口号格式为xxx.xxx.xxx.xxx:xxxx
	static String proxyHost = "";
	static int proxyPort = 0;
	static Proxy proxy = null;
	static{	
	if(PropertiesUtil.getCrawlerProxyEnable()){
		proxyHost = proxyIpAndPort.split(":")[0];//分离出域名
		proxyPort = Integer.parseInt(proxyIpAndPort.split(":")[1]);	//分离出端口号
	}
	proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	}
	
	
	public static String getDownloadUrlForCoolapk(String url)  {  
		String urlString = "http://www.coolapk.com"+getDirectDownloadUrl(url);
		String trueUrl = getRedirectDownloadUrl(urlString);
		return trueUrl;
    } 
	
	private static String getRedirectDownloadUrl(String urlString){
		String location="";
		 try {  
	            String url = "http://www.coolapk.com/dl?pn=com.wallstreetcn.news&v=MTc3OTE&h=759478cfnt9be7"; 
	            url = urlString;
	       
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = null; 
	           // System.out.println("proxyhost="+proxyHost+" proxyport="+proxyPort);
	            if(PropertiesUtil.getCrawlerProxyEnable())
	            	conn = (HttpURLConnection) serverUrl.openConnection(proxy);
					else 
					conn = (HttpURLConnection) serverUrl.openConnection();
	            
	            conn.setRequestMethod("GET");  
	            conn.setInstanceFollowRedirects(false);  
	            
	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
	            conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");  
	            conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8"); 
	            conn.addRequestProperty("Connection", "keep-alive"); 
	            //conn.addRequestProperty("Cookie", "auth=deleted; expires=Thu, 01-Jan-1970 00:00:01 GMT; Max-Age=0; path=/; domain=.coolapk.com, SESSID=d011b238_55d2a40cbf8ec8_42132887_1439867916_7836; path=/; domain=.coolapk.com");
	            conn.addRequestProperty("Cookie", cookies);						
	            conn.addRequestProperty("Host", "www.coolapk.com"); 
	   //         conn.addRequestProperty("Referer", "http://www.coolapk.com/apk/com.google.android.googlequicksearchbox");  
	            conn.addRequestProperty("Upgrade-Insecure-Requests", "1");  
	            conn.addRequestProperty("User-Agent",  
	                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");  
	            conn.connect();  
	             location = conn.getHeaderField("Location");  
	           // System.out.println("location:" + location);  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
		 return location;
	}
	
	private static String getDirectDownloadUrl(String urlString){
		//System.out.println("Getting "+urlString);
		String lines = "";
		String sourcefile="";		
		try {		
			URL url=new URL(urlString);		
			HttpURLConnection urlConnection = null;			
			if(PropertiesUtil.getCrawlerProxyEnable())
				 urlConnection = (HttpURLConnection) url.openConnection(proxy);
				else 
					urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			Map<String, List<String>> maps=urlConnection.getHeaderFields();			
			cookies = getCookies(maps);			
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}			
		} catch (Exception e) {
		}
		
		if(sourcefile.contains("apkDownloadUrl") && sourcefile.contains("function onDownloadApk"))		
			
			return  StringUtils.substringBetween(sourcefile, "var apkDownloadUrl = \"", "\";");
			//return sourcefile.substring(sourcefile.indexOf("apkDownloadUrl")+18,sourcefile.indexOf("function onDownloadApk")-6);
		return null;
	}

	private static String getCookies(Map<String, List<String>> maps){ 
		String cookieString =null;
		List<String> cookieList = maps.get("Set-Cookie");
		for (String cookie : cookieList) {
			//System.out.println("cookie="+cookie);
			if(cookie.contains("SESSID")){
				//System.out.println(cookie);
				cookieString = cookie;
			}
		}
		//System.out.println("cookieString="+cookieString);
		return cookieString.substring(0,cookieString.indexOf(";"));
	}
	
	public static String getProxyIpAndPort(){
		
		if (PropertiesUtil.getCrawlerProxyEnable()) {			
			String[] hostAndPost = PropertiesUtil.getCrawlerProxyHostAndPort();			
		//获取随机数
			Random random = new Random();
			int rand = random.nextInt(hostAndPost.length);
			return hostAndPost[rand];
		}
		return null;
	}
	
	
	
	
	
	
	public static void main(String[] args)  {  
		//SESSID=92037f82_566a3d04a5e806_77915912_1449803012_6779; 
		//Hm_lvt_7132d8577cc4aa4ae4ee939cd42eb02b=1449056445,1449126923,1449803027; 
		//Hm_lpvt_7132d8577cc4aa4ae4ee939cd42eb02b=1449804821
//		cookies = "SESSID=92037f82_566a3d04a5e806_77915912_1449803012_6779;"
//				+ "Hm_lvt_7132d8577cc4aa4ae4ee939cd42eb02b=1449056445,1449126923,1449803027;"
//				+ "Hm_lpvt_7132d8577cc4aa4ae4ee939cd42eb02b=1449804821";
//				;
//		getRedirectDownloadUrl("http://www.coolapk.com/dl?pn=com.google.android.googlequicksearchbox&v=NTAwMQ&h=c4960d28nz6cju");
		getDownloadUrlForCoolapk("http://www.coolapk.com/apk/com.google.android.googlequicksearchbox");
    } 
	

}
