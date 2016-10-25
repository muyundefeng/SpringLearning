package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 东坡下载
 * 网站主页：http://www.uzzf.com/
 * Aawap #495
 * @author lisheng
 */
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader3;


public class Uzzf_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Uzzf_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='m-softinfo clearfix']/h1/text()").toString();	
		appCategory=page.getHtml().xpath("//ul[@class='parametric']/li[1]/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appSize=page.getHtml().xpath("//ul[@class='parametric']/li[3]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//ul[@class='parametric']/li[5]/text()").toString();
		appDescription=page.getHtml().xpath("//div[@id='content']").toString();
		appDescription=usefulInfo(appDescription);
		appScrenshot=page.getHtml().xpath("//ul[@class='shot-list']/li/div/img/@src").all();
		List<String> pics=new ArrayList<String>();
		for(String str:appScrenshot)
		{
			pics.add("http://www.uzzf.com"+str);
		}
		appScrenshot=pics;
		try{
			String html = page.getRawText();
			//System.out.println(page.getRawText());
			String address = (html.split("Address:")[1]).split(",TypeID")[0];
			//appDownloadUrl="http://xz2016.uzzf.com/"+url.replace("\"", "");
			String typeId = html.split("TypeID")[1].split("SoftLinkID")[0];
			typeId = typeId.replace("\",", "").replace(":\"", "");
			Map<String, String> map = new HashMap<String,String>();
			map.put("Referer", appDetailUrl);
			String addressList = SinglePageDownloader.getHtml("http://www.uzzf.com/inc/SoftLinkType.js","GET",map);
			String json = addressList.replace("var AddressList =", "");
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map1 = objectMapper.readValue(json, Map.class);
			String address1 =map1.get("siteId_"+typeId).toString();
			//System.out.println(address1);
			appDownloadUrl = address1.split("\\|\\|")[1].split(",")[1]+address;
			appDownloadUrl = appDownloadUrl.replace("\"", "");
		}
		catch(Exception e){e.printStackTrace();}
		

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
