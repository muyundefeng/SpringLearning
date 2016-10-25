package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 酷快市场 http://www.aawap.net/
 * Aawap #117
 * @author DMT
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Dbtapk_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Dbtapk_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='name']/text()").toString();		
		appSize = page.getHtml().xpath("//div[@class='stars']/span[1]/text()").toString();			
		appDownloadedTime = page.getHtml().xpath("//span[@class='downNum']/script/@src").toString();
		appDownloadedTime=SinglePageDownloader.getHtml(appDownloadedTime, "get", null).replace("document.write('", "").replace("')", "");
		appDetailUrl = page.getUrl().toString();
		appDescription = page.getHtml().xpath("//div[@class='limit_h'][1]/text()").toString();
		appVersion = page.getHtml().xpath("//div[@class='swiper-slide details_slide']/p[1]/text()").toString();
	
		if(appVersion!=null && appName!=null){
		appVersion=appVersion.replace(appName, "");
		}
		appTag=page.getHtml().xpath("//p[@class='tags']/a/text()").toString();
		if(page.getHtml().xpath("//div[@class='wonderPic']").toString()!=null)
		appScrenshot = getImage(page.getHtml().xpath("//div[@class='wonderPic']").toString());
		appDownloadUrl = page.getHtml().xpath("//dl[@class='android_dl4']/a/@href").toString();
		
		
		
		

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
	
//	private static String usefulInfo(String allinfoString)
//	{
//		String info = null;
//		while(allinfoString.contains("<"))
//			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
//			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//		info = allinfoString.replace("\n", "").replace(" ", "");
//		return info;
//	}
	private static List<String> getImage(String str){
    	List<String> tmp=new ArrayList<String>();
		String regex="<img.*src=\"([^\"]+?)\".*/>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp.add(matcher.group(1).toString());
        	
        }
    	return tmp;   	
    }
	
	
	

}
