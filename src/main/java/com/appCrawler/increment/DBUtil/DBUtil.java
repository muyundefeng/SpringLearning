package com.appCrawler.increment.DBUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import us.codecraft.webmagic.Request;

public class DBUtil {

	private  static Logger logger = LoggerFactory.getLogger(DBUtil.class);
	public  static Jedis  redis = new Jedis ("127.0.0.1",6379);//连接redis        
	/**将已经抓取过得url链接存放到数据库中
	 * @param url
	 */
	public  synchronized static void saveAccessedUrlDB(String url){
		logger.info("save to db Accessed url is {}", url);
		redis.sadd("AccessedUrl", url);
		
	}
	
	/**将队列中的url存放到数据库中
	 * @param str
	 */
	public synchronized static void saveToAccessUrlDB(String str){
		logger.info("save to db json data is {}", str);
		redis.set("toAccessUrl", str);
	}
	
	/**将待抓取的url，转化为json字符串
	 * @param requests
	 */
	public synchronized static void saveToAccessUrlDB(Queue<Request> requests){
		String splitSymbol = "{\"toAccessUrlList\":[";
//		System.out.println(requests+"======");
		if(requests != null &&requests.size()>1)
		{
			for(Request request:requests){
//				if("http://shouji.baidu.com/".equals(request.getUrl().toString()))
//				{}
//				else
					splitSymbol +="{\"url\":" + "\"" + request.getUrl() +"\"},"; 
					
			}
			splitSymbol = splitSymbol.substring(0, splitSymbol.length()-1)+ "]}";
		}
		else {
			if(requests.size()==1)
//				if("http://shouji.baidu.com/".equals(requests.peek().getUrl().toString()))
//				{
//					splitSymbol +="]}"; 
//					}
//				else
					splitSymbol +="{\"url\":" + "\"" + requests.peek().getUrl() +"\"}]}"; 
			else{
				splitSymbol +="]}"; 

			}
		}
		saveToAccessUrlDB(splitSymbol);
	}
	

	/**读取带抓取数据库信息
	 * @return
	 */
	public static String getAccessJson(){
		String string = redis.get("toAccessUrl");
		logger.info("from redis json data is {}", string);
		return string;
	}
	
	/**读取数据库中保存的待抓取的url信息
	 * @return
	 */
	public static  List<String> readToAccessUrl(){
		String json = getAccessJson();
		System.out.println("to access url is"+json);
		if(json!=null)
		{
			List<String> requestList = new ArrayList<String>();
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Map<String, List<Map<String,String>>> map = objectMapper.readValue(json, Map.class);
				List<Map<String,String>> list = (List<Map<String,String>>)map.get("toAccessUrlList");
				for(Map<String, String> map1:list){
					requestList.add(map1.get("url").toString());
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return requestList;
		}
		else
			return null;
	} 
	
	/**还原数据库中已经存放的数据
	 * @param str
	 */
	public static List<String> restoreAccessedUrl(){
		Set<String> set = redis.smembers("AccessedUrl");
		logger.info("from redis Accessed url is {}", set);
		List<String> accessdUrls = new ArrayList<String>(set);
		return accessdUrls;
	}

}
