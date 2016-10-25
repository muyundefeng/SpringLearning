package com.appCrawler.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
/**  
 * 本程序主要模拟jquery中的post提交函数,
 * $.post(url,[data],[fn],[type])
 * url:发送请求地址。
 * data:待发送 Key/value 参数。
 * callback:发送成功时回调函数。
 * type:返回内容格式，xml, html, script, json, text, _default。
 * 主要通过建立url相关链接,然后传入相关的参数即可,
 * 参数不止一个时,可以通过键值对组合以&连接在一起
 */  
public class PostSubmit {

	public static String postGetData(String urlStr,String params)
	{
		String sTotalString = "1";   
		try{
	        //URL url = new URL("http://www.droi.com/index.php/marketHome/marketClassifyList");
	        URL url = new URL(urlStr);     
	        URLConnection connection = url.openConnection();   
	        /**  
	         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。  
	         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：  
	         */  
	        connection.setDoOutput(true);   
	        /**  
	         * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...  
	         */  
	        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "8859_1");   
	        out.write(params); //向页面传递数据。post的关键所在！   
	        // remember to clean up   
	        out.flush();   
	        out.close();   
	        /**  
	         * 这样就可以发送一个看起来象这样的POST：   
	         */  
	        // 一旦发送成功，用以下方法就可以得到服务器的回应：   
	        String sCurrentLine;   
	        sCurrentLine = "";   
	        sTotalString = "";   
	        InputStream l_urlStream;   
	        l_urlStream = connection.getInputStream();   
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
	 public static void main(String[] args) throws IOException {  
		//String json="{\"channelId\":\"1\",\"keyword\":\"qq\"}"; 
		//System.out.println(postGetData("http://localhost:9090/subtask/adasd",json));   
	    }   
}
