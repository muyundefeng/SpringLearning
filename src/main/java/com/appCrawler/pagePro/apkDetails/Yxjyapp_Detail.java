package com.appCrawler.pagePro.apkDetails;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
/**
 * 此爬取渠道为手机app应用商店
 * 渠道编号:379
 * 本渠道伪造主页:http://www.9ht.com/
 * 然后进行相关主页的替换
 * @author DMT
 */
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;


public class Yxjyapp_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yxjyapp_Detail.class);
	public static Apk getApkDetail(Map infos){
		Apk apk = null;
		String appName = null;				//app名字
		String appDetailUrl = null;			//具体页面url
		String appDownloadUrl = null;		//app下载地址
		String osPlatform = null ;			//运行平台
		String appVersion = null;			//app版本
		String appSize = null;				//app大小
		String appUpdateDate = null;		//更新日期
		String appType = null;				//下载的文件类型 apk？zip？rar？
		String appVenderName = null;			//app开发者  APK这个类中尚未添加
		String appDownloadedTime=null;		//app的下载次数
		String appDescription = null;		//app的详细描述
		List<String> appScrenshot = null;			//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
		appDownloadedTime=infos.get("downCount").toString();	
		appSize=Integer.parseInt(infos.get("packetSize").toString())/1024/1024+"MB";
		appVersion=infos.get("version").toString();
		appName=infos.get("title").toString();
		appDownloadUrl="http://www.41yx.cn:1889/"+infos.get("packet").toString();
		appCategory=infos.get("typeId").toString();
		//时间戳转化为时间
		long timeStamp=Long.parseLong(infos.get("releaseTime").toString());
		Date date=new Date(timeStamp);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		appUpdateDate=format.format(date);
		appScrenshot=ExtraScreen(infos.get("img").toString());
		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
			System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			System.out.println("appVenderName="+appVenderName);
			System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			System.out.println("appScrenshot="+appScrenshot);
			System.out.println("appCategory="+appCategory);
						
		}
		else return null;
		
		return apk;
	}

	//提取图片的相关的信息
	private static List<String> ExtraScreen(String str)
	{
		str=str.replace("{", "[{");
		str=str.replace("}", "}]");
		List<String> returnList=new ArrayList<String>();
		 ObjectMapper objectMapper=new ObjectMapper();
		 try
		 {
			List<LinkedHashMap<String, Object>> list = objectMapper.readValue(str, List.class);
	    	//System.out.println(list);
	    	for(int j=0;j<list.size();j++)
	         {
	        	 Map<String,Object> map=list.get(j);
	        	 Set<String> set = map.keySet();
	              for (Iterator<String> it = set.iterator();it.hasNext();) {
	                  String key = it.next();
	                  returnList.add("http://www.41yx.cn:1889/" + map.get(key));
	              }
	         }
		 }
	    	catch (Exception e) {
				// TODO: handle exception
			}
		 return returnList;
	}
}
