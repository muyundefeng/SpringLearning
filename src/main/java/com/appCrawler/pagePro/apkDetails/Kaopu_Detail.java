package com.appCrawler.pagePro.apkDetails;

/**
 * 靠谱助手  http://www.kpzs.com/apps/applist
 * Kaopu #368
 * @author tianlei
 */

import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Kaopu_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Kaopu_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='apC']/h1/strong/text()").toString().replace("电脑版", "");
		appDetailUrl = page.getUrl().toString();
		appSize = page.getHtml().xpath("//div[@class='apC']/p[2]/span[1]/em/text()").toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='apC']/p[2]/span[2]/em/text()").toString().replace("万+","0000");
		appUpdateDate = page.getHtml().xpath("//div[@class='apC']/p[3]/span/em/text()").toString();	
		appCategory = page.getHtml().xpath("//ul[@class='sideBar-appDetail']/li[1]/div/a/text()").toString();
		appDescription = page.getHtml().xpath("//p[@class='ttC']/text()").toString();
		appScrenshot = page.getHtml().xpath("//ul[@class='slidelist clearfix']//a/img/@src").all();
		appDownloadUrl = page.getHtml().xpath("//div[@class='apB']/p[2]/a[2]/@href").toString();		
		
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
		else return null;
		return apk;
	}		

}
