package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 激光下载站
 * 网站主页：http://www.xz7.com/
 * @id 491
 * @author lisheng
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader3;
import us.codecraft.webmagic.selector.Html;


public class Xz7_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Xz7_Detail.class);
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
		
		
		if(page.getHtml().toString().contains("<script>window.location.href="))
		{
			String html=page.getHtml().toString();
			String url=html.toString().split("window.location.href='")[1].split("';</script>")[0];
			String html1=SinglePageDownloader3.getHtml(url,"gb2312");
			Html html2=Html.create(html1);
			appDetailUrl = page.getUrl().toString();
			appName = html2.xpath("//div[@id='c_soft_down']/h1/text()").toString();	
			appSize=html2.xpath("//ul[@class='g-soft-down-introduce']/li[1]/strong[1]/text()").toString();
			//System.out.println("appSize="+appSize);
			if(appSize!=null)
			{
				appSize=appSize.split("：")[1];
			}
			appUpdateDate=html2.xpath("//ul[@class='g-soft-down-introduce']/li[2]/strong[1]/text()").toString();
			if(appUpdateDate!=null)
			{
				appUpdateDate=appUpdateDate.split("：")[1].replace("/", "-");
				
			}
			appCategory=html2.xpath("//ul[@class='g-soft-down-introduce']/li[2]/strong[2]/text()").toString();
			if(appCategory!=null)
			{
				appCategory=appCategory.split("：")[1];
			}
			String urlInfo=html2.toString();
			//System.out.println(urlInfo);
			appDownloadUrl=urlInfo.split(",TypeID")[0].split("Address:")[1];
			appDownloadUrl="http://hy.xz7.com"+appDownloadUrl.substring(1, appDownloadUrl.length()-1);
			appDescription=html2.xpath("//div[@id='content-all']").toString();
			appDescription=usefulInfo(appDescription);
			appScrenshot=html2.links("//div[@id='screen_show']").all();
			List<String> pics=new ArrayList<>();
			for(String str:appScrenshot)
			{
				if(!str.startsWith("htpp://"))
				{
					pics.add("http://www.xz7.com"+str);
				}
				else{
					pics.add(str);
				}
			}
			appScrenshot=pics;
			osPlatform=html2.xpath("//ul[@class='g-soft-down-introduce']/li[5]/text()").toString();
			if(osPlatform!=null&&osPlatform.contains("Win"))
			{
				appName=null;
			}
			
			List<String> categoryList=html2.xpath("//ul[@class='catlst']/li/a/@href").all();
	 		System.out.println(categoryList);
			List<String> apps=html2.links("//div[@id='list_content']").all();
	 		List<String> pages=html2.links("//div[@class='pg_pcl']").all();
	 		apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		for(String str:apps)
	 		{
	 			if(!str.startsWith("http://"))
	 			{
	 				page.addTargetRequest("http://www.xz7.com"+str);
	 			}
	 		}
	 	}
		else{
			appDetailUrl = page.getUrl().toString();
			appName=page.getHtml().xpath("//div[@id='c_soft_down']/h1/text()").toString();
			appSize=page.getHtml().xpath("//ul[@class='g-soft-down-introduce']/li[1]/strong[1]/text()").toString();
			
			try
				{if(appSize!=null)
				{
					appSize=appSize.split("：")[1];
				}
				appUpdateDate=page.getHtml().xpath("//ul[@class='g-soft-down-introduce']/li[2]/strong[1]/text()").toString();
				if(appUpdateDate!=null)
				{
					appUpdateDate=appUpdateDate.split("：")[1].replace("/", "-");
					
				}
				appCategory=page.getHtml().xpath("//ul[@class='g-soft-down-introduce']/li[2]/strong[2]/text()").toString();
				if(appCategory!=null)
				{
					appCategory=appCategory.split("：")[1];
				}
				String urlInfo=page.getHtml().toString();
				//System.out.println(urlInfo);
				appDownloadUrl=urlInfo.split(",TypeID")[0].split("Address:")[1];
				appDownloadUrl="http://hy.xz7.com"+appDownloadUrl.substring(1, appDownloadUrl.length()-1);
				appDescription=page.getHtml().xpath("//div[@id='content-all']").toString();
				appDescription=usefulInfo(appDescription);
				appScrenshot=page.getHtml().links("//div[@id='screen_show']").all();
				List<String> pics=new ArrayList<>();
				for(String str:appScrenshot)
				{
					if(!str.startsWith("htpp://"))
					{
						pics.add("http://www.xz7.com"+str);
					}
					else{
						pics.add(str);
					}
				}
				appScrenshot=pics;
				osPlatform=page.getHtml().xpath("//ul[@class='g-soft-down-introduce']/li[5]/text()").toString();
				if(osPlatform!=null&&osPlatform.contains("Win"))
				{
					appName=null;
				}
				}
			catch(Exception e)
			{}
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
