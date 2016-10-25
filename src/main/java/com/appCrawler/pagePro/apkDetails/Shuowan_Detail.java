package com.appCrawler.pagePro.apkDetails;
/**
 * 说玩网
 * 网站主页：http://www.shuowan.com/list_0_1_0_0.html
 * Aawap #680
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Shuowan_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Shuowan_Detail.class);
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
		appName = page.getHtml().xpath("//span[@class='xsdt']/a/text()").toString();	
		appCategory = page.getHtml().xpath("//div[@class='zlz lf overf']/div[1]/div[1]/span/text()").toString();
		appSize = page.getHtml().xpath("//div[@class='zlz lf overf']/div[1]/div[2]/span/text()").toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='zlz lf overf']/div[2]/div[1]/span/text()").toString();
		appVersion = page.getHtml().xpath("//div[@class='zlz lf overf']/div[2]/div[2]/span/text()").toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='xlbt']/div[1]/a/@href").toString();
		if(appDownloadUrl!=null&&!appDownloadUrl.equals(""))
		{
			String refrence = page.getUrl().toString();
			//处理两次跳转 
		    String string=SinglePageDownloader.handleHttp302(appDownloadUrl, refrence);
		    appDownloadUrl = SinglePageDownloader.handleHttp302(string, refrence);
		    if(appDownloadUrl.equals("")){
		    	appDownloadUrl = null;
		    }
		}
		else{
			appDownloadUrl = null;
		}
	    appScrenshot = page.getHtml().xpath("//div[@id='Action_c']/ul/li/img/@src").all();
	    appDescription = page.getHtml().xpath("//div[@name='content']").toString();
	    appDescription = appDescription!=null?usefulInfo(appDescription):null;

		appDetailUrl = page.getUrl().toString();
		

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
