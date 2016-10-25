package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 手游520  http://www.shouyou520.com/game
 * 包含两种相关的app详情页面信息
 * Aawap #117
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
//228  中国派   http://www.cn314.com/game/padgame/
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader3;

public class Cn314_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Cn314_Detail.class);
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
		
		
		appDetailUrl = page.getUrl().toString();
//		if(!appDetailUrl.contains("-"))
//		{
			appName = page.getHtml().xpath("//div[@class='romright']/table/tbody/tr[1]/td[2]/h1/text()").toString();
			//appDownloadUrl=page.getHtml().xpath("//div[@class='rom04_left']").toString();
			String pageInfo=page.getHtml().toString();
			if(pageInfo.contains("downloadPath[0]"))
			{
			int start=pageInfo.indexOf("downloadPath[0]");
			//System.out.println(start);
			int end=start;
			if(!appDetailUrl.equals("http://www.cn314.com/game/padgame/"))
			{
				while(true)
				{
					if((int)pageInfo.charAt(end)==59)
					{
						break;
					}
					end++;
				}
				appDownloadUrl=pageInfo.substring(start+17,end-1);
			}
			}
			//appDownloadUrl=page.getHtml().xpath("//div[@lass='rom04_left']/ul").toString();
			System.out.println(appDownloadUrl);
			char temp;
			if(appDownloadUrl!=null)
			{
				temp=appDownloadUrl.charAt(appDownloadUrl.length()-1);
				switch(temp)
				{
				case 'k':
					appType="apk";
					break;
				case 'p':
					appType="zip";
					break;
				case 'r':
					appType="rar";
					break;
				}
			}
			String appVersion1=page.getHtml().xpath("//div[@class='romright']/table/tbody/tr[4]/td[1]/text()").toString();
			System.out.println(appVersion1);
			appVenderName=page.getHtml().xpath("//div[@class='romright']/table/tbody/tr[2]/td[1]/text()").toString();
			if(appVersion1!=null)
			{
				appVersion1=usefulInfo(appVersion1);
				if(appVersion1.contains("："))
				{
					String []tempString=appVersion1.split("：");
					appVersion=tempString[1].substring(1);
				}
				else
				{
					appVersion=appVersion1.substring(1);
				}
			}
			String appSize1=page.getHtml().xpath("//table[@class='t01']/tbody/tr[2]/td[2]/text()").toString();
			System.out.println(appSize1);
			if(appSize1!=null)
			{
				if(appSize1.contains("M"))
				{	
					String []tempString1=appSize1.split("：");
					appSize=tempString1[1];
				}
			}
			String appUpdateDate1=page.getHtml().xpath("//table[@class='t01']/tbody/tr[4]/td[2]/text()").toString();
			if(appUpdateDate1!=null)
			{
				
					String []tempString2=appUpdateDate1.split("：");
					appUpdateDate=tempString2[1];
			}
			List <String> appScrenshot1=page.getHtml().xpath("//div[@class='pics']/div//img/@src").all();
			List<String> appScrenshot2 = new ArrayList<String>(appScrenshot1.size());
			//List <String> picList;
			for(int i=0;i<appScrenshot1.size();i++)
			{
				appScrenshot2.add("http://www.cn314.com"+appScrenshot1.get(i));
			}
			appScrenshot=appScrenshot2;
		//}
		if(appName==null){
			appName=page.getHtml().xpath("//div[@class='app_gamePage']/ul/li[2]/p[1]/text()").toString();
			String raw=page.getHtml().xpath("//div[@class='app_gamePage']/ul/li[2]/p[2]").toString();
			String raw1=usefulInfo(raw);
			appSize=raw1.split("：")[2];
			appCategory=raw1.split("：")[1];
			appCategory=appCategory.replace("软件大小", "");
			String raw2=page.getHtml().xpath("//div[@class='app_gamePage']/ul/li[2]/p[3]").toString();
			String raw3=usefulInfo(raw2);
			appVersion=raw3.split("：")[1];
			appVersion=appVersion.replace("软件语言", "");
			String raw4=page.getHtml().xpath("//div[@class='app_gamePage']/ul/li[2]/p[4]").toString();
			String raw5=usefulInfo(raw4);
			appVenderName=raw5.split("：")[1];
			appVenderName=appVenderName.replace("发布日期", "");
			appUpdateDate=raw5.split("：")[2];
			appDescription=page.getHtml().xpath("//p[@id='contentDesc']/text()").toString();
			appScrenshot=page.getHtml().xpath("//ul[@id='ISL_Cont_1']/li/img/@src").all();
			List<String> pics=new ArrayList<String>();
			for(int i=0;i<appScrenshot.size();i++)
			{
				pics.add("http://www.cn314.com"+appScrenshot.get(i));
			}
			appScrenshot=pics;
			//appDownloadUrl=page.getHtml().xpath("//ul[@id='downloadPath']/li[1]/a/@href").toString();
			String html=SinglePageDownloader3.getHtml(appDetailUrl,"gb2312");
			String str[]=html.split("精华帖汇总数组 ");
			String string=str[1].split(".apk")[0];
			appDownloadUrl=string.replace("  downloadPath[0]=\"", "")+".apk";
//			System.out.println(str[1]);
//			appDownloadUrl=str[1].split("\"; downloadPath[1]")[0];
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
