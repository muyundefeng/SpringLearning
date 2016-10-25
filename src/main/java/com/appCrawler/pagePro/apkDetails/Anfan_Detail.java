package com.appCrawler.pagePro.apkDetails;
/**
 * 安锋网,网站主页:http://www.anfan.com/wangyou/time.html
 * 渠道编号:332
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Anfan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Anfan_Detail.class);
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
		if(appDetailUrl.startsWith("http://m.anfan"))
		{
			appName=page.getHtml().xpath("//div[@class='p-info']/h1/text()").toString();
			appDownloadedTime=page.getHtml().xpath("//div[@class='p-info']/p[2]/span[3]/text()").toString();
			if(appDownloadedTime!=null)
			{
				appDownloadedTime=appDownloadedTime.split("次")[0];
			}
			appCategory=page.getHtml().xpath("//div[@class='p-info']/p[2]/span[1]/text()").toString();
			appSize=page.getHtml().xpath("//div[@class='p-info']/p[2]/span[2]/text()").toString()+"B";
			appDownloadUrl=page.getHtml().xpath("//div[@class='fns']/div[@id='android']/a/@href").toString();
			if(appDownloadUrl!=null&&appDownloadUrl.contains("itunes"))
			{
				appDownloadUrl=null;
			}
			appDescription=page.getHtml().xpath("//div[@class='g-box-bd j-txt']/div/text()").toString();
			appScrenshot=page.getHtml().xpath("//ul[@class='list list-row']/li/img/@src").all();
		}
		else{
			appName = page.getHtml().xpath("//div[@id='zq-info-r']/h2/text()").toString();	
			appVersion=page.getHtml().xpath("//div[@id='zq-info-r']/ul/li[1]/span/text()").toString();
			appCategory=page.getHtml().xpath("//div[@id='zq-info-r']/ul/li[2]/span/a/text()").toString();
			appSize=page.getHtml().xpath("//div[@id='zq-info-r']/ul/li[3]/span/text()").toString();
			appVenderName=page.getHtml().xpath("//div[@id='zq-info-r']/ul/li[6]/text()").toString();
			appUpdateDate=page.getHtml().xpath("//div[@id='zq-info-r']/ul/li[7]/span/text()").toString();
			appDownloadUrl=page.getHtml().xpath("//div[@class='dl-btns clearfix']/a/@href").toString();
			if(appDownloadUrl!=null&&appDownloadUrl.contains("itunes"))
			{
				appDownloadUrl=null;
			}
			if((appDetailUrl+"#").equals(appDownloadUrl))
			{
				appDownloadUrl=null;
			}
			appDescription=page.getHtml().xpath("//div[@class='zq-intro']").toString();
			if(appDescription!=null)
			{
				appDescription=usefulInfo(appDescription);
			}
			appScrenshot=page.getHtml().xpath("//div[@class='screenshot-imgs']/dl/dd/img/@src").all();
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
