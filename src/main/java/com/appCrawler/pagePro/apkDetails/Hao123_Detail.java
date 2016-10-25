package com.appCrawler.pagePro.apkDetails;
/**
 * 好123
 * 网站主页：http://sy.hao123.com/youxiku?pid=1
 * Aawap #516
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Hao123_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Hao123_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='tit']/h2/a/text()").toString();	
		appCategory=page.getHtml().xpath("//div[@class='other']/dl[1]/dd/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='other']/dl[3]/dd/text()").toString();
		appVersion=page.getHtml().xpath("//div[@class='other']/dl[5]/dd/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='other']/dl[6]/dd/text()").toString();
		appVenderName=page.getHtml().xpath("//div[@class='other']/dl[8]/dd/a/text()").toString();
		
		appDownloadUrl = page.getHtml().xpath("//div[@class='download']/a[@class='andr']/@href").toString();
		String osPlatform1=page.getHtml().xpath("//div[@class='cont']/p[3]/span/text()").toString();
		//osPlatform=page.getHtml().xpath("//span[@class='span-w']/text()").toString();
	//	System.out.println(page.getHtml().xpath("//div[@class='cont']/p[3]/").toString());
//		String temp5=usefulInfo(page.getHtml().xpath("//div[@class='cont']/p[3]/").toString());
		if(osPlatform1!=null)
		{
			String []temp6=osPlatform1.split("：");
			osPlatform=temp6[1];
		}
		String appSize1=page.getHtml().xpath("//div[@class='cont']/p[1]/span/text()").toString();
		if(appSize1!=null)
		{
			String []temp1=appSize1.split("：");
			appSize=temp1[1];
		}
		String appUpdateDate1=page.getHtml().xpath("//div[@class='cont']/p[2]/text()").toString();
		if(appUpdateDate1!=null)
		{
			String []temp=appUpdateDate1.split("：");
			appUpdateDate=temp[1];
		}
		String appCategory1=page.getHtml().xpath("//div[@class='cont']/p[2]/span/text()").toString();
		if(appCategory1!=null)
		{
			String []temp3=appCategory1.split("：");
			appCategory=temp3[1];
		}
		appDownloadedTime=page.getHtml().xpath("//div[@class='cont']/p[1]/span[2]/text()").toString();
		appDescription=page.getHtml().xpath("//div[@class='tab_list tab-list']/").toString();
		if(appDescription!=null)
		{
			appDescription=usefulInfo(appDescription);
		}
		//System.out.println(page.getHtml().xpath("//div[@class='pictxt item have']/text()").toString());
		appScrenshot=page.getHtml().xpath("//div[@class='screenimg]//a/@href").all();

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
