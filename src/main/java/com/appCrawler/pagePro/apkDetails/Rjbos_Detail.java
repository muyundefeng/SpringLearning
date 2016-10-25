package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 博士软件
 * 网站主页：http://www.rjbos.com/wap.html
 * Aawap #626
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Rjbos_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Rjbos_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='List_title']/text()").toString();	
		
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//a[@class='go_dlsite']/@href").toString();
		appSize = page.getHtml().xpath("//div[@class='dl_info_box']/table/tbody/tr[2]/td[2]/table/tbody/tr[2]/td[1]/span/text()").toString();
		appDownloadedTime = page.getHtml().xpath("//div[@class='dl_info_box']/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td[1]/span/text()").toString();
		appDownloadedTime = appDownloadedTime.replace("次", "");
		appUpdateDate = page.getHtml().xpath("//div[@class='dl_info_box']/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td[3]/span/text()").toString();
		osPlatform = page.getHtml().xpath("//div[@class='dl_info_box']/table/tbody/tr[2]/td[2]/table/tbody/tr[4]/td[2]/span/text()").toString();
		appDescription = page.getHtml().xpath("//div[@id='mprodm_all']").toString();
		if(appDescription == null)
		{
			appDescription = page.getHtml().xpath("//div[@id='J-desc-1']").toString();
		}
		appDescription = usefulInfo(appDescription);
		//if(appD)
		appScrenshot = page.getHtml().xpath("//div[@id='imgcon']/img/@src").all();
		List<String> pics = new ArrayList<String>();
		for(String str:appScrenshot)
		{
			pics.add("http://www.rjbos.com"+str);
		}
		appScrenshot = pics;

		if(osPlatform!=null&&osPlatform.contains("Win"))
		{
			appName = null;
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
