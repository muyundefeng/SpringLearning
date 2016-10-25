package us.codecraft.webmagic.downloader;


/*
 * 即时下载一个页面，并获取页面内容保存到String中
 * (1)如果是post请求，map1里保存参数
 * (2)如果map2不为空，则里面保存的是需要添加的请求头内容
 * */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.bcel.generic.NEW;
import org.apache.http.conn.params.ConnConnectionParamBean;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.utils.PropertiesUtil;
import com.gargoylesoftware.htmlunit.Page;

import us.codecraft.webmagic.selector.Html;

public class SinglePageDownloader {
	
	public static int count=0;
	
	public static String getHtml(String urlString){
		return getHtml(urlString, "GET", null,null);
	}
	
	
	public static String getHtml(String urlString,String method,Map<String, String> map1){
		return getHtml(urlString, method, map1,null);
	}
	
	public static String getHtml(String urlString,String method,Map<String, String> map1,Map<String, String> map2){
		return getHtml(urlString,method,map1,map2,null);		
	}
	
	
	public static void download(String url,Map<String,String> map)
	{
	    int bytesum = 0;
        int byteread = 0;
		//File file=new File("D:\\info\\test.apk");

        try {
        	URL url1 = new URL(url);
            URLConnection conn = url1.openConnection();
            if(map != null){
				for (Map.Entry<String, String> entry : map.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("D:\\info\\test.apk");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	public static String getHtml(String urlString,String method,Map<String, String> map1,Map<String, String> map2,Map<String, List<String>> responseHeader){
		//Html html =null;
		System.out.println(urlString);
		System.out.println(map1);
		String lines = "";
		String sourcefile="";
		String proxyIpAndPort = getProxyIpAndPort();//获取代理的域名和端口号格式为xxx.xxx.xxx.xxx:xxxx
		String proxyHost = "";
		int proxyPort = 0;
		if(PropertiesUtil.getCrawlerProxyEnable()){
			proxyHost = proxyIpAndPort.split(":")[0];//分离出域名
			proxyPort = Integer.parseInt(proxyIpAndPort.split(":")[1]);	//分离出端口号
		}
		try {
			//打开一个网址，获取源文件			
			URL url=new URL(urlString);	
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  
			HttpURLConnection urlConnection = null;
			//是否使用代理
			if(PropertiesUtil.getCrawlerProxyEnable())
			 urlConnection = (HttpURLConnection) url.openConnection(proxy);
			else 
				urlConnection = (HttpURLConnection) url.openConnection();
			//是否需要添加请求头
			if(map2 != null){
				for (Map.Entry<String, String> entry : map2.entrySet()) {
					urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			//是否是post方法，如果是，添加参数
			if(method.equals("POST")){
				urlConnection.setRequestMethod("POST");// 提交模式	       
				urlConnection.setDoOutput(true);// 是否输入参数
				StringBuffer params = new StringBuffer();
		        // 表单参数与get形式一样
				for (Map.Entry<String, String> entry : map1.entrySet()) {  
					params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");			  
				} 

		        byte[] bypes = params.toString().getBytes();
		        urlConnection.getOutputStream().write(bypes);	// 输入参数		        
			}	
			
			//urlConnection.setFollowRedirects(true);
			urlConnection.setConnectTimeout(5000);//设置超时时间
			//if(urlConnection.)
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
//			if(responseHeader != null)
//			 responseHeader = urlConnection.getHeaderFields();
//			for (String key : responseHeader.keySet()) {
//				   System.out.println("key= "+ key + " and value= " + responseHeader.get(key));
//				  }
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}
			 //System.out.println(sourcefile);				
		} catch (Exception e) {
			System.out.println("download "+urlString+" error");
			e.printStackTrace();
		}
		//System.out.println(getProxyIpAndPort());
	//	html = Html.create(sourcefile);
		return sourcefile;
	}
	
	public static String handleHttp302(String appDownloadUrl,String appDetailUrl)
	{
		String 	str = "";
		//String sourcefile="";
		String proxyIpAndPort = getProxyIpAndPort();//获取代理的域名和端口号格式为xxx.xxx.xxx.xxx:xxxx
		String proxyHost = "";
		int proxyPort = 0;
		if(PropertiesUtil.getCrawlerProxyEnable()){
			proxyHost = proxyIpAndPort.split(":")[0];//分离出域名
			proxyPort = Integer.parseInt(proxyIpAndPort.split(":")[1]);	//分离出端口号
		}
		try {
			//打开一个网址，获取源文件			
			URL url=new URL(appDownloadUrl);	
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));  
			HttpURLConnection urlConnection = null;
			//是否使用代理
			if(PropertiesUtil.getCrawlerProxyEnable())
			 urlConnection = (HttpURLConnection) url.openConnection(proxy);
			else 
				urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET"); 
			//urlConnection.addRequestProperty("Referer",appDetailUrl);
		        //必须设置false，否则会自动redirect到重定向后的地址  
			urlConnection.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			urlConnection.addRequestProperty("Upgrade-Insecure-Requests", "1");
			urlConnection.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
			urlConnection.setInstanceFollowRedirects(false); 
			urlConnection.connect();  
		         
	        //判定是否会进行302重定向
	        if (urlConnection.getResponseCode() == 302) { 
	            //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
	            String location = urlConnection.getHeaderField("Location");  
	            System.out.println("跳转地址:" + location); 
	            str=location;
	            urlConnection.disconnect();
	        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
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
	
	public static void main(String[] args){	 
		 String url= "http://www.shuowan.com/downs/8396/1.html";
		 String refrence = "http://www.shuowan.com/game/8396.html";
		 
	       String string=handleHttp302(url, refrence);
	       System.out.println(handleHttp302(string, url));
	}
	private static String usefulInfo(String allinfoString)
	{
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	
}
