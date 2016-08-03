package Redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import redis.clients.jedis.Jedis;



/**
 * @author lisheng
 *	本模块的主要redis中取出相关的数据信息
 */
public class FecthDataFromRedis {
   
	public static Jedis redis = null;
	
	//存放到缓存队列中
	
	public static void init(){
		redis = new Jedis("localhost", 6379, 400000);//host,port,timeout
		redis.select(8);
	}
	/**
	 * 输出缓存队列
	 */
	public static List<Map<String, String>> fetchFromRedis(){
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		Set<String> keys = redis.keys("*");
	    for(String key : keys) {
	    	dataList.add(redis.hgetAll(key));
	    }
	    return dataList;
	}
}