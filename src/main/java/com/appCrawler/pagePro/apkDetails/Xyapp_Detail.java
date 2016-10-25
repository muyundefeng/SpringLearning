package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * xy手游
 * 本渠道为手机客户端app,返回数据格式为json数据格式
 * 渠道编号:380
 * url地址为:
 * http://apk.interface.xyzs.com/apk/1.7.0/category/gameInfo?code=1001&page=2&page_size=20&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.utils.Json2Object;
import us.codecraft.webmagic.utils.Unicode2Chinese;


public class Xyapp_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Xyapp_Detail.class);
	public static Apk getApkDetail(String str){
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
		System.out.println(str);
		if(str.contains("f_and_sys_min"))
		{
			appName=Json2Object.ExtraInfo1(str, "f_appname").toString();
			appName=Unicode2Chinese.convertUnicode(appName);
			appName=appName.substring(1,appName.length()-1);
			appDescription=Json2Object.ExtraInfo1(str, "f_content").toString();
			if(appDescription.contains("appDescription"))
			{
				appDescription=Unicode2Chinese.convertUnicode(appDescription);
			}
			appDescription=appDescription.substring(1,appDescription.length()-1);
			appSize=Json2Object.ExtraInfo1(str, "f_size").toString();
			appSize=appSize.substring(1,appSize.length()-1);
			appCategory=Json2Object.ExtraInfo1(str, "fenlei").toString();
			appCategory=appCategory.substring(1,appCategory.length()-1);
			appDownloadedTime=Json2Object.ExtraInfo1(str, "f_downloadnum").toString();
			appDownloadedTime=appDownloadedTime.substring(1,appDownloadedTime.length()-1);
			if(appDownloadedTime.contains("万"))
			{
				appDownloadedTime=Float.parseFloat(appDownloadedTime.replace("万", ""))*10000+"";
			}
			appDownloadUrl=Json2Object.ExtraInfo1(str, "downloadurl").toString();
			appDownloadUrl=appDownloadUrl.substring(1,appDownloadUrl.length()-1);
			appVersion=Json2Object.ExtraInfo1(str, "f_webversion").toString();
			appVersion=appVersion.substring(1,appVersion.length()-1);
			osPlatform=Json2Object.ExtraInfo1(str, "f_and_sys_min").toString();
			osPlatform=osPlatform.substring(1,osPlatform.length()-1);
			String pic=Json2Object.ExtraInfo1(str, "f_img").toString();
			pic=pic.substring(2,pic.length()-2);
			String temp[]=pic.split(",");
			List<String> strList=new ArrayList<String>();
			for(int i=0;i<temp.length;i++)
			{
				strList.add(temp[i]);
			}
			appScrenshot=strList;
		}
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
}
