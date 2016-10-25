package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 梦游游戏  http://www.9qmy.com/
 * Aawap #230
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Qmy9_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Qmy9_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='bou_title']/p/text()").toString();	
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl= page.getHtml().xpath("//div[@class='games_cont']/table/tbody/tr[9]/td/a/@href").toString();
		osPlatform=page.getHtml().xpath("//div[@class='games_cont']/table/tbody/tr[3]/td[2]/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='games_cont']/table/tbody/tr[4]/td[2]/text()").toString();
		//appUpdateDate
		appCategory=page.getHtml().xpath("//div[@class='games_cont']/table/tbody/tr[2]/td[2]/text()").toString();
		String appDescription1=page.getHtml().xpath("//div[@class='games_con_cen']/p").toString();
		if(appDescription1!=null)
		{
			appDescription=usefulInfo(appDescription1);
		}
		//appScrenshot=
		List <String> appScrenshot1=page.getHtml().xpath("//div[@class='games_con_cen']//img/@src").all();
		List<String> appScrenshot2 = new ArrayList<String>(appScrenshot1.size());
		//List <String> picList;
		for(int i=0;i<appScrenshot1.size();i++)
		{
			appScrenshot2.add("http://www.9qmy.com/"+appScrenshot1.get(i));
		}
		appScrenshot=appScrenshot2;
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
