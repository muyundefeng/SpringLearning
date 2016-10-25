package com.appCrawler.pagePro.apkDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Vimoo_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Vimoo_Detail.class);
	public static Apk getApkDetail(Page page){
		Apk apk = null;
		String appName = null;				//app名字
		String appDetailUrl = null;		 	//具体页面url
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
		
		appDetailUrl = page.getUrl().toString();
		appName = page.getHtml().xpath("//div[@class='contenttitle']/text()").toString();
		if(appName == null) return null;
		appVenderName = page.getHtml().xpath("//font[@class='text_049']/text()").toString();
		appUpdateDate = page.getHtml().xpath("//font[@class='text_time']/text()").toString();
		appDownloadedTime = getDownloadTime("http://www.vimoo.cn"+page.getHtml().xpath("//font[@class='text_f00']/script/@src").toString());
		appDownloadUrl = page.getHtml().xpath("//div[@class='contentdownurl']/a/@href").toString();
			
//		System.out.println("appName="+appName);
//		System.out.println("appDetailUrl="+appDetailUrl);
//		System.out.println("appDownloadUrl="+appDownloadUrl);
//		System.out.println("osPlatform="+osPlatform);
//		System.out.println("appVersion="+appVersion);
//		System.out.println("appSize="+appSize);
//		System.out.println("appUpdateDate="+appUpdateDate);
//		System.out.println("appType="+appType);
//		System.out.println("appVenderName="+appVenderName);
//		System.out.println("appDownloadedTime="+appDownloadedTime);
//		System.out.println("appDescription="+appDescription);
//		System.out.println("appTag="+appTag);
//		System.out.println("appScrenshot="+appScrenshot);
//		System.out.println("appCategory="+appCategory);

		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
						
		}
		
		return apk;
	}
	

	private static String getDownloadTime(String urlString) {
		//System.out.println(urlString);
		String appDownloadedTime =null;
		String line=SinglePageDownloader.getHtml(urlString);
		if(line != null)
			appDownloadedTime =line.substring(line.indexOf("(")+1,line.indexOf(")"));

	return appDownloadedTime;
	}
	


}