package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 一起晃手游网  http://www.17huang.com/yx/
 * 渠道编号:366
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Huang17_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Huang17_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='right']/b/text()").toString();	
		appDetailUrl=page.getUrl().toString();
		appVersion=page.getHtml().xpath("//div[@class='right']/ul/li[2]/text()").toString();
		try{
			appVersion=ExtraInfo(appVersion);
		}
		catch(Exception e)
		{
			appVersion=null;
		}
		appCategory=page.getHtml().xpath("//div[@class='right']/ul/li[4]/text()").toString();
		appCategory=ExtraInfo(appCategory);
		appSize=page.getHtml().xpath("//div[@class='right']/ul/li[5]/text()").toString();
		appSize=ExtraInfo(appSize);
		appUpdateDate=page.getHtml().xpath("//div[@class='right']/ul/li[8]/text()").toString();
		appUpdateDate=ExtraInfo(appUpdateDate);
		appDescription=page.getHtml().xpath("//div[@class='hide1 tab_box_show']").toString();
		appDescription=usefulInfo(appDescription);
		String rawStr=page.getHtml().xpath("//div[@class='pic_list_middle']/div[2]/ul/").toString();
		//System.out.println(rawStr);
		String raw0=rawStr.split("var title")[0];
		raw0=raw0.replace("var imgArray=\"","");
		raw0=raw0.replace("[","");
		raw0=raw0.replace("]\";","");
		raw0=raw0.replace("<script type=\"text/javascript\">", "");
		//System.out.println(raw0);
		String rawStr1=raw0.substring(0,raw0.length()-3);
		//System.out.println(rawStr1);
		String picArray[]=rawStr1.split(",");
		List<String> screenList=new ArrayList<String>();
		for(int i=0;i<picArray.length;i++)
		{
			//if(picArray[i])
			screenList.add(picArray[i].replace("&quot;",""));
		}
		//appScrenshot=screenList;
		//System.out.println(appScrenshot.size());
		//System.out.println(rawStr1);
		String rawDownload=page.getHtml().xpath("//li[@class='andr_down']/script").toString();
		//System.out.println(rawDownload);
		String raw=rawDownload.split(";")[1];
		//System.out.println(raw);
		int startIndex1=raw.indexOf("href");
		//System.out.println(startIndex1);
		int endIndex1=raw.indexOf("target");
		//System.out.println(endIndex1);
		//System.out.println(raw.subSequence(startIndex1, endIndex1));
		appDownloadUrl="http://www.17huang.com"+raw.subSequence(startIndex1+6, endIndex1-2);


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
	
	private static String ExtraInfo(String str)
	{
		return str==null?null:str.split("：")[1];
	}
	
	
	

}
