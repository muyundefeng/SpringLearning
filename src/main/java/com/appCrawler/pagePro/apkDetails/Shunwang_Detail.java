package com.appCrawler.pagePro.apkDetails;
/**
 * 渠道编号：334
 * 网站主页：http://mg.shunwang.com/
 *
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Shunwang_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Shunwang_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='fr game-data-main mt25']/h3/text()").toString();	
		
		appDetailUrl = page.getUrl().toString();
		appCategory=page.getHtml().xpath("//div[@class='fr game-data-main mt25']/ul/li[1]/span/text()").toString();
		appVenderName=page.getHtml().xpath("//div[@class='fr game-data-main mt25']/ul/li[4]/span/text()").toString();
		appScrenshot=page.getHtml().xpath("//ul[@class='game-pics-main']/li/img/@src").all();
		appDescription=page.getHtml().xpath("//div[@class='game-intro mt20']/p/span/text()").toString();
		String rawStr=page.getHtml().toString();
		int temp=0;
		int endIndex=0;
		if(rawStr.contains("Android下载"))
		{
			endIndex=rawStr.indexOf("Android下载");
			System.out.println(endIndex+"**********");
			temp=rawStr.indexOf("dialog-bd");
			System.out.println(temp+"**********");
			System.out.println(rawStr.substring(temp,endIndex));
			appDownloadUrl=rawStr.substring(temp,endIndex);
			if(!appDownloadUrl.contains("iOS下载"))
			{
				int startIndex=appDownloadUrl.indexOf("href=");
				int endIndex1=appDownloadUrl.indexOf("target");
				appDownloadUrl=appDownloadUrl.substring(startIndex+6, endIndex1-2);
			}
			else{
				int startIndex=appDownloadUrl.indexOf("iOS下载");
				System.out.println(startIndex);
				if(!appDownloadUrl.contains(".apk"))
				{
					appDownloadUrl=null;
				}
				else
				{
					int endIndex1=appDownloadUrl.indexOf(".apk");
					appDownloadUrl=appDownloadUrl.substring(startIndex, endIndex1);
					int index=appDownloadUrl.indexOf("href");
					appDownloadUrl=appDownloadUrl.substring(index+6)+".apk";
				}
			}
		}
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
