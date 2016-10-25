package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *龙珠游乐园  http://www.lzvw.com/
 * Aawap #222
 * @author DMT
 */
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Lzvw_Detail2 {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Lzvw_Detail2.class);
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
		//System.out.println("电我啊");
		//String[] temp1=appDetailUrl.split("?");
		appName = page.getHtml().xpath("//div[@class='content']/div[2]/h2/text()").toString();
		System.out.println(appName);
		appCategory = page.getHtml().xpath("//div[@id='info']/p[1]/span/a/text()").toString();
		appSize= page.getHtml().xpath("//div[@id='info']/p[3]/p[1]/text()").toString();
		String rawString=page.getHtml().xpath("//div[@id='info']/p[3]/span/script/@src").toString();
		System.out.println(rawString);
		Map<String, String> map = new HashMap<String, String>();  
		map.put("Cookie", "sogou_ts_ads0=popdate:115%u5E7411%u67081%u65E5&vt:0&ht:&qt:0&jd:; ASPSESSIONIDQACDBCCB=DOMBMLFDPDKFGHIKKJHJHJBJ; __jsluid=1ec644ac3958b8f2ea9e93e9deb64a0f; ASPSESSIONIDSCACDDDA=OGHJOKFDPJMPFDPPEDOMKNAE; ASPSESSIONIDQCBCCDCA=FFOLPLFDLGKGIEFPNHLMABLC; CNZZDATA1221717=cnzz_eid%3D1220294204-1446169774-%26ntime%3D1446426220; ASPSESSIONIDSCACCCDA=OICDKMFDIEBIBPEAMKNLEPEO; ASPSESSIONIDQACACDCB=BLCHKMFDBBFNOHBEMPLCIEFJ; ASPSESSIONIDQCAACDCA=PBGNCNFDHBDDFDCNEOPLPLEB; ASPSESSIONIDSCDDACDB=DHJBLNFDKOIHOFLONFJIBOEF");
		map.put("Host", "www.lzvw.com");  
		map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36" );
	
		String temp12 = SinglePageDownloader.getHtml("http://www.lzvw.com"+rawString,"",null,map);
		System.out.println(temp12);
		//int length1=temp12.length();
		//System.out.println(temp12);
		int end=0;
		int start=0;
		int j=0;
		while(true)
		{
			
			if(temp12.charAt(j)>=48&&temp12.charAt(j)<=57)
			{
				end++;
			}
			else
			{
				start++;
			}
			if(temp12.charAt(j)==59)
			{
				break;
			}
			j++;
		}
		int newStart=start-3;
		appDownloadedTime=temp12.substring(newStart,newStart+end);
		System.out.println(appDownloadedTime);
		appType= page.getHtml().xpath("//div[@id='info']/p[2]/strong/text()").toString();
		appDownloadUrl=page.getHtml().xpath("//div[@id='down2']/a[2]/@href").toString();
		if(!appName.contains("安卓")&&!appName.contains("Android")&&!appName.contains("android"))
		{
			appDownloadUrl=null;
		}
		System.out.println(appDownloadUrl);
		appDescription=page.getHtml().xpath("//div[@class='content']/div[3]").toString();
		if(appDescription!=null)
		{
			appDescription=usefulInfo(appDescription);
		}
		//appScrenshot=page.getHtml().xpath("//div[@id='pic']//img/@src").all();
		
		List <String> appScrenshot1=page.getHtml().xpath("//div[@id='pic']//img/@src").all();
		List<String> appScrenshot2 = new ArrayList<String>(appScrenshot1.size());
		//List <String> picList;
		for(int i=0;i<appScrenshot1.size();i++)
		{
			appScrenshot2.add("http://www.lzvw.com"+appScrenshot1.get(i));
		}
		//appScrenshot="http://www.lzvw.com"+appScrenshot;
		appScrenshot=appScrenshot2;
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
