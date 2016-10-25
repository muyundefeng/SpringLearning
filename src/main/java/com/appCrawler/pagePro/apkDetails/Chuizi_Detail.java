package com.appCrawler.pagePro.apkDetails;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
/**
 * #197 锤子
 * Chuizi http://www.chuizi.com/
 * @author DMT
 *
 */

public class Chuizi_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Chuizi_Detail.class);
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
		List<Apk> listapp=new ArrayList<Apk>();
		String str[]=page.getHtml().toString().split("<div class=\"apps\">");
		for(int i=1;i<str.length;i++)
		{
			if(i==4)
			{
				continue;
			}
			else
			{
				//System.out.println(str.length);
				//System.out.println(str[i]);
				String temp=str[i];
				String str1[]=temp.split("<h2>");
				int end=0;
				for(int j=0;;j++)
				{
					if(str1[1].charAt(j)=='<')
					{
						end=j;
						break;
					}
				}
				appName=str1[1].substring(0,end);
				System.out.println(appName);
				String str2[]=str1[1].split("<div class=\"desc-summary\">");
				//System.out.println(str2[1]);
				for(int j=11;;j++)
				{
					if(str2[1].charAt(j)=='<')
					{
						end=j;
						break;
					}
				}
				appDescription=str2[1].substring(12,end);
				System.out.println(appDescription+"****");
				String temp1=str2[1];
				String str3[]=temp1.split("href=");
				appDownloadUrl=str3[1].split("title")[0];
				appDownloadUrl=appDownloadUrl.replaceAll("\"","");
				System.out.println(appDownloadUrl);
				String str4[]=temp.split("<ul id=\"screenshot-list\">");
				String str5[]=str4[1].split("</ul>");
				String temp2=str5[0].replaceAll("<li><img ng-src=",";").replaceAll("/></li>",";").replaceAll("<li class=\"last\"><img ng-src=",";").replaceAll("\"","");
				String str6[]=temp2.split(";");
				List<String> list=new ArrayList<String>();
				for(int j=1;j<str6.length-1;j++)
				{
					if(str6[j]!=null)
					{
						list.add(str6[j]);
					}
				}
				appScrenshot=list;
				if(appName != null && appDownloadUrl != null){
					apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
					//apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
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
					listapp.add(apk);
								
				}
				//System.out.println(list);
			}
		}
		return listapp;
	}
}
