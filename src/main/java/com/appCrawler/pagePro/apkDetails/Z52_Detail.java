package com.appCrawler.pagePro.apkDetails;
/**
 * Z52  
 *  #271
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;


public class Z52_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Z52_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='syXxTitleIn']/h1/text()").toString();
		System.out.println(appName);
		//对app名字进行提取，提取相关的版本信息
//		if(appName!=null)
//		{
//			if(appName.contains("V"))
//			{
//				String str[]=appName.split(" ");
//				if(str.length>=2)
//				{
//					appName=str[0];
//					appVersion=str[1];
//				}
//			}
//		}
		List<String> rawStr1=page.getHtml().xpath("//div[@class='syXxMiddle']/ul/li[1]/a/text()").all();
		appCategory="";
		for(int i=0;i<rawStr1.size();i++)
		{
			appCategory+=rawStr1.get(i)+" ";
		}
		appSize=page.getHtml().xpath("//div[@class='syXxMiddle']/ul/li[4]/span/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='syXxMiddle']/ul/li[5]/span/text()").toString();
		osPlatform=osPlatform.replace("/", "");
		if(osPlatform.contains("win")||osPlatform.contains("Vista"))
		{
			appName=null;
		}
		appUpdateDate=page.getHtml().xpath("//div[@class='syXxMiddle']/ul/li[7]/span/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appDescription=page.getHtml().xpath("//div[@class='syDeRe']").toString();
		appDescription=usefulInfo(appDescription);
		appScrenshot=page.getHtml().xpath("//div[@id='focus']/ul/li/a/img/@hrefs").all();
		//if(appDetail)
		String str[]=appDetailUrl.split("/");
		String id=str[4].replace(".html", "");
		System.out.println(id);
		String url="http://www.52z.com/soft/downview?id="+id;
		Html html=Html.create(SinglePageDownloader.getHtml(url));
		//System.out.println(html);
		
		List<String> appDownloadUrl1 = html.links("//div[@class='syDaLeft biaoti1']").all();
		for(String url1:appDownloadUrl1)
		{
			if(url1.contains(".apk"))
			{
				appDownloadUrl=url1;
				break;
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
