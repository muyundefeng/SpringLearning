package com.appCrawler.pagePro.apkDetails;
/**
 * 丫丫玩  http://www.yayawan.com
 * Yayawan #362
 * @author tianlei
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Yayawan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yayawan_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='game_info over']/div[1]/h1/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='game_info over']/div[1]/p[2]/text()").toString().replace("下载：", "");
		if(appDownloadedTime!=null&&appDownloadedTime.contains("次"))
		{
			appDownloadedTime=appDownloadedTime.replace("次", "");
		}
		if(page.getHtml().xpath("//div[@class='info apk']/p[2]/text()").toString() == null){
			return null;
		}
		appVersion = page.getHtml().xpath("//div[@class='info apk']/p[2]/text()").toString().replace("游戏版本：", "");
		appSize = page.getHtml().xpath("//div[@class='info apk']/p[1]/text()").toString().replace("游戏大小：", "");
		appDescription = page.getHtml().xpath("//div[@class='sp4']/div[7]/p/text()").toString();
		appScrenshot = page.getHtml().xpath("//ul[@id='w_act_img']//a/img/@src").all();
		appDownloadUrl = page.getHtml().xpath("//p[@class='downbtn']/a[3]/@ex_url").toString();		
		
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
