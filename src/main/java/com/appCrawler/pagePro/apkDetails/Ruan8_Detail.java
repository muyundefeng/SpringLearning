package com.appCrawler.pagePro.apkDetails;

/*
 * //http://www.ruan8.com/soft_16422.html
 * 2015年10月26日17:09:51 
 *http://www.ruan8.com/soft_16422.html
 * 2015年11月3日18:36:15 网站改版
 *@author DMT
*/
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;

public class Ruan8_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Fpwap_Detail.class);
	public static Apk getApkDetail(Page page){
		
	
			Apk apk = null;
			String appName = null;				//app名字
			String appDetailUrl = null;			//具体页面url
			String appDownloadUrl = null;		//app下载地址
			String osPlatform = null ;			//运行平台
			String appVersion = null;			//app版本
			String appSize = null;				//app大小
			String appUpdateDate = null;		//更新日期
			String appType = null;				//下载的文件类型 apk？zip？rar？ipa?
			String appVenderName = null;		//app开发者  APK这个类中尚未添加
			String appDownloadedTime=null;		//app的下载次数
			String appDescription = null;		//app的详细描述
			List<String> appScrenshot = null;	//app的屏幕截图
			String appTag = null;				//app的应用标签
			String appCategory = null;			//app的应用类别 
			
			String nameString=page.getHtml().xpath("//P[@class='floatl mgl25']/A[4]/text()").toString();	
			if(nameString != null && nameString.contains("V"))
			{
				appName=nameString.substring(0,nameString.lastIndexOf("V")-1);
				appVersion = nameString.substring(nameString.lastIndexOf("V")+1,nameString.length());
			}
			else if(nameString != null && nameString.contains("v"))
			{
				appName=nameString.substring(0,nameString.lastIndexOf("v")-1);
				appVersion = nameString.substring(nameString.lastIndexOf("v")+1,nameString.length());
			}
			else if(nameString != null && nameString.contains("."))
			{
				appName=nameString.substring(0,nameString.indexOf(".")-1);
				appVersion = nameString.substring(nameString.indexOf(".")-1,nameString.length());
			}
			else 
			{
				appName = nameString;
				appVersion = null;
			}
			
			osPlatform = page.getHtml().xpath("//p[@id='os']/text()").toString();
			if(osPlatform !=null && !osPlatform.contains("Android")) return null;	
			
			String type_size_update_time_String = page.getHtml().xpath("//ul[@class='mdccs']").toString();
			
			appType = StringUtils.substringBetween(type_size_update_time_String, "文件格式：","</li>");			
			appSize = StringUtils.substringBetween(type_size_update_time_String, "软件大小：","</li>");
			appUpdateDate = StringUtils.substringBetween(type_size_update_time_String, "更新时间：","</li>");
			appDownloadedTime = StringUtils.substringBetween(type_size_update_time_String, "下载次数：","</li>");
			
			appDetailUrl = page.getUrl().toString();		
			
			appDownloadUrl = page.getHtml().xpath("//div[@class='mddpiclist floatl dbb']/a/@href").toString();
			
			appDescription = usefulInfo(page.getHtml().xpath("//div[@class='w760 mgl10 floatr']/div[4]/div[2]").toString());
				
			appScrenshot= page.getHtml().xpath("//div[@class='mdcimg']//img/@src").all();	
			
			appCategory = page.getHtml().xpath("//p[@class='floatl mgl25']/a[3]/text()").toString();
			
			System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			System.out.println("appvender="+appVenderName);
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
			
			return apk;
		
	}
	
	private static String usefulInfo(String allinfoString)
	{
	if(allinfoString == null) return null;
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	

}
