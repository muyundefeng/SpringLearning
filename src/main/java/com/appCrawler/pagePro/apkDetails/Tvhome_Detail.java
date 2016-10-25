package com.appCrawler.pagePro.apkDetails;
/**
 * 电视之家
 * 网站主页：http://soft.tvhome.com/category/t0_o1_p0
 * Aawap #590
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Tvhome_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Tvhome_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='gameDown_info']/h4/a/text()").toString();	
		
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='gameDown_info']/div[1]/a[1]/@href").toString();	
		appDownloadedTime = page.getHtml().xpath("//div[@class='gameDown_info']/div[2]/text()").toString();	
		appDownloadedTime = appDownloadedTime!=null?appDownloadedTime.replace("次下载", ""):null;
		appDownloadedTime=appDownloadedTime.replace("  |  条评论  | ", "");
		appCategory = page.getHtml().xpath("//div[@class='gameDown_info']/div[3]/table/tbody/tr[2]/td[1]/text()").toString();	
		appCategory =appCategory.split("：")[1];
		appSize = page.getHtml().xpath("//div[@class='gameDown_info']/div[3]/table/tbody/tr[2]/td[2]/text()").toString();	
		appSize =appSize.split("：")[1];
		appVersion = page.getHtml().xpath("//div[@class='gameDown_info']/div[3]/table/tbody/tr[3]/td[1]/text()").toString();	
		appVersion =appVersion.split("：")[1];
		appUpdateDate = page.getHtml().xpath("//div[@class='gameDown_info']/div[3]/table/tbody/tr[4]/td[1]/text()").toString();	
		appUpdateDate =appUpdateDate.split("：")[1];
		appDescription = page.getHtml().xpath("//div[@class='appInfo']").toString();
		appDescription = usefulInfo(appDescription);
		appScrenshot = page.getHtml().xpath("//div[@class='imgShow']/ul/li/img/@src").all();
		

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
