package com.appCrawler.pagePro.apkDetails;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 229  百分网  http://android.byfen.com/
 * Aawap #229
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader2;


public class Byfen_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Byfen_Detail.class);
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
		String appCategory = null;			//app的应用类别 魔力宝贝   V4.5.0 
		String appName1 = page.getHtml().xpath("//div[@class='left-box']/dl/dd/h3/text()").toString();	
		System.out.println(appName);
		if(appName1!=null)
		{
			if(appName1.contains("V"))
			{
				appVersion=appName1.substring(appName1.indexOf('V')+1);
				appName=appName1.substring(0, appName1.indexOf('V')-1);
			}
			else
			{
				appVersion=null;
				appName=null;
			}
		}
		appDetailUrl = page.getUrl().toString();
		System.out.println(appDetailUrl+"************");
		appDownloadUrl=page.getHtml().xpath("//div[@class='down-list rBox']/a/@href").toString();
		System.out.println(appDownloadUrl);
		if(appDownloadUrl!=null)
		{
			appDownloadUrl=SinglePageDownloader.handleHttp302(appDownloadUrl, appDetailUrl);
		}
		osPlatform=page.getHtml().xpath("//div[@class='about-text']/div[2]/p[5]/span[2]/text()").toString();
		appSize=page.getHtml().xpath("//div[@class='about-text']/div[2]/p[1]/span[2]/text()").toString();
		String appUpdateDate1=page.getHtml().xpath("//div[@class='right-box']/p/text()").toString();
		if(appUpdateDate1!=null)
		{
			if(appUpdateDate1.contains("："))
			{
				String []temp=appUpdateDate1.split("：");
				appUpdateDate=temp[1];		
			}
			else
			{
				appUpdateDate=appUpdateDate1;
			}
		}
		//appType
		appVenderName=page.getHtml().xpath("//div[@class='about-text']/div[2]/p[6]/span[2]/a/text()").toString();
		String appDownloadedTime0=page.getHtml().xpath("//div[@class='count']/div/ul/li[1]/a/text()").toString();
		if(appDownloadedTime0!=null)
		{
			String appDownloadedTime1=usefulInfo(appDownloadedTime0);
			String appDownloadedTime2=appDownloadedTime1.substring(0, appDownloadedTime1.indexOf("人"));
			
				if(appDownloadedTime2.contains("万"))
				{
					String temp=appDownloadedTime1.substring(0, appDownloadedTime1.indexOf("万"));
					int number1=Integer.parseInt(temp);
					//if(appDownloadedTime1.substring( appDownloadedTime1.indexOf('万'))!=null)
					//int number2=Integer.parseInt(appDownloadedTime1.substring( appDownloadedTime1.indexOf('万')+1));
					appDownloadedTime=number1*10000+"";
				}
				else
				{
					appDownloadedTime=appDownloadedTime2;
				}
				}
		String appDescription1=page.getHtml().xpath("//div[@class='public-right']/div[2]/div[2]").toString();
		System.out.println(appDescription1);
		if(appDescription1!=null)
		{
			appDescription=usefulInfo(appDescription1);
		}
		appScrenshot=page.getHtml().xpath("//div[@class='img-box media-box']/ul//img/@src").all();
		String appTag1=page.getHtml().xpath("//div[@class='the-tags']").toString();
		//System.out.println(appTag1);
		if(appTag1!=null)
		{
			appTag=usefulInfo(appTag1).substring(4);
		}
		appCategory=page.getHtml().xpath("//div[@class='about-text']/div[2]/p[2]/span[2]/a/text()").toString();
		
		
		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
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
	private static String handleHttp302(String url,String appUrl)
	{
		String str="";
		try{
			StringBuffer buffer = new StringBuffer();
	        //String url = "http://www.zhuodown.com/plus/download.php?open=2&id=34750&uhash=d5ed808c5ded69811445d7bc";  
	       // System.out.println("访问地址:" + ""); 
	         
	        //发送get请求
	        URL serverUrl = new URL(url);  
	        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();  
	        conn.setRequestMethod("GET"); 
	        conn.addRequestProperty("Referer", appUrl);
	        //必须设置false，否则会自动redirect到重定向后的地址  
	        conn.setInstanceFollowRedirects(false); 
	        conn.connect();  
	         
	        //判定是否会进行302重定向
	        if (conn.getResponseCode() == 302) { 
	            //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
	            String location = conn.getHeaderField("Location");  
	            System.out.println("跳转地址:" + location); 
	            str=location;
	        }
		}
		catch(Exception e){}
		finally {
			
		}
		//System.out.println(str);
		return str;
	}
	
	
	

}
