package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 九城游戏中心  http://m.the9.com/html/Tegrazhuanqu/
 * Aawap #232
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Mthe9_Detail2 {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mthe9_Detail2.class);
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
		appName = page.getHtml().xpath("//div[@class='title gametitle']/h2/text()").toString();
		appDetailUrl=page.getUrl().toString();
		appSize=page.getHtml().xpath("//div[@class='infolist']/span[1]/text()").toString();
		appVenderName=page.getHtml().xpath("//div[@class='infolist']/span[2]/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='infolist']/span[3]/text()").toString();
		
		String info=page.getHtml().toString();
		if(info.contains("版权所有"))
		{
			int start=info.indexOf("版权所有");
			String rawString=info.substring(start);
			//System.out.println(rawString);
			int start2=rawString.indexOf("url");
			//String temp="";
			int end=0;
			for(int i=0;i<rawString.length();i++)
			{
				if(rawString.charAt(i)==';')
				{
					end=i;
					break;
				}
			}
			appDownloadUrl=rawString.substring(start2+7,end-1);
			appDownloadUrl="http://m.the9.com"+appDownloadUrl;
		}
		if(osPlatform.contains("Android")||osPlatform.contains("android")||osPlatform.contains("安卓"))
		{
			
		}
		else
		{
			appDownloadUrl=null;
		}
		System.out.println(appDownloadUrl);
		appDescription=page.getHtml().xpath("//div[@class='content']/p[1]/span/text()").toString();
	//	appScrenshot=page.getHtml().xpath("//div[@class='content']/p//img/@src").all();
		
		
		//System.out.println(appDownloadUrl);
//		osPlatform=page.getHtml().xpath("//div[@class='infolist']/span[3]/text()").toString();
//		appSize=page.getHtml().xpath("//div[@class='infolist']/span[1]/text()").toString();
//		if(appSize!=null)
//		{
//			if(!appSize.contains("MB"))
//			{
//				appSize=appSize+"MB";
//			}
//		}
//		appVenderName=page.getHtml().xpath("//div[@class='infolist']/span[2]/text()").toString();
//		
//		if(appDescription1!=null)
//		{
//			appDescription=usefulInfo(appDescription1);
//		}
//		//appScrenshot=page.getHtml().xpath("//div[@class='content']//img/@src").all();
		List <String> appScrenshot1=page.getHtml().xpath("//div[@class='content']/p//img/@src").all();
		List<String> appScrenshot2 = new ArrayList<String>(appScrenshot1.size());
		//List <String> picList;
		for(int i=0;i<appScrenshot1.size();i++)
		{
			appScrenshot2.add("http://m.the9.com"+appScrenshot1.get(i));
		}
		appScrenshot=appScrenshot2;
		
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