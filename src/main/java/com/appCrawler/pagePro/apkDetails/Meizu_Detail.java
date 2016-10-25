package com.appCrawler.pagePro.apkDetails;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;



import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Meizu_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Meizu_Detail.class);
	public static Apk getApkDetail(Page page){
		
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
					
		appDetailUrl = page.getUrl().toString();
		appName = page.getHtml().xpath("//div[@class='detail_top']/h3/text()").toString();		
		if(appName == null) return null;
		appVersion = page.getHtml().xpath("//div[@class='app_content ellipsis noPointer']/text()").toString();
		appCategory = page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[2]/div/a/text()").toString();
		appVenderName = page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[3]/div/a/text()").toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[5]/div/span/text()").toString();
		appSize =page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[6]/div/text()").toString();
		appUpdateDate = page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[7]/div/text()").toString();
		osPlatform = page.getHtml().xpath("//div[@class='app_download download_container']/ul/li[8]/div/text()").toString();
		appDescription = usefulInfo(page.getHtml().xpath("//div[@class='detail_content']").toString());
		appScrenshot = page.getHtml().xpath("//ul[@class='piclist mainlist']//img/@src").all();

	//	http://app.meizu.com/games/public/download.json?app_id=2628236
		String responseData = null;
		if(page.getUrl().toString().contains("/apps/"))
			responseData = getResponseData("http://app.meizu.com/apps/public/download.json?app_id="+getAppId(page));
		else
			responseData = getResponseData("http://app.meizu.com/games/public/download.json?app_id="+getAppId(page));
			
		JSONObject jsonObj = JSONObject.fromObject(responseData);
		jsonObj = JSONObject.fromObject(jsonObj.getString("value"));		
		appDownloadUrl = jsonObj.getString("downloadUrl");
		
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

		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//					String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
						
		}
		
		return apk;
	}
		
	private static String getResponseData(String urlString){
		
		return SinglePageDownloader.getHtml(urlString);
	}
	
	private static String getAppId(Page page) {
		String idString = null;
		idString = page.getHtml().xpath("//div[@class='price_bg downloading']/@data-appId").toString();
		return idString;
	}
	
	private static String usefulInfo(String allinfoString)
	{
		String info = null;
		if(allinfoString == null) return null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	
	

}
