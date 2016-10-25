package com.app.saveDB.Persistent;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;


public class SaveToRedis {
   
	public static Jedis redis = null;
	
	//存放到缓存队列中
	public volatile Queue<Map<String, String>> toQueue = new LinkedBlockingQueue<Map<String,String>>();
	public volatile Queue<Map<String, String>> fromQueue = new LinkedBlockingQueue<Map<String,String>>();
	public volatile Map<String, String> map = new HashMap<String,String>();
	private static int count = 0;
	private static final int COUNT = 5000;
	private Lock lock = new ReentrantLock();
	private Condition waitNewMap = lock.newCondition();
	private static final int timeout = 1;
	private static boolean isOver;
	
	public void waitNewDate(){
		lock.lock();
		try {
			waitNewMap.await(timeout, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			isOver = true;
			e.printStackTrace();
		}
		lock.unlock();
		
	}
	public static void init(){
		redis = new Jedis("localhost", 6379, 400000);//host,port,timeout
		redis.select(8);
		//redis.flushDB();
	}
	/**缓存队列
	 * @param appName :app name
	 * @param url:app download url
	 */
	public void saveToQueue(String appName,String url){
		map.clear();
		if(count<=COUNT)
		{
			System.out.println("appNames"+appName);
			if(appName!=null&&url!=null)
			{
				count++;
				map.put(appName, url);
				toQueue.add(map);
				transferQueueToRedis();
			}
//			Thread thread = new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					transferQueueToRedis();
//				}
//			});
//			thread.start();
		}
	}
	
	public void transferQueueToRedis(){
		//Iterator<Map<String, String>> iterator = queue.iterator();
		int key = 1;
//		while(key<=COUNT){
			if(toQueue.size()==0)
			{
				waitNewDate();
//				if(isOver==true)
//					;
				Map<String, String> map = toQueue.remove();
				if(map != null){
					System.out.println("map="+map);
					 redis.hmset("key_" + count, map);
				}
				key++;
			}
		}
//	}
}