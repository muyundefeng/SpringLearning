package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 安卓在线
 * 网站主页:http://www.androidonline.net/software/index.html
 * 渠道编号:376
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Androidonline_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Androidonline_Detail.class);
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
		appDetailUrl=page.getUrl().toString();
		appName = page.getHtml().xpath("//dt[@class='g_name']/span/font/b/text()").toString();	
		appCategory=page.getHtml().xpath("//div[@class='info_m']/ul/li[1]/span/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='info_m']/ul/li[2]/span/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='info_m']/ul/li[6]/span/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='info_m']/ul/li[3]/span/text()").toString();
		//System.out.println(page.getHtml());
		//System.out.println(appUpdateDate);
		//System.out.println(appName);
		appSize=appSize.equals("未知")?null:appSize;
		appDownloadUrl=page.getHtml().xpath("//div[@id='down']/ul/li/a/@href").toString();
		appDownloadedTime=page.getHtml().xpath("//div[@class='info_m']/ul/li[7]/span/text()").toString();
		appScrenshot=page.getHtml().xpath("//dl[@class='a2_info']/dd/div/img/@src").all();
		//System.out.println(appScrenshot.size());
		List<String> newList=new ArrayList<String>();
		if(appScrenshot!=null&&appScrenshot.size()!=0)
		{
			if(!(appScrenshot.get(0).contains("http")))
			{
				for(String url:appScrenshot)
				{
					newList.add("http://www.androidonline.net"+url);
				}
				appScrenshot=newList;
			}
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
