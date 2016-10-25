package com.appCrawler.pagePro.apkDetails;

import java.util.ArrayList;
/**
 * 卓易市场  http://www.zhuoyi.com/
 * Zhuoyi #311
 * @author tianlei
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

public class Zhuoyi_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Zhuoyi_Detail.class);
	public static List<Apk> getApkDetail(Page page){
		List<Apk> apks = new ArrayList<Apk>();		

		String info = SinglePageDownloader.getHtml(page.getUrl().toString());
		Html html = Html.create(info);
		List<String> appInfo = html.xpath("//div[@class='appListDiv']").all();
		for (String str : appInfo){
			apks.add(getAppInfo(str));
		}
		return apks;
	}		
	
	private static Apk getAppInfo(String str){
		Apk apk =  null;
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
		List<String> appScrenshot = new ArrayList<String>();			//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
		String picture = null;
	
		String regex= "<span class=\"apkName\" style=\"display:inline; font-size:16px;\">([^<]+)</span>";
        Matcher matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appName = matcher.group(1).toString();   	
        }
        
        regex="软件大小：([^M]+)MB";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appSize = matcher.group(1).toString()+"MB";   	
        }     
        
        regex="版本：([^\\)]+)";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appVersion = matcher.group(1).toString();   	
        }
        
        regex="<div class=\"appDetails\">([^<]+)";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appDescription = matcher.group(1).toString().trim();   	
        }
        
        regex="更新时间：([^&]+)";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appUpdateDate = matcher.group(1).toString();   	
        }
        
        regex="downloadApp([^\\,]+)'";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appDownloadUrl = matcher.group(1).toString().replace("('", "");   	
        }
        
        regex="<a href=\"([^\"]+)\"><img src=\"([^\"]+)\"";
        matcher = Pattern.compile(regex).matcher(str); 
        if(matcher.find()){    	
        	appScrenshot.add(matcher.group(2).toString());
        	appDetailUrl = "http://www.zhuoyi.com"+matcher.group(1).toString();
        }

        if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);					
		}
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
        
        
    	return apk;   	
    }
}
