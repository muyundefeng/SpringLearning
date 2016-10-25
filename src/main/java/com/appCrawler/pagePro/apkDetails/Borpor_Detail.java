package com.appCrawler.pagePro.apkDetails;

import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


/**
 * #195
 * Borpor
 * http://www.borpor.com/class.php
 * @author DMT
 *
 */

public class Borpor_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Borpor_Detail.class);
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
	
		
		appName = page.getHtml().xpath("//li[@class='xay']/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = "http://www.borpor.com/download.php?gid="+getApkId(page.getUrl().toString());
		appCategory = page.getHtml().xpath("//li[@class='sidebar']/a[3]/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@id='spic1']//img/@src").all();
		appDescription = usefulInfo(page.getHtml().xpath("//li[@class='xay_01']/span").toString());
		appVenderName = page.getHtml().xpath("//li[@class='xay_01']/table/tbody/tr[3]/td/table/tbody/tr[4]/td[2]/text()").toString();
		appVersion = page.getHtml().xpath("//li[@class='xay_01']/table/tbody/tr[3]/td/table/tbody/tr[5]/td[2]/text()").toString();
		appUpdateDate = page.getHtml().xpath("//li[@class='xay_01']/table/tbody/tr[3]/td/table/tbody/tr[6]/td[2]/text()").toString();
		appSize = page.getHtml().xpath("//li[@class='xay_01']/table/tbody/tr[3]/td/table/tbody/tr[8]/td[2]/text()").toString();
		
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
	
	public static String getApkId(String url){
		if(!url.contains("http://www.borpor.com/enddisplay.php?gid="))
			return null;
		else return url.substring(url.indexOf("gid")+4,url.length());
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
