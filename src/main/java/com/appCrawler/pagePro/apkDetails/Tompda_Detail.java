package com.appCrawler.pagePro.apkDetails;
/**
 * Tompda http://android.tompda.com/
 * Aawap #236
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Tompda_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Tompda_Detail.class);
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
		if((appDetailUrl.contains("?keyword=")||appDetailUrl.contains("DownLoad"))&&!appDetailUrl.contains(".htm"))
		{
			return null;
		}
		else
		{
			System.out.println(appDetailUrl);
		appName = page.getHtml().xpath("//div[@class='mainBody1_left']/h1/text()").toString();	
		
		String appDownloadUrl1 = page.getHtml().xpath("//div[@class='mainBody1_right']/a/@href").toString();
		if(appDownloadUrl1!=null)
		{
			if(!appDownloadUrl1.contains("http"))
			{
				appDownloadUrl="http://android.tompda.com"+appDownloadUrl1;
			}
			else
			{
				appDownloadUrl=appDownloadUrl1;
			}
		}
		String rawString1=page.getHtml().xpath("//div[@class='mainBody1_left_2_p']/p").toString();
		
		if(rawString1!=null)
		{
		String rawString2=usefulInfo(rawString1);
		System.out.println(rawString2);
		if(rawString2!=null)
		{
			int start=rawString2.indexOf('型');
			int end=rawString2.indexOf('&');
			appCategory=rawString2.substring(start+2, end);
			int start1=rawString2.indexOf('本');
			int end1=rawString2.indexOf('费');
			appVersion=rawString2.substring(start1+2,end1-1);
			if(("未知").equals(appVersion))
			{
				appVersion=null;
			}
			if(rawString2.contains("MB"))
			{
				int start2=rawString2.indexOf('小');
				int end2=rawString2.indexOf('B');
				appSize=rawString2.substring(start2+2,end2+1);
			}
			else
			{
				appSize=null;
			}
		}}
		appDescription=page.getHtml().xpath("//div[@class='mainBody3']/p/text()").toString();
		appScrenshot=page.getHtml().xpath("//div[@class='mainBody5']").all();
		//System.out.println(appScrenshot);
		appScrenshot=page.getHtml().xpath("//div[@class='mainBody5']/img/@src2").all();
		//String tempString=page.getHtml().xpath("//div[@class='mainBody']").toString();
//		if(tempString!=null)
//		{
//			if(tempString.contains("mainBody5"))
//			{
//			List <String> appScrenshot1=page.getHtml().xpath("//div[@class='mainBody5']/img/@src").all();
//			boolean flag=false;
//			if(appScrenshot1!=null)
//			{
//				for(int i=0;i<appScrenshot1.size();i++)
//				{
//					if(appScrenshot1.get(i)==null)
//					{
//						flag=flag&false;
//					}
//					else
//					{
//						flag=flag|true;
//					}
//				}
//				if(flag==true)
//				{
//					appScrenshot=appScrenshot1;
//				}
//				else
//				{
//					appScrenshot=null;
//				}
//			}
//			else
//			{
//				appScrenshot=null;
//				}
//			}
//			else
//			{
//				appScrenshot=null;
//			}
//		}
//		

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
	}}
	
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
