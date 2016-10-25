package us.codecraft.webmagic.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;

import org.eclipse.jetty.util.statistic.SampleStatistic;

import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.selector.Html;
/**  
 * 本程序主要模拟jquery中的post提交函数,
 * $.post(url,[data],[fn],[type])
 * url:发送请求地址。
 * data:待发送 Key/value 参数。
 * callback:发送成功时回调函数。
 * type:返回内容格式，xml, html, script, json, text, _default。
 * 主要通过建立url相关链接,然后传入相关的参数即可,
 * 参数不止一个时,可以通过键值对组合以&连接在一起
 * 
 * @lisheng
 */  
public class PostSubmit {
	
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

	public static String postGetData(String urlStr,String params)
	{
		//设置相关的代理
		String proxyIpAndPort = getProxyIpAndPort();//获取代理的域名和端口号格式为xxx.xxx.xxx.xxx:xxxx
		String sTotalString = "1";   
		String proxyHost = "";
		int proxyPort = 0;
		if(PropertiesUtil.getCrawlerProxyEnable()){
			proxyHost = proxyIpAndPort.split(":")[0];//分离出域名
			proxyPort = Integer.parseInt(proxyIpAndPort.split(":")[1]);	//分离出端口号
		}
		try{
	        //URL url = new URL("http://www.droi.com/index.php/marketHome/marketClassifyList");
	        URL url = new URL(urlStr);     
	        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
	        HttpURLConnection urlConnection = null;
			//是否使用代理
			if(PropertiesUtil.getCrawlerProxyEnable())
			 urlConnection = (HttpURLConnection) url.openConnection(proxy);
			else 
				urlConnection = (HttpURLConnection) url.openConnection();
			if(urlStr.startsWith("http://ws2.aptoide.com"))
			{
				urlConnection.addRequestProperty("Content-Type", "application/json");
			}
			// URLConnection connection = url.openConnection();   
	        /**  
	         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。  
	         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：  
	         */  
			urlConnection.setDoOutput(true);   
	        /**  
	         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...  
	         */  
	        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream(),"gb2312");   
	        out.write(params); //向页面传递数据。post的关键所在！   
	        // remember to clean up   
	        out.flush();   
	        out.close();   
	        
	        // 一旦发送成功，用以下方法就可以得到服务器的回应：   
	        String sCurrentLine;   
	        sCurrentLine = "";   
	        sTotalString = "";   
	        InputStream l_urlStream;   
	        l_urlStream = urlConnection.getInputStream();   
	        BufferedReader l_reader = new BufferedReader(new InputStreamReader(   
	                l_urlStream));   
	        while ((sCurrentLine = l_reader.readLine()) != null) {   
	        	 sTotalString=sTotalString+sCurrentLine;
	        }    
		}
		catch(Exception e)
		{
			
		}
        return sTotalString;
	}
	
	public static String WrappRequest(String pkg,String appId){
		String jsonParamter = "{\"cpuid\":\"NoInfo\",\"lang\":\"zh_CN\",\"q\":\"bwF4u2RrPTE5Jm1heFNjcmv1bj1ub3JtYWwmbwF4R2x1cz0yLjAmbx1DUFU8YXJtZWFiaS12N2EsYXJtZWFiaSZteUR1bnNpdHk9MZIWJm15QXBOPTQ4MA\",\"offset\":0,\"mature\":True,\"limit\":0,\"info\":False,\"aptoide_vercode\":480,\"node\":{\"versions\":{\"package_name\":\""+pkg+"\"},\"meta\":{\"app_id\":\""+appId+"\",\"package_name\":\""+pkg+"\"}}}";
		String json = postGetData("http://ws2.aptoide.com/api/7/getApp",jsonParamter);
		 return json;
	}
	public static String SearchWrappRequest(String md5sum){
		String jsonParamter = "{\"cpuid\":\"NoInfo\",\"lang\":\"zh_CN\",\"q\":\"bwF4u2RrPTE5Jm1heFNjcmv1bj1ub3JtYWwmbwF4R2x1cz0yLjAmbx1DUFU8YXJtZWFiaS12N2EsYXJtZWFiaSZteUR1bnNpdHk9MZIWJm15QXBOPTQ4MA\",\"offset\":0,\"mature\":True,\"limit\":0,\"info\":False,\"aptoide_vercode\":480,\"node\":{\"versions\":{\"apk_md5sum\":\""+md5sum+"\"},\"meta\":{\"apk_md5sum\":\""+md5sum+"\",\"store_name\":\"apps\"}}}";
		String json = postGetData("http://ws2.aptoide.com/api/7/getApp",jsonParamter);
		return json;
	}
	 public static void main(String[] args) throws IOException {   
		// String searchParameter = "search=qq&q=bwF4u2RrPTE5Jm1heFNjcmv1bj1ub3JtYWwmbwF4R2x1cz0yLjAmbx1DUFU8YXJtZWFiaS12N2EsYXJtZWFiaSZteUR1bnNpdHk9MZIWJm15QXBOPTQ4MA&options=%28limit%3d7%3Brepos%3Dapps%3Bmature%3Dtrue%3B%29&mode=json";
		String url = "https://android.clients.google.com/auth";
		String json = PostSubmit.postGetData(url, null);
		 
		 System.out.println(json);
	 }   
}