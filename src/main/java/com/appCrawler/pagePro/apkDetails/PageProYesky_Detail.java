package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.StaticApplicationContext;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 天极网 http://mydown.yesky.com
 * PageProYesky #148
 * 该网站部分详情页apk下载链接的提取需要执行js代码方能获取到，这些下载链接里面带有时间和相应的MD5信息 2015年11月23日11:27:55 
 * 
 * @author DMT
 */
public class PageProYesky_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProYesky_Detail.class);

    public static Apk getApkDetail(Page page){
        String appName = null;				//app名字
        String appDetailUrl = null;			//具体页面url
        String appDownloadUrl = null;		//app下载地址
        String osPlatform = null ;			//运行平台
        String appVersion = null;			//app版本
        String appSize = null;				//app大小
        String appUpdateDate = null;		//更新日期
        String appType = null;				//下载的文件类型 apk？zip？rar？ipa?
        String appvender = null;			//app开发者  APK这个类中尚未添加
        String appDownloadedTime=null;		//app的下载次数
        String appDescription =null;        //app的详细介绍

        String platFormString =page.getHtml().xpath("//font[@itemprop='operatingSystem']/text()").toString();
        osPlatform = platFormString;
        if(osPlatform == null || ( !osPlatform.contains("Android") && !osPlatform.contains("android") ) )
        {
            LOGGER.info("return from PageProYesky.process()");
            return null;

        }
        appName=page.getHtml().xpath("//span[@itemprop='name']/text()").toString();

        appVersion=page.getHtml().xpath("//span[@itemprop='version']/text()").toString();

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
        String sizeString = page.getHtml().xpath("//div[@class='box_degest_left']/p[2]/font/text()").toString();
        appSize = sizeString;

        String updatedateString = page.getHtml().xpath("//div[@class='box_degest_left']/p[1]/font/text()").toString();
        appUpdateDate = updatedateString;

        String typeString = "apk";
        appType =typeString;

        appvender=null;

        String DownloadedTimeString = page.getHtml().xpath("//div[@class='box_degest_left']/p[9]/font/text() ").toString();

        appDownloadedTime =DownloadedTimeString;

        String descriptionString = page.getHtml().xpath("//div[@class='soft_text']").toString().replaceAll("\n", " ");
        String allinfoString = descriptionString;
        while(allinfoString.contains("<"))
            if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
            else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
            else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
        appDescription = allinfoString;

        List<String> appScreenshot = html.xpath("//div[@id='effect_img']//img/@src").all();
        String appTag = null;
        String appCategory = null;//html.xpath("//div[@class='cate_point']/a[4]/text()").get();
       // appCategory = html.xpath("//font[@class='co_blue']/a/text()").toString();

        //System.out.println("appCategory:" + appCategory);
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = DownloadedTimeString;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCommentUrl(appCommentUrl);
            apk.setAppComment(appComment);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
            String s = appDownloadUrl;
            s = s.substring(s.lastIndexOf(".") + 1 , s.length());
            if(s != null && s.length() < 5 && s.length() > 1){
            	apk.setAppType(s);
            }
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
    
 
    public static void main(String[] args){
    	
    		getUseJsDownloadUrl("what");
		
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
