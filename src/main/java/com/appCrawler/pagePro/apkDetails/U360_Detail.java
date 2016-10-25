package com.appCrawler.pagePro.apkDetails;
/**
 * 360游戏大厅
 * 网站主页：http://ku.u.360.cn/
 * Aawap #514
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class U360_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(U360_Detail.class);
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
		appDetailUrl = page.getUrl().toString();
		if(appDetailUrl.startsWith("http://ku.u.360.cn/detail"))
		{
			appName = page.getHtml().xpath("//div[@class='ng_d_info']/h2/text()").toString();	
			
			appDetailUrl = page.getUrl().toString();
			String raw=page.getHtml().xpath("//p[@class='g_d_t_nums']/text()").toString();
			//System.out.println(usefulInfo(raw)+"+++");
			try{
				String temp[]=raw.split("大小");
				System.out.println(temp.length);
				appCategory=temp[0].replace("游戏类型：", "");
				
				appSize=temp[1].split("下载次数")[0].replace("：", "");
				appDownloadedTime=temp[1].split("下载次数")[1].replace("次", "").replace("：", "");
				appDownloadedTime=appDownloadedTime.split("更新时间")[0].replaceAll(" ", "");
				appDownloadedTime=appDownloadedTime.contains("万")?appDownloadedTime.replace("万", "")+"0000":appDownloadedTime.contains("亿")?
						appDownloadedTime.replace("亿", "")+"00000000":appDownloadedTime;
				appDownloadedTime=appDownloadedTime.toString();
				appDownloadedTime=appDownloadedTime.replaceAll("   ", "");
				appUpdateDate=raw.split("更新时间：")[1];
			}
			catch(Exception e){}
			//appDownloadedTime=appDownloadedTime.replace("   ", "");
			
			appScrenshot=page.getHtml().xpath("//ul[@class='ng_lb_b_gl']/li/a/img/@src").all();
			appDescription=page.getHtml().xpath("//div[@class='ng_lb_b_d_c_div']").toString();
			appDescription=appDescription==null?null:usefulInfo(appDescription);
			appDownloadUrl = page.getHtml().xpath("//div[@class='ng_d_i_right']/a/@href").toString();
		}
		else {
			appName=page.getHtml().xpath("//div[@class='contag']/span[@class='gamename']/text()").toString();
			appSize=page.getHtml().xpath("//div[@class='contag']/span[2]/text()").toString();
			appCategory=page.getHtml().xpath("//div[@class='contag']/span[3]/em/text()").toString();
			appDownloadUrl=page.getHtml().xpath("//a[@class='android']/@href").toString();
			appScrenshot=page.getHtml().xpath("//ul[@class='Plists Mainvideo Mainvideo2']/li/img/@src").all();
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
