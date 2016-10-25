package com.appCrawler.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;




































import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import junit.framework.Protectable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.util.EnumValues;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;




































import com.appCrawler.utils.PropertiesUtil;


public class Monitor extends TimerTask{
	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		1.使用json格式的数据 
//		2.参数包括 ip:本机ip port:端口号 
//		type:子模块种类 "00"为Searcher;"01"为Downloader;"02"为Transporter;"03"为Analyzer;"05"为fuzzy；"07"为静态分析；"08"为动态分析；
//		version:子模块版本号 
//		status:1为正常；0为失败 m
//		maxCap:最大负载 
//		currentLoad:当前执行任务数
//
//		心跳包发送地址：/heart_beat/{agentId} 类型：put
//		3-5s发一次
		logger.info("start send heartbeats");		
		String ip = "";
		String port ="3050";
		String type = "00";
		String version = "1.0";
		String status = "1";//1
		String maxCap = "20";//20
		String currentLoad = String.valueOf(ProcessNumber.modifyProcessNum("getNumber"));	
		String toIpAndPort = PropertiesUtil.getHeartBeatIpAndPort();
		String agentId = PropertiesUtil.getHeartBeatAgentId();
		
		try {
			ip = getLocalHost();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		 try {
//			 port = getPort();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		JSONObject obj = new JSONObject();
         obj.element("ip", ip);
         obj.element("port", port);
         obj.element("type", type);
         obj.element("version", version);
         obj.element("status", status);
         obj.element("maxCap", maxCap);
         obj.element("currentLoad", currentLoad);   
        sendData(obj, toIpAndPort, agentId);

         logger.info("send heartbeats:"+obj.toString()+"to "+"http://"+toIpAndPort+"/heart_beat/"+agentId);		
	}
	
//	private String getLocalIp(){//获取本地ip
//		InetAddress ia=null;
//		String localip="";
//		try {						
//			ia=InetAddress.getLocalHost();					
//			localip=ia.getHostAddress();			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.error("Can't get local ip address");
//		}
////		System.out.println(localip);
//		return localip;
//		
//	}
	
//	private synchronized static String getCurrentProcessNum(){//获取当前线程数
//		String path= PropertiesUtil.getProcessPath();
//		File file= new File(path);
//		if(!file.exists())
//			return "0";
//		FileReader reader;
//		try {
//			reader = new FileReader(file);
//			BufferedReader br = new BufferedReader (reader);
//			String content = br.readLine();
//			return content;
//		} catch (Exception e) {
//			System.out.println("Can't get process number");
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	private void sendData(JSONObject jsonObject,String toIpAndPort,String agentId){
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut("http://"+toIpAndPort+"/heart_beat/"+agentId);		
		try {
			StringEntity params= new StringEntity(jsonObject.toString());
			put.setEntity(params);
			HttpResponse response = httpClient.execute(put);
//			System.out.println("Response Code:"+response.getStatusLine().getStatusCode());
		} catch (IOException e) {
			logger.info("Fail to send heartbeats");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args){
		Monitor monitor = new Monitor();
		//monitor.run();
//		monitor.getNetworkInterfaceResult();
		try {
			System.out.println(monitor.getLocalHost());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	

	String getTomcatPort() throws Exception {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
//			System.out.println(mbs.getDomains().toString());
//			System.out.println(mbs.queryNames(null, null));
			Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
						Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			String port = "";
			for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
				ObjectName obj = i.next();
				port = obj.getKeyProperty("port");
			}
		//	return port;
			String hostname = InetAddress.getLocalHost().getHostName();
//			System.out.println("hostname="+hostname);
//			System.out.println("localHost="+InetAddress.getLocalHost());
			InetAddress[] addresses = InetAddress.getAllByName(hostname);
			System.out.println("addresses:");
			for (InetAddress inetAddress : addresses) {
				System.out.println(inetAddress.getHostAddress());
			}
			ArrayList<String> endPoints = new ArrayList<String>();
			for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
				ObjectName obj = i.next();
				String scheme = mbs.getAttribute(obj, "scheme").toString();
				String port2 = obj.getKeyProperty("port");
			//	System.out.println("port:"+port);
				for (InetAddress addr : addresses) {
					String host = addr.getHostAddress();
					String ep = scheme + "://" + host + ":" + port2;
					System.out.println(ep);
					endPoints.add(ep);
				}	
				}			
			return port;
		}
	

	
	private String getLocalHost() throws Exception{
		String localHost ="";
//		System.out.println("Host addr: " + InetAddress.getLocalHost().getHostAddress());  // often returns "127.0.0.1"
        Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();//获取所有的网络接口
        for (; n.hasMoreElements();)
        {
                NetworkInterface e = n.nextElement();
//                System.out.println("Interface: " + e.getName());
                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements();)
                {
                        InetAddress addr = a.nextElement();
                        String host=addr.getHostAddress().toString();
                        if(host.contains("10.108."))
                        	localHost = host;
                      //  System.out.println("  " + addr.getHostAddress());
                }
        }
        return localHost;
	}
	
	private String getPort() throws Exception{
		ObjectName service = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");  
		InitialContext ctx = new InitialContext();  
		System.out.println(ctx.getNameInNamespace());
		MBeanServer server = (MBeanServer)ctx.lookup("java:comp/env/jmx/runtime");  
		ObjectName rt =  (ObjectName)server.getAttribute(service,"ServerRuntime");  
		System.out.println("Server Name  : "+server.getAttribute(rt,"Name"));  
		System.out.println("Server Address : "+server.getAttribute(rt,"ListenAddress"));  
		System.out.println("Server Port : "+server.getAttribute(rt,"ListenPort"));  
		ctx.close();  
		return null;
	}
	
	

	

}
