package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 新浪微游戏 http://game.weibo.cn/
 * Gameweibo #305
 * @author tianlei
 */
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Gameweibo_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Gameweibo_Detail.class);
	public static List<Apk> getApkDetail(Page page){
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
		String	detailPage = null;
		List<Apk> apkList = new ArrayList<Apk>();	
		detailPage = page.getHtml().toString();
		detailPage=convert(detailPage).replace("&quot;", "").replace(",kind:1", "").replace(",kind:0","");
		List<String> tmp =new ArrayList<String>();
		String regex ="name:([^,]+),category:([^,]+),count:&lt;([^,]+),description:(.*?),size:([^,]+),downpath:([^,]+),detailurl:([^}]+)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(detailPage);
        while(matcher.find()){     
        	appName = matcher.group(1).toString();
        	appCategory = matcher.group(2).toString();
        	appDescription = matcher.group(4).toString();
        	appSize = matcher.group(5).toString()+"MB";
        	appDownloadUrl = "http://game.weibo.cn"+matcher.group(6).toString().replace("&amp;", "&").replace("\\", "");
        	appDetailUrl = "http://game.weibo.cn"+matcher.group(7).toString().replace("&amp;", "&").replace("\\", "");
        	
//    		System.out.println("appName="+appName);
//    		System.out.println("appDetailUrl="+appDetailUrl);
//    		System.out.println("appDownloadUrl="+appDownloadUrl);
//    		System.out.println("osPlatform="+osPlatform);
//    		System.out.println("appVersion="+appVersion);
//    		System.out.println("appSize="+appSize);
//    		System.out.println("appUpdateDate="+appUpdateDate);
//    		System.out.println("appType="+appType);
//    		System.out.println("appVenderName="+appVenderName);
//    		System.out.println("appDownloadedTime="+appDownloadedTime);
//    		System.out.println("appDescription="+appDescription);
//    		System.out.println("appTag="+appTag);
//    		System.out.println("appScrenshot="+appScrenshot);
//    		System.out.println("appCategory="+appCategory);
        	
        	apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
        	apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);		
			apkList.add(apk);
        }
		return apkList;
	}		

	public static String convert(String utfString){  
	    StringBuilder str = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	      
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        str.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            str.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }       
	    return str.toString();  
	}

}
