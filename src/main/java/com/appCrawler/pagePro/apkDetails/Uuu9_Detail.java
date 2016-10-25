package com.appCrawler.pagePro.apkDetails;
/**
 * 游久网
 * 网站主页：http://www.yoyojie.com/
 * Aawap #225
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Uuu9_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Uuu9_Detail.class);
	public static Apk getApkDetail(Page page){
		//System.out.println("无法访问");
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
		if(page.getUrl().regex("http://sjdb\\.uuu9\\.com/[a-zA-Z]+/").match())
		{
			appName = page.getHtml().xpath("//div[@class='gameinfo r']/h1/text()").toString();
			appDetailUrl = page.getUrl().toString();
			//System.out.println(appDetailUrl);	
			//System.out.println(appName);
	//		if(appName==null)
	//		{
	//			System.out.println("Yes");
	//		}
			appDownloadUrl=page.getHtml().xpath("//div[@class='xiazai']/a[1]/@href").toString();
			System.out.println(appDownloadUrl);
			
	//		osPlatform=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[6]/span/text()").toString();
	//		System.out.println(osPlatform);
	//		if(osPlatform!=null)
	//		{
	//			if(!osPlatform.contains("Android"))
	//			{
	//				appDownloadUrl=null;
	//				System.out.println("Yes");
	//			}
	//			
	//		}
			System.out.println(appDownloadUrl);
			if(appDownloadUrl.equals(page.getUrl().toString()))
			{
				appDownloadUrl=null;
			}
			appSize=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[7]/span/text()").toString();
			appVersion=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[2]/span/text()").toString();
			appVenderName=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[3]/span/text()").toString();
			String appUpdateDate1=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[8]/span/text()").toString();
			String tempTime1="";
			//System.out.println("无法访问11");
			if(appUpdateDate1!=null)
			{
				if(appUpdateDate1.contains(":"))
				{
					String []tempTime=appUpdateDate1.split(" ");
					String []YearMonthDay=tempTime[0].split("-");
					for(int i=0;i<YearMonthDay.length;i++)
					{
						if(YearMonthDay[i].length()<2)
						{
							YearMonthDay[i]="0"+YearMonthDay[i];
						}
					}
					tempTime1=YearMonthDay[0];
					for(int i=1;i<YearMonthDay.length;i++)
					{
						tempTime1=tempTime1+"-"+YearMonthDay[i];
					}
					appUpdateDate=tempTime1;
				}
				else
				{
					if(appUpdateDate1.length()!=10)
					{
						String []YearMonthDay=appUpdateDate1.split("-");
						for(int i=0;i<YearMonthDay.length;i++)
						{
							if(YearMonthDay[i].length()<2)
							{
								YearMonthDay[i]="0"+YearMonthDay[i];
							}
						}
						tempTime1=YearMonthDay[0];
						for(int i=1;i<YearMonthDay.length;i++)
						{
							tempTime1=tempTime1+"-"+YearMonthDay[i];
						}
						appUpdateDate=tempTime1;
					}
					appUpdateDate=appUpdateDate1;
				}
					
			}
			appCategory=page.getHtml().xpath("//div[@class='gameinfo r']/ul/li[5]/span/text()").toString();
			appDescription=page.getHtml().xpath("//div[@class='game_js m-10 b']").toString();
			if(appDescription!=null)
			{
				appDescription=usefulInfo(appDescription);
			}
			appScrenshot=page.getHtml().xpath("//div[@id='gallerylist']//img/@src").all();
			List<String> appScrenshot1=page.getHtml().$("div.gallerylist","@src").all();
			//System.out.println(appScrenshot1+"****///");
			if(appName != null && appDownloadUrl != null){
				//System.out.println("无法访问11fasffaf");
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
		}
		if(page.getUrl().regex("http://game\\.yoyojie\\.com/game/.*").match())
		{
			appName=page.getHtml().xpath("//div[@class='game-info-detail']/h2/text()").toString();
			appDetailUrl=page.getUrl().toString();
			appCategory=page.getHtml().xpath("//div[@class='game-info-detail']/div[@class='game-info-lables']/span/text()").toString();
			appSize=page.getHtml().xpath("//div[@class='game-info-detail']/div[2]/span/text()").toString();
			appVersion=page.getHtml().xpath("//div[@class='game-info-detail']/div[3]/span[1]/text()").toString();
			appUpdateDate=page.getHtml().xpath("//div[@class='game-info-detail']/div[3]/span[2]/text()").toString();
			//appDownloadUrl=page.getHtml().xpath("//div[@class='module-download']/a[1]/@href").toString();
			List<String> downUrls=page.getHtml().xpath("//div[@class='module-download']/a/@href").all();
			for(String str:downUrls)
			{
				if(str.contains("itunes"))
				{
					
				}
				else{
					appDownloadUrl=str;
				}
			}
			appScrenshot=page.getHtml().xpath("//ul[@class='clearfloat']/li/img/@src").all();
			appDescription=page.getHtml().xpath("//div[@class='module-introduction']/text()").toString();
			appDescription=usefulInfo(appDescription);
			if(appName != null && appDownloadUrl != null){
				//System.out.println("无法访问11fasffaf");
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
			
		}
			
		return apk;
	}
	
	private static String usefulInfo(String allinfoString)
	{
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "");
		return info;
	}
	
	
	
	

}
