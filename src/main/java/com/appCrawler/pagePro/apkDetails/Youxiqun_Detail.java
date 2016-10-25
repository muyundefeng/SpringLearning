package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 游戏群 http://www.youxiqun.com/
 * 渠道编号:383
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Youxiqun_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Youxiqun_Detail.class);
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
		appName=page.getHtml().xpath("//div[@class='dGameTitle']/i/text()").toString();
		appVersion=page.getHtml().xpath("//div[@class='dGameInforR fr']/ul/li[1]/text()").toString();
		appCategory=page.getHtml().xpath("//div[@class='dGameInforR fr']/ul/li[2]/span/@id").toString();
		appCategory=getCategory(appCategory);
		appSize=page.getHtml().xpath("//div[@class='dGameInforR fr']/ul/li[3]/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='dGameInforR fr']/ul/li[4]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='dGameInforR fr']/ul/li[8]/text()").toString();
		appDownloadUrl=page.getHtml().xpath("//div[@class='downloadbox']/a/@href").toString();
		appDescription=page.getHtml().xpath("//div[@class='pText']").toString();
		appDescription=usefulInfo(appDescription);
		appScrenshot=page.getHtml().xpath("//ul[@class='gallery piclist']/img/@src").all();
		List<String> newList=new ArrayList<String>();
		for(String str:appScrenshot)
		{
			newList.add("http://www.youxiqun.com"+str);
		}
		appScrenshot=newList;
		appDetailUrl=page.getUrl().toString();
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
	
	private static String getCategory(String str)
	{
		String x=null;
		if(str!=null)
		{
			int d=(int)str.charAt(0)-107;
			switch (d)
			  {
			  case 1:
				x="角色";
				break;
			  case 2:
				x="策略";
				break;
			  case 3:
				x="卡牌";
				break;
			  case 4:
				x="休闲";
				break;
			  case 5:
				x="动作";
				break;
			  case 6:
				x="其他";
				break;
			  }

		}
		return x;
	}
}
