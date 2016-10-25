package com.appCrawler.pagePro.apkDetails;
/**
 * 偶玩游戏 http://www.ouwan.com/gamestore/
 * 渠道编号：325
 * 
 * 
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Ouwan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Ouwan_Detail.class);
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
		if(page.getUrl().toString().contains("detail"))
		{
			appName = page.getHtml().xpath("//div[@class='gamed-app-mes left']/h4/text()").toString();	
			appDetailUrl = page.getUrl().toString();
			appDownloadUrl = page.getHtml().xpath("//p[@class='clear']/a/@href").toString();
			appDownloadedTime = page.getHtml().xpath("//div[@class='gamed-app-mes left']/p[2]/text()").toString();
			if(appDownloadedTime!=null)
			{
				appDownloadedTime=appDownloadedTime.split("：")[1];
				appDownloadedTime=appDownloadedTime.replace("次", "");
			}
			appScrenshot=page.getHtml().xpath("//div[@class='sliderCon ml30 mr30 h300']/div/img/@src").all();
			appDescription=page.getHtml().xpath("//div[@class='gamed-intro']").toString();
			if(appDescription!=null)
			{
				appDescription=usefulInfo(appDescription);
			}
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
				return apk;
							
			}
			else 
				return null;
		}
			if(page.getUrl().regex("http://www\\.ouwan\\.com/.*").match()){
					//System.out.println("call me first");
					appName=page.getHtml().xpath("//div[@class='infod-bar-game']/h3/text()").toString();
					appSize=page.getHtml().xpath("//div[@class='infod-bar-game']/p[1]/text()").toString();
					if(appSize!=null)
					{
						if(appSize.contains("："))
						{
							appSize=appSize.split("：")[1];
						}
					}
					appDetailUrl = page.getUrl().toString();
					appCategory=page.getHtml().xpath("//div[@class='infod-bar-game']/p[2]/text()").toString();
					if(appCategory!=null)
					{
						if(appCategory.contains("："))
						{
							appCategory=appCategory.split("：")[1];
						}
					}
					appDescription=page.getHtml().xpath("//div[@class='gamehome-game-intro']").toString();
					if(appDescription!=null)
					{
						appDescription=usefulInfo(appDescription);
					}
					appDownloadUrl=page.getHtml().xpath("//p[@class='p10']/a/@href").toString();
					appScrenshot=page.getHtml().xpath("//div[@class='gamehome-screen-inner']/a/@href").all();
					
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
						return apk;
									
					}
					else 
						return null;
				}
			return null;
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
