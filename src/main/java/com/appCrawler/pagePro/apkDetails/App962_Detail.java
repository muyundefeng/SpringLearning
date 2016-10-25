package com.appCrawler.pagePro.apkDetails;
/**
 * 962  http://www.962.net/
 * Aawap #538
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class App962_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(App962_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='g-game-h1']/h1/text()").toString();	
		appCategory=page.getHtml().xpath("//ul[@class='g-game-introdul']/li[2]/span/text()").toString();
		appSize=page.getHtml().xpath("//ul[@class='g-game-introdul']/li[3]/span/text()").toString();
		appVersion=page.getHtml().xpath("//ul[@class='g-game-introdul']/li[5]/span/text()").toString();
		appUpdateDate=page.getHtml().xpath("//ul[@class='g-game-introdul']/li[6]/span/text()").toString();
		
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//li[@class='m-clear-li']/a/@href").toString();
		appScrenshot=page.getHtml().xpath("//div[@class='screenshots-container']/table/tbody/tr/td/img/@src").all();
		appDescription=page.getHtml().xpath("//dd[@id='content']").toString();
		appDescription=appDescription==null?null:usefulInfo(appDescription);
		if(appDownloadUrl==null)
		{
			appName=page.getHtml().xpath("//dl[@class='left']/dt/h1/text()").toString();
			appDetailUrl=page.getUrl().toString();
			appVersion=page.getHtml().xpath("//dl[@class='left']/dt/span/text()").toString();
			appVersion=appVersion==null?null:appVersion.replace("安卓版", "");
			appSize=page.getHtml().xpath("//dl[@class='left']/dd[4]/ul/li[4]/span/text()").toString();
			appCategory=page.getHtml().xpath("//dl[@class='left']/dd[4]/ul/li[5]/span/text()").toString();
			appCategory=appCategory==null?null:appCategory.replace("类别：", "");
			appUpdateDate=page.getHtml().xpath("//dl[@class='left']/dd[4]/ul/li[6]/span/text()").toString();
			appUpdateDate=appUpdateDate==null?null:appUpdateDate.replaceAll("/", "-");
			appDescription=page.getHtml().xpath("//dd[@id='c_des_content']").toString();
			appDescription=appDescription==null?null:usefulInfo(appDescription);
			appScrenshot=page.getHtml().xpath("//ul[@class='show_img']/li/img/@src").all();
			appDownloadUrl=page.getHtml().xpath("//dl[@class='left']/dd[2]/a/@href").toString();
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
