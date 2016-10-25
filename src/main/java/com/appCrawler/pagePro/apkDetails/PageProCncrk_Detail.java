package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 起点下载[中国] app搜索抓取
 * url:http://zhannei.baidu.com/cse/search?q=QQ&entry=1&s=5746876527562368484&nsid=
 *
 * @version 1.0.0
 */
public class PageProCncrk_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCncrk_Detail.class);

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
		appDetailUrl=page.getUrl().toString();
		appName = page.getHtml().xpath("//h1[@class='azh1']/text()").toString();	
		String appCategory1=page.getHtml().xpath("//dl[@class='dl-info2']/dd[@class='dtj']/text()").toString();
		//System.out.println(appCategory);
		String temp[]=appCategory1.split(" ");
		System.out.println(temp.length);
		for(int i=0;i<temp.length;i++)
		{
			System.out.println(temp[i]);
		}
		//int j=0;
		appCategory=temp[1];
		//osPlatform=temp[2];
		appUpdateDate=temp[5];
		if(appCategory1!=null&&!appCategory1.contains("Android"))
		{
			appName=null;
		}
		appDownloadUrl=page.getHtml().xpath("//a[@class='a-pc1']/@href").toString();
		appDescription=page.getHtml().xpath("//div[@class='ul-fl-1 jsTAB_CON']").toString();
		appDescription=appDescription!=null?usefulInfo(appDescription):null;
		appScrenshot=page.getHtml().xpath("//div[@class='jspContainer']/div/ul/li/img/@src").all();
		List<String> pics=new ArrayList<String>();
		for(String str:appScrenshot)
		{
			pics.add("http://www.cncrk.com"+str);
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
		if(allinfoString == null) return null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
}
