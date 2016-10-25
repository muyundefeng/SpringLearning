package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 专题兰
 * 网站主页：http://www.zhuantilan.com/
 * Aawap #586
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Zhuantilan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Zhuantilan_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='syXxTitleIns']/h1/text()").toString();	
		osPlatform=page.getHtml().xpath("//ul[@class='syXxList']/li[5]/text()").toString();
		osPlatform=osPlatform!=null?osPlatform.split("：")[1]:null;
		appCategory=page.getHtml().xpath("//div[@class='cl']/p[1]/a/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='cl']/p[2]/text()").toString();
		appUpdateDate=appUpdateDate!=null?appUpdateDate.split(":")[1]:null;
		appUpdateDate=appUpdateDate.replace("年", "-").replace("月", "").replace("日", "");
		
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='btn_trig']/a/@href").toString();
		appDescription=page.getHtml().xpath("//div[@id='soft-info']").toString();
		appDescription=appDescription!=null?usefulInfo(appDescription):null;
		appScrenshot=page.getHtml().xpath("//div[@id='soft-info']//img/@src").all();
		List<String> pics=new ArrayList<String>();
		for(String str:appScrenshot)
		{
			pics.add("http://www.zhuantilan.com"+str);
		}
		appScrenshot=pics;
		if(appDownloadUrl==null){
			appName = page.getHtml().xpath("//div[@class='syXxTitleIns']/h1/text()").toString();
			appCategory = page.getHtml().xpath("//div[@class='w mt10 c']/div[1]/p[1]/a/text()").toString();
			appUpdateDate = page.getHtml().xpath("//div[@class='w mt10 c']/div[1]/p[2]/text()").toString();
			if(appUpdateDate!=null&&appUpdateDate.contains(":")){
				appUpdateDate = appUpdateDate.split(":")[1];
			}
			appSize = page.getHtml().xpath("//div[@class='w mt10 c']/div[1]/p[3]/text()").toString();
			if(appSize!=null&&appSize.contains(":")){
				appSize = appSize.split(":")[1];
			}
			appDownloadUrl = page.getHtml().xpath("//a[@id='quickDown']/@href").toString();
			appDescription = page.getHtml().xpath("//div[@class='syDeRe']").toString();
			appDescription = usefulInfo(appDescription);
			appScrenshot = page.getHtml().xpath("//div[@class='syDeRe']//img/@src").all();
			List<String> pics1 = new ArrayList<String>();
			for(String str:appScrenshot){
				pics1.add("http://www.zhuantilan.com"+str);
			}
			appScrenshot = pics1;
		}
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
