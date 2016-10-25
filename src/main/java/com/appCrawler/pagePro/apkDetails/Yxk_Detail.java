package com.appCrawler.pagePro.apkDetails;
/**
 * 游戏库
 * 网站主页：http://sj.xiaopi.com/yxk.html
 * @id 407
 * @author lisheng
 */

import java.net.URLDecoder;
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Yxk_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yxk_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='jjzq_m1_info1_r']/div[@class='tit']/text()").toString();	
		//appName=handle(appName);
		
		appCategory=page.getHtml().xpath("//ul[@class='xzy1_r_ul']/p[1]/text()").toString();	
		//appCategory=handle(appCategory);
		appUpdateDate=page.getHtml().xpath("//ul[@class='xzy1_r_ul']/p[4]/text()").toString();	
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='download-wrap-item js-show']/a[@class='a1 xpazb']/@href").toString();
		appDescription=page.getHtml().xpath("//div[@class='jjzq_m1_info3']").toString();
		//appDescription=handle(appDescription);
		if(appDescription!=null)
		{
			appDescription=usefulInfo(appDescription);
		}
		appScrenshot=page.getHtml().xpath("//div[@class='smallpic']/ul/li/div/img/@bigsrc").all();
		if(appDownloadUrl==null)
		{
			appName=page.getHtml().xpath("//div[@class='show_l1']/div[@class='tit']/text()").toString();
			appDetailUrl=page.getUrl().toString();
			appDownloadUrl=page.getHtml().xpath("//div[@class='show_l2']/ul/li[@class='az tiaz']/a/@href").toString();
			appCategory=page.getHtml().xpath("//div[@class='show_l3']/p[2]/span[1]/text()").toString();
			appDescription=page.getHtml().xpath("//div[@id='j_app_desc']/p/text()").toString();
			appScrenshot=page.getHtml().xpath("//div[@class='scrollbar']/div[1]/ul/li/img/@src").all();
		}
		if(appDownloadUrl==null)
		{
			appDownloadUrl=page.getHtml().xpath("//div[@class='zqmain1_r1']/a[@class='xpazb']/@href").toString();
			appDetailUrl=page.getUrl().toString();
			appName=page.getHtml().xpath("//div[@class='zqm3_r1']/b[1]/text()").toString();
			appCategory=page.getHtml().xpath("//div[@class='zqm3_r1']/b[2]/text()").toString();
			appDescription=page.getHtml().xpath("//div[@class='zqm3_r1']/p[2]/text()").toString();
			appScrenshot=page.getHtml().xpath("//ul[@class='zqfocus-bar']/li/a/img/@src").all();
		}
		if(appDownloadUrl==null)
		{
			appName=page.getHtml().xpath("//div[@class='info']/p[1]/text()").toString();
			if(appName!=null)
			{
				appName=appName.split("：")[1];
			}
			appName=page.getHtml().xpath("//div[@class='info']/p[1]/text()").toString();
			if(appName!=null)
			{
				appName=appName.split("：")[1];
			}
			appCategory=page.getHtml().xpath("//div[@class='info']/p[2]/text()").toString();
			if(appCategory!=null)
			{
				appCategory=appCategory.split("：")[1];
			}
			appDownloadUrl=page.getHtml().xpath("//div[@class='down']/a[@class='az xpazb']/@href").toString();
			appScrenshot=page.getHtml().xpath("//ul[@class='zqfocus-bar']/li/a/img/@src").all();
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
	
	public static String handle(String str)
	{
		String ss="";
		System.out.println(str);
//		try{
//			String str1=URLDecoder.decode(str, "utf-8");
//			System.out.println(str1);
//			byte[] b2=str1.getBytes("gbk");
//			ss=new String(str1);
//		}
//		catch(Exception e)
//		{
//			
//		}
		return ss;
	}
	
	

}
