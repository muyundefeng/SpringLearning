package com.appCrawler.pagePro.apkDetails;
/**
 * 好用助手
 * 网站主页：http://android.haoyongapp.com/
 * Aawap #639
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Haoyongapp_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Haoyongapp_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='app-info']/h1/span/text()").toString();	
		String raw = page.getHtml().xpath("//div[@class='app-info']/p[3]/text()").toString();
		System.out.println(raw);
		
		appVersion = raw.split("\\|")[0].replace("版本：", "");//属于正则表达式常见字符，需要转移
		appSize = raw.split("\\|")[1].replace("大小：", "");
		String raw1 = page.getHtml().xpath("//div[@class='app-info']/p[4]/text()").toString();
		appDownloadedTime = raw1.split("\\|")[0].replace("下载：", "");
		appUpdateDate = raw1.split("\\|")[1].replace(" 更新时间：", "");
		if(appDownloadedTime.contains("万次"))
			appDownloadedTime =appDownloadedTime.replace("万次", "")+"0000";
		if(appDownloadedTime.contains("亿次"))
			appDownloadedTime =appDownloadedTime.replace("亿次", "")+"00000000";
		appDownloadedTime = appDownloadedTime.replaceAll(" ", "");
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//a[@class='install-btn']/@href").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='min-pic']/ul/li/a/img/@src").all();
		appDescription = page.getHtml().xpath("//div[@class='desc-info']").toString();
		appDescription = usefulInfo(appDescription);
		

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
