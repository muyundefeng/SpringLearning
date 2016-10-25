package com.appCrawler.pagePro.apkDetails;
/**
 * Gooyo http://www.gooyo.com/
 * Gooyo #201
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.fullstack.Gooyo;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Gooyo_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Gooyo_Detail.class);
	public static Apk getApkDetail(Page page){
		
		Apk apk = null;
		String appName = null;				//app名字       	 1
		String appDetailUrl = null;			//具体页面url	 1
		String appDownloadUrl = null;		//app下载地址
		String osPlatform = null ;			//运行平台
		String appVersion = null;			//app版本		 1
		String appSize = null;				//app大小		1
		String appUpdateDate = null;		//更新日期		1
		String appType = null;				//下载的文件类型 apk？zip？rar？
		String appVenderName = null;			//app开发者  APK这个类中尚未添加
		String appDownloadedTime=null;		//app的下载次数             1
		String appDescription = null;		//app的详细描述
		List<String> appScrenshot = null;	//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
		appName = page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[1]/td/text()").toString();	
		appUpdateDate = page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[2]/td[2]/text()").toString();	
		appSize = page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[3]/td[2]/text()").toString().trim();	
		appVersion = page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[3]/td[3]/text()").toString();	
		appDownloadedTime = page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[4]/td[1]/span/text()").toString();	
		appDetailUrl = page.getUrl().toString();
		appCategory= page.getHtml().xpath("//table[@class='appParTable']/tbody/tr[2]/td[3]/text()").toString();	
		appDescription = page.getHtml().xpath("//div[@class='paraSty']/text()").toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='downBtn']/a/@href").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='lastUpdate2']/ul/li/img/@src").all();
		

		


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
	
}
