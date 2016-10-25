package com.appCrawler.pagePro.apkDetails;


import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
/**
 * #198 下载吧
 * Xiazaiba http://a.xiazaiba.com/
 * @author DMT
 *
 */

public class Xiazaiba_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Xiazaiba_Detail.class);
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
		
		appName = getName(page.getHtml().xpath("//div[@class='gc-2 fl']/h1/text()").toString());
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//ul[@class='gc-2-btn mt10']/li/a/@href").toString();
		appVersion = getVersion(page.getHtml().xpath("//div[@class='gc-2 fl']/h1/text()").toString());
			appVersion = appVersion.replace("V", "").replace("v", "");
		appCategory = page.getHtml().xpath("//p[@class='ext page-loca']/a[3]/text()").toString();
		
		appUpdateDate = getDetailInfo(page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[1]/text()").toString());
		appSize = getDetailInfo(page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[2]/text()").toString());
		appDownloadedTime = getDetailInfo(page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[3]/text()").toString());
		osPlatform = getDetailInfo(page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[4]/text()").toString());
		appCategory = getDetailInfo(page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[5]/text()").toString());				
		appTag = page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[6]/a/text()").toString();
		appVenderName = page.getHtml().xpath("//ul[@class='gc-2-con clearfix mt10']/li[7]/a/text()").toString();
		
		appDescription = usefulInfo(page.getHtml().xpath("//div[@class='con']").toString());
		appScrenshot = page.getHtml().xpath("//div[@class='app-pics']//img/@src").all();
		
		if(appDownloadedTime !=null && appDownloadedTime.contains("次"))
			appDownloadedTime = appDownloadedTime.substring(0,appDownloadedTime.length()-1);
		
		
		
//		System.out.println("appName="+appName);
//		System.out.println("appDetailUrl="+appDetailUrl);
//		System.out.println("appDownloadUrl="+appDownloadUrl);
//		System.out.println("osPlatform="+osPlatform);
//		System.out.println("appVersion="+appVersion);
//		System.out.println("appSize="+appSize);
//		System.out.println("appUpdateDate="+appUpdateDate);
//		System.out.println("appType="+appType);
//		System.out.println("appVenderName="+appVenderName);
//		System.out.println("appDownloadedTime="+appDownloadedTime);
//		System.out.println("appDescription="+appDescription);
//		System.out.println("appTag="+appTag);
//		System.out.println("appScrenshot="+appScrenshot);
//		System.out.println("appCategory="+appCategory);

		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//					String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
						
		}
		
		return apk;
	}
	
	
	public static String getName(String nameString){
		if(nameString != null && !nameString.contains("("))
			return nameString;
		if(nameString == null || !nameString.contains("("))
			return null;
		return nameString.substring(0,nameString.indexOf("("));
	}
	
	public static String getVersion(String versionString){
		if(versionString == null || !versionString.contains("V"))
			return null;
		return versionString.substring(versionString.indexOf("V"),versionString.length());
	}
	
	private static String getDetailInfo(String detail) {
		
		if(detail!=null && !detail.endsWith("："))
			return detail.split("：")[1];
		else return null;
		
	}
	
	private static String usefulInfo(String allinfoString) {
		String info = null;
		if(allinfoString == null) return null;
		int length =allinfoString.length()+1;
		while (allinfoString.contains("<") && allinfoString.length()<length){
			length = allinfoString.length();
			if (allinfoString.indexOf("<") == 0)
				allinfoString = allinfoString.substring(
						allinfoString.indexOf(">") + 1, allinfoString.length());
			else if (allinfoString.contains("<!--"))
				allinfoString = allinfoString.substring(0,
						allinfoString.indexOf("<!--"))
						+ allinfoString.substring(
								allinfoString.indexOf("-->") + 3,
								allinfoString.length());
			else
				allinfoString = allinfoString.substring(0,
						allinfoString.indexOf("<"))
						+ allinfoString.substring(
								allinfoString.indexOf(">") + 1,
								allinfoString.length());
		}
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	
	
	

	
	
	
	

}
