package com.appCrawler.pagePro.apkDetails;
/**
 * 18touch超好玩游戏中心
 * 网站主页：http://game.18touch.com/game/1-0-0-0-0-1
 * Aawap #395
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Touch18_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Touch18_Detail.class);
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
		appDetailUrl=page.getUrl().toString();
		appName=page.getHtml().xpath("//div[@class='gameData']/h1/a/text()").toString();
		appDownloadedTime=page.getHtml().xpath("//div[@class='gameData']/div/ul/li[3]/span/text()").toString();
		appDownloadedTime=appDownloadedTime.contains("+")?appDownloadedTime.replace("+", ""):appDownloadedTime;
		appCategory=page.getHtml().xpath("//div[@class='gameData']/div/ul/li[4]/a/text()").toString();
		appVenderName=page.getHtml().xpath("//div[@class='gameData']/div/ul/li[5]/a/text()").toString();
		appVersion=page.getHtml().xpath("//div[@class='mm-and']/div/span[1]/span/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='mm-and']/div/span[2]/span/text()").toString();
		//appDownloadUrl=page.getHtml().xpath("//div[@class='mm-and']/div[2]/div/ul/li/div[@class='mm-cover']/img//span[2]/span/text()").toString();
		//获取下载地址
		String raw=page.getHtml().toString();
		String temp=raw.split("// 各种游戏下载地址立即扫描下载")[1];
		String temp1=temp.split("var dc_check=true")[0];
		String temp2=temp1.split("\"2_1\"  :")[1];
		appDownloadUrl=temp2.split("\"2_2\"  :")[0];
		if(appDownloadUrl.contains(".apk"))
		{
			String temp3[]=appDownloadUrl.split("apk");
			appDownloadUrl=temp3[0].substring(2)+"apk";
		}
		else{
			appDownloadUrl=null;
		}
		appDescription=page.getHtml().xpath("//div[@class='zy abbre']/p").toString();
		appDescription=appDescription==null?null:usefulInfo(appDescription);
		appScrenshot=page.getHtml().xpath("//div[@class='scrollbar']/div/ul/li/img/@src").all();
		

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
