package com.appCrawler.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * get info from properties file
 * @author buildhappy
 *
 */
public class PropertiesUtil {
	private static Properties pro = null;
	private static int turn = 0;
		
	static{
		pro = new Properties();
		//test environment test.asec.buptnsrc.com				
//		String fullPath = System.getProperty("user.dir")+"/webapps/baseInfo.properties";
//		InputStream in = null;		
//		try {
//			in = new FileInputStream(new File(fullPath));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		//local environment
		InputStream in = PropertiesUtil.class.getResourceAsStream("/baseInfo.properties");		
//		
		try {
			pro.load(in);
		} catch (IOException e) {
			
			e.printStackTrace();
		}			
	}
	
	public static void test(){
		System.out.println(pro.getProperty("crawler.test"));
	}
	public static int getRetryTimes(){
		
		return Integer.parseInt(pro.getProperty("crawler.retry.times"));
	}
	
	public static int getInterval(){
		return Integer.parseInt(pro.getProperty("crawler.interval"));
	}
	
	public static String getCrawlerDataPath(){
		return pro.getProperty("crawler.data.path");
	}
	
	public static String getProcessPath(){
		return pro.getProperty("crawler.process.path");
	}
	
	public static String getHeartBeatIpAndPort(){
		return pro.getProperty("crawler.monitor.heartbeats.ip_port");
	}
	
	public static String getHeartBeatAgentId(){
		return pro.getProperty("crawler.monitor.heartbeats.agentId");
	}
	
	//use proxy or not
	public static boolean getCrawlerProxyEnable(){
		return pro.getProperty("crawler.proxy.enable").equals("0");
	}	
	
	public static String[] getCrawlerProxyHostAndPort(){
		String[] hostAndPort = pro.getProperty("crawler.proxy.host_port").split(",");		
		return hostAndPort;		
	}
	
	public static String[] getCrawlerProxyHostPortWeight(){
		String[] weight = pro.getProperty("crawler.proxy.host_port_weight").split(",");
		return weight;
	}
	
	public static String getSendDataPort(){		
		return pro.getProperty("crawler.sendData.port");
	}
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getRetryTimes());
		System.out.println(PropertiesUtil.getInterval());
		System.out.println(PropertiesUtil.getCrawlerDataPath());
		System.out.println(PropertiesUtil.getCrawlerProxyEnable());
	}

}
