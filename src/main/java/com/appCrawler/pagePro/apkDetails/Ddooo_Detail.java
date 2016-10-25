package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * ddooo  http://www.ddooo.com/
 * Aawap #117
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Ddooo_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Ddooo_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='list_top71']/h1/text()").toString();	
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='dow_tj4']/a/@href").toString();
		//String htmlString=page.getHtml().toString().replace("<strong>", "").replace("</strong>", "");
		
		String osPlatform2=page.getHtml().xpath("//div[@class='down_tj21l']/div[5]").toString();
		//System.out.println(osPlatform2);
		if(osPlatform2!=null)
		{
			String osPlatform1=usefulInfo(osPlatform2);
			if(osPlatform1!=null)
			{
				if(!osPlatform1.contains("Android"))
				{
					appDownloadUrl=null;
				}
				else
				{
					if(osPlatform1.contains("："))
					{
					String []osPlatform3=osPlatform1.split("：");
					osPlatform=osPlatform3[1];
					}
					if(osPlatform1.contains(":"))
					{
						String []osPlatform3=osPlatform1.split(":");
						osPlatform=osPlatform3[1];	
					}
				}
			}
		}
		String appSize1=page.getHtml().xpath("//div[@class='down_tj21l']/div[1]").toString();
		//System.out.println(usefulInfo(appSize1));
		if(appSize1!=null)
		{
			String []appSize3=usefulInfo(appSize1).split("：");
			appSize=appSize3[1];
		}
		String tempTime1="";
		String appUpdateDate1=page.getHtml().xpath("//div[@class='down_tj21l']/div[6]/text()").toString();
		if(appUpdateDate1!=null)
		{
			String appUpdateDate2=appUpdateDate1.substring(2);
			String []appUpdateDate3=appUpdateDate2.split(" ");
			String []YearMonthDay=appUpdateDate3[0].split("-");
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
			
			
			
		//}
		//String []appUpdateDate3=usefulInfo(appUpdateDate1).split("：");
		//appUpdateDate=appUpdateDate3[1];
		String appCategory1=page.getHtml().xpath("//div[@class='down_tj21l']/div[3]/text()").toString();
		if(appCategory1!=null)
		{
			String []appCategory2=usefulInfo(appCategory1).split("：");
			appCategory=appCategory2[1];
		}
		appDescription=usefulInfo(page.getHtml().xpath("//div[@class='tj3lm']").toString());
		List<String> appScrenshot1=page.getHtml().xpath("//div[@class='tj3lm']//img/@src").all();
		List<String> appScrenshot2 = new ArrayList<String>(appScrenshot1.size());
		for(int i=0;i<appScrenshot1.size();i++)
		{
			//System.out.println(appScrenshot1.get(i))
			if(appScrenshot1.get(i).contains("http"))
			{
				appScrenshot2.add(appScrenshot1.get(i));
			}
			else
			{
			appScrenshot2.add("http://www.ddooo.com"+appScrenshot1.get(i));
			}
		}
		appScrenshot=appScrenshot2;
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
