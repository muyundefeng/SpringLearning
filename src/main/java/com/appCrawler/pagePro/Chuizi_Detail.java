package com.appCrawler.pagePro;


import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
/**
 * #197 锤子
 * Chuizi http://www.chuizi.com/
 * @author DMT
 *
 */

public class Chuizi_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Chuizi_Detail.class);
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
		
		osPlatform = page.getHtml().xpath("//div[@class='pge_mid']/h1/span/text()").toString();
		if(osPlatform ==null || !osPlatform.contains("Android"))
			return null;
		osPlatform = null;
		appName = page.getHtml().xpath("//div[@class='pge_mid']/h1/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='pge_dwn']/a/@href").toString();
		appCategory = page.getHtml().xpath("//div[@class='area site_nav']/a[4]/text()").toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='pge_mid']/ul/li[1]/span/text()").toString();
		appUpdateDate = page.getHtml().xpath("//div[@class='pge_mid']/ul/li[4]/time/text()").toString();
		appSize = getDetailInfo(page.getHtml().xpath("//div[@class='pge_mid']/ul/li[5]/text()").toString());
		appDescription = usefulInfo(page.getHtml().xpath("//div[@class='pge_inf']").toString());
		appScrenshot = page.getHtml().xpath("//div[@class='pge_img']//img/@src").all();
		
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
	public static String getVersion(String versionString){
		if(versionString == null || !versionString.contains("v"))
			return null;
		return versionString.substring(versionString.indexOf("v"),versionString.length());
	}
	
	private static String getDetailInfo(String detail) {
		
		if(detail!=null && !detail.endsWith("："))
			return detail.split("：")[1];
		else return null;
		
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
