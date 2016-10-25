package com.appCrawler.pagePro.apkDetails;
/**
 * 悦应用:http://www.yueapp.com/index.php/homepage/getsoftorgame/1
 * 渠道编号:326
 * 网页app列表采用JQURY数据返回
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Yueapp_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yueapp_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='fl inf']/h3/text()").toString();	
		
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='fl inf']/div/a/@href").toString();
		appSize=page.getHtml().xpath("//div[@class='fl inf1']/p[1]/text()").toString();
		if(appSize!=null)
		{
			appSize=appSize.split("：")[1]+"B";
		}
		appVersion=page.getHtml().xpath("//div[@class='fl inf1']/p[1]/span/text()").toString();
		if(appVersion!=null)
		{
			appVersion=appVersion.split("：")[1];
		}
		appCategory=page.getHtml().xpath("//div[@class='fl inf1']/p[2]/text()").toString();
		if(appCategory!=null)
		{
			appCategory=appCategory.split("：")[1];
		}
		//appCategory=page.getHtml().xpath("//div[@class='fl inf1']/p[2]/text()").toString();
		
		appVenderName=page.getHtml().xpath("//div[@class='fl inf1']/p[3]/text()").toString();
		appVenderName=arr(appVenderName);
		appUpdateDate=page.getHtml().xpath("//div[@class='fl inf1']/p[4]/text()").toString();
		appUpdateDate=arr(appUpdateDate);
		appUpdateDate=appUpdateDate.replace("年","-").replace("月","-").replace("日","-");
		appUpdateDate=appUpdateDate.substring(0,appUpdateDate.length()-1);
		appDescription=page.getHtml().xpath("//div[@class='fontpart']/p/text()").toString();
		appScrenshot=page.getHtml().xpath("//div[@class='piclist']/span/img/@src").all();

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
	private static String arr(String str)
	{
		if(str==null)
		{
			return null;
		}
		else{
			return str.split("：")[1];
		}
	}
	
	
	
	

}
