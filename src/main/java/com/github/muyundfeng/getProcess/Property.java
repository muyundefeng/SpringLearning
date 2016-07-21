package com.github.muyundfeng.getProcess;

import java.io.InputStream;
import  java.util.Properties;  
public class Property {

	public static String proxy;
	public static String filePath;
	public static boolean useProxy;
	private static String hostName;
	private static String port;
	
	static{
		Properties prop = new Properties();
		InputStream inputStream =Property.class.getResourceAsStream("/baseInfo.properties");
		try{
			prop.load(inputStream);
			proxy = prop.getProperty("proxy").trim();
			hostName = proxy.split(":")[0];
			port = proxy.split(":")[1];
			filePath = prop.getProperty("filepath").trim();
			useProxy = prop.getProperty("enable").trim().equals("1")?true:false;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getProxy(){
		return proxy;
	}
	
	public static String getFilePath(){
		return filePath;
	}
	
	public static boolean getUseProxy(){
		return useProxy;
	}
	public static void main(String[] args) {
		//readProxy();
		System.out.println(filePath);
	}

	public static String getHostName() {
		return hostName;
	}

	public static void setHostName(String hostName) {
		Property.hostName = hostName;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		Property.port = port;
	}
	
}
