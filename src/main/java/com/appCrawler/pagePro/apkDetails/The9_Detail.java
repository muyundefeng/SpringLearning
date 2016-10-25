package com.appCrawler.pagePro.apkDetails;
/**
 * 九号下载
 * 网站主页:http://www.9ht.com/
 * 渠道编号:378
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class The9_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(The9_Detail.class);
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
		appName = page.getHtml().xpath("//dd[@id='linfo']/h1/text()").toString();	
		appUpdateDate=page.getHtml().xpath("//dd[@id='linfo']/p[2]/i[3]/text()").toString();
		appUpdateDate=ExtrInfo(appUpdateDate);
		String str="";
		if(appUpdateDate!=null&&appUpdateDate.length()<10)
		{
			String temp[]=appUpdateDate.split("/");
			str=temp[0];
			for(int i=1;i<temp.length;i++)
			{
				if(temp[i].length()<2)
				{
					str=str+"-0"+temp[i];
				}
				else
				{
					str=str+"-"+temp[i];
				}
			}
		}
		appUpdateDate=str;
		appSize=page.getHtml().xpath("//dd[@id='linfo']/p[2]/i[4]/b/text()").toString();
		appVersion=page.getHtml().xpath("//dd[@id='linfo']/p[2]/i[5]/text()").toString();
		appVersion=ExtrInfo(appVersion);
		appDescription=page.getHtml().xpath("//div[@id='intro']/p/text()").toString();
		osPlatform=page.getHtml().xpath("//dd[@id='linfo']/p[2]/i[7]/text()").toString();
		if(osPlatform!=null&&!osPlatform.contains("安卓")&&!osPlatform.contains("android")&&!osPlatform.contains("Android"))
		{
			appName=null;
			osPlatform=null;
		}
		osPlatform=null;
		appScrenshot=page.getHtml().xpath("//div[@class='dimg']/span/i/img/@src").all();
//		String  string=page.getHtml().xpath("//div[@class='dimg']/span/i/img/@src").toString();
//		System.out.println(string);
		appDownloadUrl=page.getHtml().xpath("//dd[@id='uaddl']/h3/a/@href").toString();
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
	private static String ExtrInfo(String str)
	{
		if(str==null)
		{
			return null;
		}
		else
		{
			return str.split("：")[1];
		}
	}
	
	
	

}
