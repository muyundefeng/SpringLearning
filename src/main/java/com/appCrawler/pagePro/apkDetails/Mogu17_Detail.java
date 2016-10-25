package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 蘑菇游戏
 * 网站主页：http://www.17mogu.com/youxi/
 * Aawap #410
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Mogu17_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mogu17_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='game-info']/h2/text()").toString();	
		appDetailUrl = page.getUrl().toString();
		appSize=page.getHtml().xpath("//div[@class='game-info']/p[1]/text()").toString();
		if(appSize!=null)
		{
			appSize=appSize.split("：")[1];
		}
		appCategory=page.getHtml().xpath("//div[@class='game-info']/p[2]/text()").toString();
		if(appCategory!=null)
		{
			appCategory=appCategory.split("：")[1];
		}
		appVersion=page.getHtml().xpath("//div[@class='game-info']/p[3]/text()").toString();
		if(appVersion!=null)
		{
			appVersion=appVersion.split("：")[1];
		}
		appDownloadUrl=page.getHtml().xpath("//div[@class='game-info']/p[4]/button/@onclick").toString();
		appDownloadUrl=appDownloadUrl.replace("window.open('", "").replace("','newwindow')", "");
		appScrenshot=page.getHtml().xpath("//div[@class='sly']/ul/li/img/@src").all();
		List<String> pics=new ArrayList<String>();
		for(String str:appScrenshot)
		{
			pics.add("http://www.17mogu.com"+str);
		}
		appScrenshot=pics;
		appDescription=page.getHtml().xpath("//div[@class='game-body']/p").toString();
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
