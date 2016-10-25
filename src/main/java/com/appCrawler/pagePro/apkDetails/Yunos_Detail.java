package com.appCrawler.pagePro.apkDetails;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 云os应用商店
 * 网站主页;http://apps.yunos.com/index.htm
 * app详细信息从客户端进行抓取
 * Aawap #646
 * @author lisheng
 */

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Yunos_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yunos_Detail.class);
	public static Apk getApkDetail(String pkg){
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
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			appDetailUrl = "http://apps.yunos.com/api.htm?system=zhushou&type=game&sort=hot&newlist=1&screen_width=720&screen_height=1280&phone_type=CHM-TL00H&phone_ver=4.4.2&apilevel=19&method=getAppDetail&pkg="+pkg;
			String jsonApp = SinglePageDownloader.getHtml(appDetailUrl);
			Map<String, Object> map5 = null;
			try {
				map5 = objectMapper.readValue(jsonApp, Map.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, Object> map6 = (Map<String, Object>)map5.get("result");
	        appDescription = map6.get("desc").toString();
	        System.out.println("appDescription");
			appVenderName = map6.get("developer").toString();
			
			appDownloadedTime = map6.get("downloadTimes").toString();
			appUpdateDate = map6.get("publishTime").toString();
			appSize = map6.get("size").toString();
			List<Map<String, Object>> list7 = (List<Map<String, Object>>)map6.get("previews");
			List<String> pics = new ArrayList<String>();
			for(Map<String, Object> map8:list7){
				pics.add(map8.get("addr").toString());
			}
			appScrenshot = pics;
			appName = map6.get("name").toString();
			String appDownloader = "http://apps.yunos.com/api.htm?method=getDownloadAddr&pkg="+pkg;
			Map<String, Object> map9 = null;
			try {
				map9 = objectMapper.readValue(SinglePageDownloader.getHtml(appDownloader),Map.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, Object> map10 =(Map<String, Object>) map9.get("result");
			appDownloadUrl = map10.get("downloadUrl").toString();
		}
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
