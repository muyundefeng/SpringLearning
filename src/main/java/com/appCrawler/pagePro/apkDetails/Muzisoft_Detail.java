package com.appCrawler.pagePro.apkDetails;
/**
 * 木子安卓http://www.muzisoft.com/
 * Muzisoft #217
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Muzisoft_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Muzisoft_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='desc']/h1/text()").toString();	
		appSize = page.getHtml().xpath("//div[@class='desc']/ul/li[2]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='desc']/ul/li[3]/span/text()").toString().split("日")[0].replace("年", "-").replace("月", "-");
	    appVersion=page.getHtml().xpath("//div[@class='desc']/ul/li[4]/text()").toString();	
		appDownloadedTime = "http://www.muzisoft.com/"+page.getHtml().xpath("//div[@class='desc']/ul/li[5]/script[1]/@src").toString();	
        appDownloadedTime=SinglePageDownloader.getHtml(appDownloadedTime,"get",null).replace("document.write('","").replace("');","");
		appDetailUrl = page.getUrl().toString();	
		
		appDownloadUrl = page.getHtml().xpath("//div[@class='down']/a/@onclick").toString();
		if(appDownloadUrl.contains("apkDown"))
		{
			String temp[]=appDownloadUrl.split(",");
			String section=temp[1].replaceAll("'", "");
			appDownloadUrl="http://www.muzisoft.com/mz/"+section.replace(")", "");
		}
		appDescription = page.getHtml().xpath("//div[@class='dl_kkbd']/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='inimg']//img/@src").all();
		for(int i=0;i<appScrenshot.size();i++){
			String url="http://www.muzisoft.com"+appScrenshot.get(i).trim();
			appScrenshot.set(i,url);
		}
//


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
	
//	private static String usefulInfo(String allinfoString)
//	{
//		String info = null;
//		while(allinfoString.contains("<"))
//			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
//			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//		info = allinfoString.replace("\n", "").replace(" ", "");
//		return info;
//	}
//	
//	
	
	

}
