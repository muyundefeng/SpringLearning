package com.appCrawler.pagePro.apkDetails;
/**
 * 凯斯应用
 * 渠道编号:314
 * 网站主页:http://mrpyx.cn/download/list.aspx?classid=1988
 * 主页进行替换
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Mrpyx_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mrpyx_Detail.class);
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
		String rawStr = page.getHtml().xpath("//div[@class='content']").toString();//对获取的信息进行处理,筛选出名称等基本信息
		//String str1=usefulInfo(rawStr);
		appDetailUrl=page.getUrl().toString();
		rawStr=rawStr.replace("<div class=\"content\">", "");
		rawStr=rawStr.replace("</div>", "");
		//System.out.println(rawStr);
		String temp[]=rawStr.split("<br />");
		appName=temp[0].substring(8);
		appUpdateDate=temp[1].substring(6);
		appVersion=temp[4].substring(6);
		appCategory=page.getHtml().xpath("//div[@class='content']/a[1]/text()").toString();
		appTag=page.getHtml().xpath("//div[@class='content']/a[2]/text()").toString();
		appDownloadUrl=page.getHtml().xpath("//div[@class='bt1']/a/@href").toString();
		appDownloadedTime=page.getHtml().xpath("//div[@class='bt1']/a/text()").toString();
		appDownloadedTime=(appDownloadedTime.replace("点击下载(", "")).replace("次)", "");
		appDescription=page.getHtml().xpath("//div[@class='mains']/div[7]/text()").toString();
		appScrenshot=page.getHtml().xpath("//ul[@class='slider slider1']/li/a/@href").all();
		appSize=page.getHtml().xpath("//div[@class='mains']/div[9]/text()").toString();
		//System.out.println(appSize);
		int start=appSize.indexOf("/");
		int end=appSize.indexOf("MB");
		appSize=appSize.substring(start+1,end)+"MB";
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
}
