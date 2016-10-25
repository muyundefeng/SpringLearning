package com.appCrawler.pagePro.apkDetails;
/**
 * 天极手机软件下载  http://mydown.yesky.com/
 * Yesky #163
 * @author tianlei
 */


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.UrlUtils;


public class Yesky_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yesky_Detail.class);
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

		
        appName = page.getHtml().xpath("//dl[@class='soft_name']/dd/h1/a/span[1]/text()").toString();
        appUpdateDate = page.getHtml().xpath("//div[@class='box_degest_left']/p/font/text()").toString();
        appSize = page.getHtml().xpath("//div[@class='box_degest_left']/p[2]/font/text()").toString();
        appCategory = page.getHtml().xpath("//div[@class='box_degest_left']/p[7]/font/a/text()").toString();
        appDownloadedTime = page.getHtml().xpath("//div[@class='box_degest_left']/p[9]/font/text()").toString().trim();
        appDescription = page.getHtml().xpath("//div[@class='soft_text']/p/text()").toString().trim();
        appScrenshot = page.getHtml().xpath("//div[@class='overview']/ul//a/img/@src").all();
        appDetailUrl = page.getUrl().toString();
        //下载链接的获取需要跳转页面
        String downloadurlString = page.getHtml().xpath("//div[@class='linkdown']/a").toString();
        appDownloadUrl =downloadurlString;
        if(downloadurlString != null && downloadurlString.contains("http") && downloadurlString.contains("shtml"))
            downloadurlString =  downloadurlString.substring(downloadurlString.indexOf("http"),downloadurlString.lastIndexOf("shtml")+5);
       // System.out.println(downloadurlString);
        String sourcefile=SinglePageDownloader.getHtml(downloadurlString);
 
        Html html = new Html(sourcefile);

        //System.out.println(html.toString());
        appDownloadUrl = html.xpath("//div[@class='dxxz']/a/@href").toString();
        if(appDownloadUrl == null) appDownloadUrl = html.xpath("//div[@class='gfxz']/a/@href").toString();
        //这个应用的下载地址需要执行js才能获取到正确的下载地址 http://mydown.yesky.com/sjsoft/170/11447170.shtml 
        if(appDownloadUrl == null) {
        	String urlInfo = html.xpath("//div[@class='wtxz']/a/@href").toString();
//        	System.out.println(urlInfo);
        	appDownloadUrl = getUseJsDownloadUrl(StringUtils.substringBetween(urlInfo, "('", "')"));
        }
       // appDownloadUrl = getNextUrl(appDownPage);
        
        
	    
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
    private static String getUseJsDownloadUrl(String urlInfo){   
    	if(urlInfo == null) return null;
    	ScriptEngineManager manager = new ScriptEngineManager();   
    	ScriptEngine engine = manager.getEngineByName("javascript");             	
    	try {
    		//获取要执行的js代码
			engine.eval(PageProYesky_Detail_js.getJavaScriptMD5Code());
			if(engine instanceof Invocable) {    
		    	Invocable invoke = (Invocable)engine;   
//		    	// 调用getHexTime方法，并传入参数    
//		    	String hexTimeString = (String)invoke.invokeFunction("getHexTime");
//		    	System.out.println("hexTimeString = " + hexTimeString);    // 调用getMD5方法，并传入参数    
//		    	String MD5 = (String)invoke.invokeFunction("getMD5", "yesky_download/sjsoft/201007/Android_For_PC.rar"+hexTimeString);    		    	
//		    	System.out.println("MD5 = " + MD5);  
		    	
		    	//调用getDownloadLink方法，并传入参数    
		    	String url = (String)invoke.invokeFunction("getDownloadLink", "/soft/201511/v143BJrjUVeQ-1.apk");    		    	
//		    	System.out.println("url = " + url);
		    	return url;
		    	}   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	return null;
    }


}
