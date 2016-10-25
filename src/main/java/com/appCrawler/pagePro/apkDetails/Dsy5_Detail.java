package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
/**
 * 5DSY 网站主页:http://www.5dsy.cn/game
 * 渠道编号:327
 * 本应用商店一共九款app,将全部链接加入page中
 * 从map中读取相关app信息
 */

public class Dsy5_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Dsy5_Detail.class);
	public static Apk getApkDetail(Map<String,Object> map){
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
		appName =  map.get("name").toString();	
		
		String gameId=map.get("game_id").toString();
		appDetailUrl="http://www.5dsy.cn/game/"+gameId;
		String appScrenshot1=map.get("image").toString();
		//System.out.println(appScrenshot1);
		appScrenshot1=(appScrenshot1.replace("[", "")).replace("]", "");
		String temp[]=appScrenshot1.split(",");
		List<String> imgList=new ArrayList<String>();
		for(int i=0;i<temp.length;i++)
		{
			imgList.add("http://img.5dsy.cn"+temp[i]);
		}
		appScrenshot=imgList;
		String appSize1=map.get("size").toString();
		appSize=Integer.parseInt(appSize1)/1024+"MB";
		String rawStr=SinglePageDownloader.getHtml("https://api.5dsy.cn/game/"+gameId);
		if(rawStr.contains("android"))
		{
			int start=rawStr.indexOf("android");
			int end=rawStr.indexOf("banner_url");
			String str=rawStr.substring(start,end-4);
			String downloadUrl=str.split(",")[0];
			appDownloadUrl="http://img.5dsy.cn/"+(downloadUrl.replace("android\":\"", "")).replace("\"","");
			System.out.println(str);
			System.out.println(appDownloadUrl);
		}
		else{
			appDownloadUrl=null;
		}
		appDescription=map.get("intro").toString();
		appCategory=map.get("category").toString();
		
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
}
