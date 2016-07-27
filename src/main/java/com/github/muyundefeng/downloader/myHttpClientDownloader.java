package com.github.muyundefeng.downloader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.muyundefeng.utils.Page;
import com.github.muyundefeng.utils.Request;
import com.github.muyundfeng.getProcess.Property;
import com.github.muyundfeng.pageProcessor.impl.SinaExtra;

public class myHttpClientDownloader implements Downloader {
    private   CloseableHttpClient httpClient;
    public  String CHARSET = "utf-8";
    private  final String POST = "POST";
    private  final String GET = "GET";
    private Logger logger = LoggerFactory.getLogger(myHttpClientDownloader.class);


    public myHttpClientDownloader(String charset) {
		// TODO Auto-generated constructor stub
    	httpClient = HttpClients.createDefault();
    	
    }
    public myHttpClientDownloader() {
		// TODO Auto-generated constructor stub
    	httpClient = HttpClients.createDefault();
    	
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	页面内容
     */
    public  String requestUrl(Request request){
    	return requestUrl(request,null);
    }
    
    public  String requestUrl(Request request,Map<String,String> params){
    	RequestBuilder requestBuilder = null;
    	System.out.println("downloading url "+request.getUrl());
    	if(request.getMethod().equals(GET)){
    		try{
    			requestBuilder = RequestBuilder.get(request.getUrl());
    		}
    		catch(Exception e){
    			logger.error(e.toString());
    		}
    		if(params != null)
    		 {
    			 for(Map.Entry<String, String> entry:params.entrySet()){
    				 requestBuilder.addHeader(entry.getKey(), entry.getValue());
    			 }
    		 }
    	}
    	if(request.getMethod().equals(POST)){
    		requestBuilder = RequestBuilder.post(request.getUrl());
	   		 if(params != null)
	   		 {
		   		 for(Map.Entry<String, String> entry:params.entrySet()){
		   			requestBuilder.addHeader(entry.getKey(), entry.getValue());
		   		 	}
	   		 }
    	}
    	RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
				.setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000)
				.setConnectTimeout(5000)
				.setCookieSpec(CookieSpecs.BEST_MATCH);
    	
    	String[] UserAgent = {
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
				"Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36"};
    	
    	Random random = new Random();
    	int rand = random.nextInt(UserAgent.length);
    	requestBuilder.addHeader("User-Agent",UserAgent[rand]);
    	if(Property.getUseProxy())
    	{
    		//获取代理服务器
    		String proxyHost = Property.getHostName();
			int proxyPort = Integer.parseInt(Property.getPort());
			HttpHost host = new HttpHost(proxyHost, proxyPort, "http");
			System.out.println(proxyHost);
			System.out.println(proxyPort);
			requestConfigBuilder.setProxy(host);
			//request.putExtra(Request.PROXY, host);
    	}
    	//设置代理服务器
    	requestBuilder.setConfig(requestConfigBuilder.build());
    	HttpUriRequest httpUriRequest = requestBuilder.build();
		try{
			
	    	CloseableHttpResponse response = httpClient.execute(httpUriRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpUriRequest.abort();
				throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
				//CHARSET = getCharset(response);
				System.out.println("charset is: "+CHARSET);
				result = EntityUtils.toString(entity, CHARSET);
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
    }
    
   public  String getCharset(HttpResponse httpResponse){
	   String html = httpResponse.getEntity().getContentType().getValue();
	   Pattern pattern = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");
	   Matcher matcher = pattern.matcher(html);
       if (matcher.find()) {
           String charset = matcher.group(1);
           if (Charset.isSupported(charset)) {
               return charset;
           }
       }
       return null;
   }
   public  Page downloadPage(Request request,Map<String, String> header){
	   String content = requestUrl(request, header);
	   Page page = new Page(content, request.getUrl(),CHARSET);
	   return page;
   } 
   public static void main(String[] args) {
	Request request = new Request("http://news.sina.com.cn/");
	System.out.println(new myHttpClientDownloader().requestUrl(request));
}
}