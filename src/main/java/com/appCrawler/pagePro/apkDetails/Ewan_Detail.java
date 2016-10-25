package com.appCrawler.pagePro.apkDetails;
/**
 * 上海益玩  http://www.ewan.cn/
 * Ewan #303
 * @author tianlei
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Ewan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Ewan_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='gameinfo clearfix']/span/h4/text()").toString();
		appCategory  = page.getHtml().xpath("//div[@class='gameinfo clearfix']/span/p[1]/text()").toString().split("：")[1];
		appSize= page.getHtml().xpath("//div[@class='gameinfo clearfix']/span/p[3]/text()").toString().split("：")[1];
		appVersion = page.getHtml().xpath("//div[@class='gameinfo clearfix']/span/p[4]/text()").toString().split("：")[1];
		appDetailUrl = page.getUrl().toString();
		appDescription = page.getHtml().xpath("//div[@class='gm_description']/p/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='pic']/img/@src").all();
		appDownloadUrl = page.getHtml().xpath("//div[@class='appdl']/a/@href").toString();		
		for(int i =0 ; i<appScrenshot.size();i++){
			appScrenshot.set(i, "http://www.ewan.cn"+appScrenshot.get(i));
		}
		
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
