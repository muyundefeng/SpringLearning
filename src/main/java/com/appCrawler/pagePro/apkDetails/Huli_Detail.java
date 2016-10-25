package com.appCrawler.pagePro.apkDetails;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * 狐狸助手
 * 网站主页：http://app.huli.cn/android/
 * Aawap #542
 * @author lisheng
 */
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.utils.Unicode2Chinese;


public class Huli_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Huli_Detail.class);
	public static Apk getApkDetail(String downloadUrl,String count,Map map){
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
		
		appName=map.get("appname").toString();
		appName=Unicode2Chinese.convertUnicode(appName);
		appDownloadUrl=downloadUrl;
		appDownloadedTime=count;
		appCategory=map.get("catname").toString();
		appCategory=Unicode2Chinese.convertUnicode(appCategory);
		appSize=map.get("appsize").toString();
		appSize=(int)(Integer.parseInt(appSize)/Math.pow(1024, 2))+"MB";
		appVersion=map.get("appversion").toString();
		appVenderName=map.get("company").toString();
		appVenderName=Unicode2Chinese.convertUnicode(appVenderName);
		appUpdateDate=map.get("updatetime").toString();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		appUpdateDate = sdf.format(new Date(Long.parseLong(appUpdateDate)*1000));
		//System.out.println(sd);
		//System.out.println(str);
		appDescription=map.get("appnewfeature").toString();
		appDescription=Unicode2Chinese.convertUnicode(appDescription);
		List<Map<String, Object>> picList=(List<Map<String, Object>>)map.get("imglist");
		List<String> pics=new ArrayList<String>();
		for(Map map1:picList)
		{
			pics.add(map1.get("url").toString());
		}
		appScrenshot=pics;
		
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
	
	
	
	

}
