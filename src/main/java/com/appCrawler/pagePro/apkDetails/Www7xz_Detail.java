package com.appCrawler.pagePro.apkDetails;

/**
 * 7匣子 http://www.7xz.com
 * Www7xz #135
 * 
 * 3种详情页
 * (1)http://www.7xz.com/swsy/
 * (2)http://www.7xz.com/gtqst/
 * (3)http://www.7xz.com/mhxy/
 * @author DMT
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.bcel.generic.RETURN;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

public class Www7xz_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Www7xz_Detail.class);

	public static Apk getApkDetail(Page page) {
		Apk apk = null;
		String appName = null; // app名字
		String appDetailUrl = null; // 具体页面url
		String appDownloadUrl = null; // app下载地址
		String osPlatform = null; // 运行平台
		String appVersion = null; // app版本
		String appSize = null; // app大小
		String appUpdateDate = null; // 更新日期
		String appType = null; // 下载的文件类型 apk？zip？rar？
		String appVenderName = null; // app开发者 APK这个类中尚未添加
		String appDownloadedTime = null; // app的下载次数
		String appDescription = null; // app的详细描述
		List<String> appScrenshot = null; // app的屏幕截图
		String appTag = null; // app的应用标签
		String appCategory = null; // app的应用类别
		

		 appName=page.getHtml().xpath("//div[@class='col-md-9 clearfix']/h1/text()").toString();		
		 if(appName == null) //(2)
			 appName = page.getHtml().xpath("//li[@class='home']/a/text()").toString();
		 if(appName != null)
		 appName = appName.replace("官网", "");
		 String htmlString = page.getHtml().toString();
		 if(appName == null && htmlString.contains("新手礼包"))  //(3)没找到提取的标签
			 appName = StringUtils.substringAfter(StringUtils.substringBefore(htmlString, "新手礼包"), ">");
		 if(appName == null)  
			return null;
			
		appDetailUrl = page.getUrl().toString();
		
		appDownloadUrl = page.getHtml().xpath("//p[@class='sprit down_android']/a/@href").toString();
		if(appDownloadUrl == null) //(2)
			appDownloadUrl = page.getHtml().xpath("//a[@class='btn green']/@href").toString();
		if(appDownloadUrl == null ) //(3)
			appDownloadUrl = page.getHtml().xpath("//a[@class='btn-block']/@href").toString();
		
		appTag = page.getHtml().xpath("//p[@class='tag1']//a/text()").all().toString();
		
		
		String allinfoString=page.getHtml().xpath("//table").toString();
		while(allinfoString != null && allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
				

		if(allinfoString !=null){
		if(allinfoString.contains("游戏平台") && allinfoString.contains("发行商")){
		String platFormString =allinfoString.substring(allinfoString.indexOf("游戏平台")+5,allinfoString.indexOf("发行商")-1);
			osPlatform = platFormString.replace("\n", "").replace(" ", "");
		}
		
		if(allinfoString.contains("游戏版本")){
		String versionString = allinfoString.substring(allinfoString.indexOf("游戏版本")+5,allinfoString.length());
			appVersion = versionString.replace("\n", "").replace(" ", "");
		}
		
		if(allinfoString.contains("游戏大小") && allinfoString.contains("游戏版本")){
		String sizeString = allinfoString.substring(allinfoString.indexOf("游戏大小")+5,allinfoString.indexOf("游戏版本")-1);
			appSize = sizeString.replace("\n", "").replace(" ", "");
		}
		
		appType = "apk";
		
		if(allinfoString.contains("发行商") && allinfoString.contains("玩家")){
		String venderString = allinfoString.substring(allinfoString.indexOf("发行商")+4,allinfoString.indexOf("玩家")-1);
		appVenderName=venderString.replace("\n", "").replace(" ", "");
		}
		}
		String descriptionString = page.getHtml().xpath("//p[@class='p_detail p_l_r10']/text()").toString();
			appDescription = descriptionString;	
		appCategory = page.getHtml().xpath("//div[@class='row positon']/div[1]/a[2]/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='carousel-inner']//img/@original").all();
			
		System.out.println("appName=" + appName);
		System.out.println("appDetailUrl=" + appDetailUrl);
		System.out.println("appDownloadUrl=" + appDownloadUrl);
		System.out.println("osPlatform=" + osPlatform);
		System.out.println("appVersion=" + appVersion);
		System.out.println("appSize=" + appSize);
		System.out.println("appUpdateDate=" + appUpdateDate);
		System.out.println("appType=" + appType);
		System.out.println("appVenderName=" + appVenderName);
		System.out.println("appDownloadedTime=" + appDownloadedTime);
		System.out.println("appDescription=" + appDescription);
		System.out.println("appTag=" + appTag);
		System.out.println("appScrenshot=" + appScrenshot);
		System.out.println("appCategory=" + appCategory);

		if (appName != null && appDownloadUrl != null) {
			apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform,
					appVersion, appSize, appUpdateDate, appType, null);
			// Apk(String appName,String appMetaUrl,String appDownloadUrl,String
			// osPlatform ,
			// String appVersion,String appSize,String appTsChannel, String
			// appType,String cookie){
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);

		} else
			return null;

		return apk;
	}
	
	private  static String  getUrlInfo(String urlString) {
		String sourcefile="";
		String lines;		
		try {
			
			URL url=new URL(urlString);		
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}
			// System.out.println(sourcefile);
			 return sourcefile;
		} catch (Exception e) {
			return null;
		}
	}
	

	
}
