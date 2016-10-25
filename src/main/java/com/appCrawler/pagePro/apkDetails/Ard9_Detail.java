package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * ard9  http://www.ard9.com/soft/
 * Ard9 #215
 * @author DMT
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Ard9_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Ard9_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='info_title']/h1/text()").toString();			
		appDetailUrl = page.getUrl().toString();
		appUpdateDate =page.getHtml().xpath("//div[@class='yingyong_info]/dl/dd[2]/span[2]/text()").toString();
		appCategory =page.getHtml().xpath("//div[@class='yingyong_info]/dl/dd[4]/text()").toString();
		if(appCategory.split("：").length>1) appCategory=appCategory.split("：")[1];
		if(appCategory.split(":").length>1) appCategory=appCategory.split(":")[1];
		appSize =page.getHtml().xpath("//div[@class='yingyong_info]/dl/dd[5]/text()").toString().trim();
		if(appSize.split("：").length>1) appSize=appSize.split("：")[1];
		if(appSize.split(":").length>1) appSize=appSize.split(":")[1];
		String img = page.getHtml().xpath("//div[@id='con_one_1']/table/tbody/tr/td/p[1]").toString();
		appScrenshot=getImage(img);
		appDescription = page.getHtml().xpath("//div[@id='con_one_1']/table/tbody/tr/td/p[2]/text()").toString();
		

		
		String info=page.getHtml().xpath("//div[@id='con_down_1']/ul/li[1]/a/@href").toString();
		
		String infohtml= SinglePageDownloader.getHtml(info,"get",null);
		
		appDownloadUrl = "http://www.ard9.com"+getUrl(infohtml);
		
		
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
//			Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//					String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
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
	private static String getUrl(String str){
    	String tmp=null;
		String regex="<a href=\"(/plus/down[^\"]+?)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp=matcher.group(1).toString();
        	
        }
    	return tmp;   	
    }
	private static List<String> getImage(String str){
    	List<String> tmp=new ArrayList<String>();
		String regex="<img src=\"([^\"]+?)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        String tmps;
        while(matcher.find()){
        	tmps="http://www.ard9.com"+matcher.group(1).toString();
        	tmp.add(tmps);
        	
        } 
    	return tmp;   	
    }
	

}
