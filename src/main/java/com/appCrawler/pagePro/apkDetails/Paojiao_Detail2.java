package com.appCrawler.pagePro.apkDetails;

/**
 * 泡椒网手机软件下载 www.paojiao.cn/
 * Paojiao #133	网页打不开，未完成
 * (1)有网游和单击软件两种详细页面
 * @author DMT
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

public class Paojiao_Detail2 {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Paojiao_Detail2.class);

	public static Apk getApkDetail(Page page) {
		Apk apk = null;
		String appName = null; // app名字
		String appDetailUrl = null; // 具体页面url
		String appDownloadUrl = null; // app下载地址
		String osPlatform = null; // 运行平台
		String appVersion = null; // app版本
		String appSize = null; // app大小
		String appUpdateDate = null; // 更新日期
		String appType = null; // 下载的文件类型 apk？zip？rar？
		String appVenderName = null; // app开发者 APK这个类中尚未添加
		String appDownloadedTime = null; // app的下载次数
		String appDescription = null; // app的详细描述
		List<String> appScrenshot = null; // app的屏幕截图
		String appTag = null; // app的应用标签
		String appCategory = null; // app的应用类别
		
		appName=page.getHtml().xpath("//div[@class='app-right']/h2/span/text()").toString();
		appDescription=page.getHtml().xpath("//div[@class='app-right']/p/text()").toString();
		if(appDescription.contains("br"))
		{
			appDescription.replace("<br>", " ");
		}
		appDownloadUrl=page.getHtml().xpath("//div[@class='appdown']/a/@onclick").toString();
		appDownloadedTime=page.getHtml().xpath("//div[@class='stat']/span[1]/i/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='stat']/span[2]/i/text()").toString();
		String appInfo=page.getHtml().xpath("//div[@class='stat']/div").toString();
		appInfo=usefulInfo(appInfo);
		String appInfo1=appInfo;
		//String appInfo2=appInfo;
		
		if(appInfo!=null)
		{
			//System.out.println(appInfo);
			appInfo1=appInfo1.replaceAll("&nbsp", "");
			appInfo1=appInfo1.replaceAll(":", "");
			appInfo1=appInfo1.replaceAll("状态", "");
//			System.out.println(appInfo1);
			String []temp=appInfo1.split(";");
			if(appInfo1.contains("-"))
			{
				for(int i=0;i<temp.length;i++)
				{
					if(temp[i].contains("-"))
					{
						appUpdateDate=temp[i];
					}
				}
			}
			if(appInfo1.contains("开发商"))
			{
				appVenderName=temp[temp.length-1];
			}
		}
		int start=0;
		int end=0;
		if(appDownloadUrl!=null)
		{
			for(int i=0;i<appDownloadUrl.length();i++)
			{
				if(appDownloadUrl.charAt(i)=='(')
				{
					start=i;
				}
				if(appDownloadUrl.charAt(i)==')')
				{
					end=i;
				}
			}
		}
		appDownloadUrl=appDownloadUrl.substring(start+1,end);
		String []temp2=appDownloadUrl.split(",");
		appDownloadUrl=temp2[0].replace("'", "");
		appScrenshot=page.getHtml().xpath("//div[@class='gallery_box gallery']/a/@href").all();
//		if(nameString != null && nameString.contains("V"))
//		{
//			appName=nameString.substring(0,nameString.indexOf("V")-1);
//		
//		}
//		else if(nameString != null && nameString.contains("v"))
//		{
//			appName=nameString.substring(0,nameString.indexOf("v")-1);
//			
//		}
//		else if(nameString != null && nameString.contains("."))
//		{
//			appName=nameString.substring(0,nameString.indexOf(".")-1);
//			
//		}
//		else 
//		{
//			appName = nameString;
//		}
//			
		appDetailUrl = page.getUrl().toString();
		
		
//		System.out.println("appName=" + appName);
//		System.out.println("appDetailUrl=" + appDetailUrl);
//		System.out.println("appDownloadUrl=" + appDownloadUrl);
//		System.out.println("osPlatform=" + osPlatform);
//		System.out.println("appVersion=" + appVersion);
//		System.out.println("appSize=" + appSize);
//		System.out.println("appUpdateDate=" + appUpdateDate);
//		System.out.println("appType=" + appType);
//		System.out.println("appVenderName=" + appVenderName);
//		System.out.println("appDownloadedTime=" + appDownloadedTime);
//		System.out.println("appDescription=" + appDescription);
//		System.out.println("appTag=" + appTag);
//		System.out.println("appScrenshot=" + appScrenshot);
//		System.out.println("appCategory=" + appCategory);

		if (appName != null && appDownloadUrl != null) {
			apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform,
					appVersion, appSize, appUpdateDate, appType, null);
			// Apk(String appName,String appMetaUrl,String appDownloadUrl,String
			// osPlatform ,
			// String appVersion,String appSize,String appTsChannel, String
			// appType,String cookie){
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);

		} else
			return null;

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
