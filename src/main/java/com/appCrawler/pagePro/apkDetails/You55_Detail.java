package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
/**
 * #370
 * 55游·http://www.55you.com/tafang/
 * 搜索接口无效
 * @author Administrator
 *
 */

public class You55_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(You55_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='jieshao']/h1/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appCategory=page.getHtml().xpath("//div[@class='s_count_info']/ul/li/a/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='s_count_info']/ul/li[3]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='s_count_info']/ul/li[4]/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='s_count_info']/ul/li[5]/text()").toString();
		String toUrl=page.getHtml().xpath("//a[@class='downbtn']/@href").toString();
		//System.out.println(toUrl);
		String rawStr=SinglePageDownloader.getHtml(toUrl);
		appDownloadUrl=ExtraInfo(rawStr);
		appDescription=usefulInfo(page.getHtml().xpath("//div[@class='content']").toString());
		String rawStr1=page.getHtml().xpath("//div[@id='picPanel']/div/text()").toString();
		String temp[]=rawStr1.split(",");
		List<String> picList=new ArrayList<String>();
		for(int i=0;i<temp.length;i++)
		{
			picList.add(temp[i]);
		}
		appScrenshot=picList;
		
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
	private static String ExtraInfo(String str)
	{
		String string=null;
		Pattern p = Pattern.compile("<a href=[^>]*>");
		Matcher m = p.matcher(str);
		List<String> result=new ArrayList<String>();
		while(m.find()){
			result.add(m.group());
		}
		for(String s1:result){
			//System.out.println(s1);
			if(s1.contains("downid")&&!s1.contains("iswp"))
			{
				//System.out.println(s1);
				int index=s1.indexOf("href=");
				int index1=s1.indexOf("title");
				string=s1.substring(index+6,index1-2);
				break;
			}
		}
	return string;
	}
}

