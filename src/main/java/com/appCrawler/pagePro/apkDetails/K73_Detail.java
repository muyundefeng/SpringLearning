package com.appCrawler.pagePro.apkDetails;
/**
 * K73电玩之家
 * http://www.k73.com/
 * Aawap #427
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;


public class K73_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(K73_Detail.class);
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
		if(page.getUrl().toString().startsWith("http://www.k73.com/ku"))
		{
			appName = page.getHtml().xpath("//div[@class='game-info']/dl/dt/h1/a/text()").toString();	
			appCategory = page.getHtml().xpath("//div[@class='game-info']/dl/dd/p[2]/span[1]/text()").toString();	
			appUpdateDate = page.getHtml().xpath("//div[@class='game-info']/dl/dd/p[3]/a/text()").toString();	
			appScrenshot=page.getHtml().xpath("//ul[@id='loop-main']/li/img/@src").all();
			appDescription=page.getHtml().xpath("//div[@id='k-text']").toString();
			if(appDescription!=null)
			{
				appDescription=usefulInfo(appDescription);
			}
			String appUrl=page.getHtml().xpath("//ul[@class='k-down']/li/a/@href").toString();
			System.out.println(appUrl);
			Html html=Html.create(SinglePageDownloader.getHtml(appUrl));
			List<String> urls=html.links("//ul[@class='downa_ul f-xzdz2 f-xzdz1']").all();
			for(String str:urls)
			{
				if(!str.endsWith(".exe"))
				{
					appDownloadUrl=str;
					break;
				}
				else
				{
					
				}
			}
			appSize=html.xpath("//dd[@class='info']/li[7]/font/text()").toString();
		
			appDetailUrl = page.getUrl().toString();
		}
		if(page.getUrl().toString().startsWith("http://www.k73.com/down/"))
		{
			Html html=page.getHtml();
			appName=html.xpath("//dl[@class='game_info']/dt/h1/text()").toString();
			appSize=html.xpath("//dd[@class='info']/li[7]/font/text()").toString();
			appCategory=html.xpath("//dd[@class='info']/li[2]/text()").toString();
			if(appCategory!=null)
			{
				appCategory=appCategory.split("：")[1];
			}
			appVenderName=html.xpath("//dd[@class='info']/li[4]/font/text()").toString();
			List<String> urls1=html.links("//ul[@class='downa_ul f-xzdz2 f-xzdz1']").all();
			for(String str:urls1)
			{
				if(!str.endsWith(".exe"))
				{
					appDownloadUrl=str;
					break;
				}
				else
				{
					
				}
			}
			appDescription=page.getHtml().xpath("//div[@class='game_about']").toString();
			appDescription=usefulInfo(appDescription);
			appScrenshot=page.getHtml().xpath("ul[@class='gallery']/li/a/img/@src").all();
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
