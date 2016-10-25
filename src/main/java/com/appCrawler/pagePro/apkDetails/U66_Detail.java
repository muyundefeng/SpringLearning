package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * 66u手机频道  http://android.66u.com/
 * @id 430
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class U66_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(U66_Detail.class);
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
		
		appDetailUrl = page.getUrl().toString();
		if(appDetailUrl.startsWith("http://ku.66u.com/"))
		{
			appName=page.getHtml().xpath("//div[@class='game-info clearfix']/h1/text()").toString();
			appDownloadUrl=page.getHtml().xpath("//div[@class='down-c1 down-c']/a[@class='btn-down-andr btn-down']/@href").toString();
			appCategory=page.getHtml().xpath("//div[@class='game-info-box side-mod-box']/div[2]/ul/li[4]/span/text()").toString();
			appUpdateDate=page.getHtml().xpath("//div[@class='game-info-box side-mod-box']/div[2]/ul/li[3]/text()").toString();
			if(appUpdateDate!=null)
			{
				appUpdateDate=appUpdateDate.split("：")[1];
			}
			appDescription=page.getHtml().xpath("//div[@class='mod-box-intro mod-box']").toString();
			appDescription=usefulInfo(appDescription);
			appScrenshot=page.getHtml().xpath("//div[@id='gamepics_show']/ul/li/img/@src").all();
			String string=SinglePageDownloader.getHtml(appDetailUrl);
			String str1=string.split("var screenShot =")[1];
			String string2=str1.split(";")[0].replace("'", "");
			String temp[]=string2.split("@@@");
			appScrenshot=Arrays.asList(temp);
		}
		else{
			appName=page.getHtml().xpath("//div[@class='game-info-c1-c1']/h1/text()").toString();
			appCategory=page.getHtml().xpath("//div[@class='game-info-c1-c2']/ul/li[4]/a/span/text()").toString();
			appUpdateDate=page.getHtml().xpath("//div[@class='game-info-c1-c2']/ul/li[5]/a/text()").toString();
			appVenderName=page.getHtml().xpath("//div[@class='game-info-c1-c2']/ul/li[6]/a/text()").toString();
			appSize=page.getHtml().xpath("//div[@class='game-info-c1-c2']/ul/li[7]/a/text()").toString();
			osPlatform=page.getHtml().xpath("//div[@class='game-info-c1-c2']/ul/li[8]/a/text()").toString();
			appDescription=page.getHtml().xpath("//div[@class='intro']").toString();
			appDescription=usefulInfo(appDescription);
			//String appScrenshot1=page.getHtml().xpath("//div[@class='game-pic mod-box1']/script/").toString();
			String string=SinglePageDownloader.getHtml(appDetailUrl);
			String str1=string.split("var screenShot =")[1];
			String string2=str1.split(";")[0].replace("'", "");
			String temp[]=string2.split("@@@");
			appScrenshot=Arrays.asList(temp);
			System.out.println(string2);
			appDownloadUrl=page.getHtml().xpath("//div[@class='downs']/a/@href").toString();
			
		}
		if(appDownloadUrl==null)
		{
			appName=page.getHtml().xpath("//div[@class='property']/h3/text()").toString();
			appUpdateDate=page.getHtml().xpath("//div[@class='property_cons']/ul/li[3]/text()").toString();
			if(appUpdateDate!=null)
			{
				appUpdateDate=appUpdateDate.split("：")[1];
			}
			appSize=page.getHtml().xpath("//div[@class='property_cons']/ul/li[4]/text()").toString();
			if(appSize!=null)
			{
				appSize=appSize.split("：")[1];
			}
			appCategory=page.getHtml().xpath("//div[@class='property_cons']/ul/li[6]/text()").toString();
			if(appCategory!=null)
			{
				appCategory=appCategory.split("：")[1];
			}
			appVenderName=page.getHtml().xpath("//div[@class='property_cons']/ul/li[7]/text()").toString();
			if(appVenderName!=null)
			{
				appVenderName=appVenderName.split("：")[1];
			}
			osPlatform=page.getHtml().xpath("//div[@class='property_cons']/ul/li[8]/text()").toString();
			if(osPlatform!=null)
			{
				osPlatform=osPlatform.split("：")[1];
			}
			appDownloadUrl=page.getHtml().xpath("//div[@class='game-down-box']/a/@href").toString();
			appDescription=page.getHtml().xpath("//div[@class='con']").toString();
			appDescription=usefulInfo(appDescription);
			String string=SinglePageDownloader.getHtml(appDetailUrl);
			String str1=string.split("var screenShot =")[1];
			String string2=str1.split(";")[0].replace("'", "");
			String temp[]=string2.split("@@@");
			appScrenshot=Arrays.asList(temp);
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
