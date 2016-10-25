package com.appCrawler.pagePro.apkDetails;
/**
 * 金立游戏中心（单机）  http://game.gionee.com/
 * Gionee #350
 * @author tianlei
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Gionee_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Gionee_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='game_intro']/h4/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appVersion = page.getHtml().xpath("//div[@class='game_intro']/p[1]/span[3]/text()").toString().replace("版本：", "");
		appSize = page.getHtml().xpath("//div[@class='game_intro']/p[1]/span[2]/text()").toString().replace("大小：", "");
		appCategory = page.getHtml().xpath("//div[@class='game_intro']/p[1]/span[1]/text()").toString();
		appUpdateDate = page.getHtml().xpath("//ul[@class='intro_list']/li[2]/text()").toString().replace("更新日期：", "");
		appDescription = page.getHtml().xpath("//div[@id='j_content']/text()").toString();
		appScrenshot = page.getHtml().xpath("//ul[@class='slide_pic clearfix']//img/@src").all();
		appDownloadUrl = page.getHtml().xpath("//p[@class='btn_area']/a[2]/@href").toString();		
		
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
//			Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//					String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
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
