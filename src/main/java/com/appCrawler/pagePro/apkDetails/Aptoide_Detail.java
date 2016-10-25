package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * Aptoide
 * 网站主页：https://www.aptoide.com/
 * 需要手机客户端才能可以获得相关信息
 * Aawap #654
 * @author lisheng
 */
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.utils.PostSubmit;

public class Aptoide_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Aptoide_Detail.class);
	public static Apk getApkDetail(String json){
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
		try{
			System.out.println(json);
		if(json!=null)
		{
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = objectMapper.readValue(json, Map.class);
			Map<String, Object> map5 = (Map<String, Object>)map.get("nodes");
			Map<String, Object> map1 = (Map<String, Object>)map5.get("meta");
			//System.out.println(map1.get("size").toString());
			appSize = Integer.parseInt(((Map<String, Object>)map1.get("data")).get("size").toString())/1024/1024+"MB";
			appUpdateDate = ((Map<String, Object>)map1.get("data")).get("modified").toString();
			appVenderName = ((Map<String, Object>)((Map<String, Object>)map1.get("data")).get("developer")).get("name").toString();
			appDescription = ((Map<String, Object>)((Map<String, Object>)map1.get("data")).get("media")).get("description").toString();
	
			
			//appVersion = ((Map<String, Object>)map1.get("file")).get("vername").toString();
			appName = ((Map<String, Object>)map1.get("data")).get("name").toString();
			appDownloadUrl = ((Map<String, Object>)((Map<String, Object>)map1.get("data")).get("file")).get("path").toString();
			//appDescription = map1.get("description").toString();
			List<Map<String, Object>> list =(List<Map<String, Object>>)((Map<String, Object>)((Map<String, Object>)map1.get("data")).get("media")).get("screenshots");
			List<String> pics = new ArrayList<String>(); 
			for(Map<String,Object> map3:list){
				pics.add(map3.get("url").toString());
			}
			appScrenshot = pics;}}

			catch(Exception e){}
		
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
	
	private static String usefulInfo(String allinfoString)
	{
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	
	
	
	

}
