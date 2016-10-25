package com.appCrawler.pagePro.apkDetails;
/**
 * Jz5u绿色下载 http://www.jz5u.com/
 * Jz5u #273
 * @author tianlei
 */

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Jz5u_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Jz5u_Detail.class);
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
		String appDownPage=null;
		

 		appName=page.getHtml().xpath("//div[@class='title_all']/h1/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='co5l']/ul/li[1]/text()").toString().replace("软件大小：","");
		appUpdateDate=page.getHtml().xpath("//div[@class='co5l']/ul/li[7]/text()").toString().replace("更新时间：", "");
		appTag=page.getHtml().xpath("//div[@class='co5l']/ul/li[3]/a/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='co5l']/ul/li[9]/text()").toString().replace("运行环境：","");
	    appDetailUrl = page.getUrl().toString();
	    appScrenshot=page.getHtml().xpath("//div[@id='c_des_content']/img/@src").all();
	    for(int i=0;i<appScrenshot.size();i++){
	    	appScrenshot.set(i,"http://www.jz5u.com"+appScrenshot.get(i));
	    	
	    }
		appDescription=usefulInfo(page.getHtml().xpath("//div[@id='c_des_content']").toString());
		appDownloadUrl=page.getHtml().xpath("//div[@class='co_content7']/a/@href").toString();		
		appDownPage=SinglePageDownloader.getHtml(appDownloadUrl);	
        appDownloadUrl=getDownUrl(appDownPage);		
	    

					
		
		
		

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
	
	private static String getDownUrl(String str){
    	String tmp=null;
		String regex="<a href=\"(http://fzyd[^\"]+)\" target=\"_blank\">";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp=matcher.group(1).toString();
        	
        }
    	return tmp;   	
    }
	
	
	

}
