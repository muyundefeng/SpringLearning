package com.appCrawler.pagePro.apkDetails;

/**
 * 下载银行 http://www.downbank.cn
 * Downbank #132
 * (1)下载链接有防盗链设置
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

public class Downbank_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Downbank_Detail.class);

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
		
		osPlatform = page.getHtml().xpath("//div[@id='soft_name']/ul/li[4]/span/text()").toString();
		if( osPlatform == null ||  ( !osPlatform.contains("android") && !osPlatform.contains("Android") )){
			
			return null;
		}
			appName=page.getHtml().xpath("//div[@id='soft_name']/h1/label/b/text()").toString();
	//		System.out.println(appName);
			String appVersion1="";
			if(appName!=null)
			{
				if(appName.contains("V"))
				{
					//System.out.println("hELLO");
					appVersion1=appName.substring(appName.indexOf('V')+1);
					int length=appVersion1.length();
					//System.out.println(length);
					int a=0;
					for(int i=0;i<length;i++)
					{
						if(((int)appVersion1.charAt(i)>=48&&(int)appVersion1.charAt(i)<=57)||(int)appVersion1.charAt(i)==46)
						{
							//System.out.println(appVersion1.charAt(i));
							a++;
						}
					}
					//System.out.println(appVersion1);
					//System.out.println(a);
					appVersion=appVersion1.substring(0,a);
					//System.out.println(appVersion);
				}
				if(appName.contains("v"))
				{
					//System.out.println("hELLO");
					appVersion1=appName.substring(appName.indexOf('v')+1);
					int length=appVersion1.length();
					//System.out.println(length);
					int a=0;
					for(int i=0;i<length;i++)
					{
						if(((int)appVersion1.charAt(i)>=48&&(int)appVersion1.charAt(i)<=57)||(int)appVersion1.charAt(i)==46)
						{
							//System.out.println(appVersion1.charAt(i));
							a++;
						}
					}
					//System.out.println(appVersion1);
					//System.out.println(a);
					appVersion=appVersion1.substring(0,a);
					//System.out.println(appVersion);
				}
			}
			
			
		appDetailUrl = page.getUrl().toString();
		if(page.getHtml().xpath("//div[@id='soft_down']/ul/a[2]/@href").toString().contains(".exe"))
			appDownloadUrl = page.getHtml().xpath("//div[@id='soft_down']/ul/a[3]/@href").toString();
		else 
			appDownloadUrl = page.getHtml().xpath("//div[@id='soft_down']/ul/a[2]/@href").toString();
		appSize = page.getHtml().xpath("//div[@id='soft_name']/ul/li[1]/span/text()").toString();
		
		appUpdateDate = page.getHtml().xpath("//div[@id='soft_name']/ul/li[8]/span/text()").toString();
		
	
		
		String descriptionString = page.getHtml().xpath("//div[@id='soft_intro']").toString();
		String allinfoString = descriptionString;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		
		appDescription = allinfoString.replace("\n", "");
		appCategory = page.getHtml().xpath("//div[@id='login']/p[2]/a[4]/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@id='soft_intro']//img/@src").all();
		for (String temp : appScrenshot) {
			appScrenshot.remove(temp);
			appScrenshot.add("http://www.downbank.cn"+temp);
		}
		
//		
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
