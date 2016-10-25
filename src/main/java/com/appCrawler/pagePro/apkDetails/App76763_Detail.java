package com.appCrawler.pagePro.apkDetails;
/**
 * 76763游戏
 * 网站主页：http://www.7663.com/game/0_0_0_0_1/
 * Aawap #550
 * @author lisheng
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.downloader.SinglePageDownloader3;


public class App76763_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(App76763_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='game_name clearfix']/h1/text()").toString();	
		
		appDetailUrl = page.getUrl().toString();
		appCategory=page.getHtml().xpath("//div[@class='gifts_list clearfix']/ul/li[1]/a/text()").toString();
		appDownloadedTime=page.getHtml().xpath("//div[@class='gifts_list clearfix']/ul/li[3]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='gifts_list clearfix']/ul/li[6]/text()").toString();
		String appSize1=page.getHtml().xpath("//a[@class='android']/div[2]/text()").toString();
		appSize=appSize1!=null?appSize1.split("|")[1]:null;
		appVersion=appSize1!=null?appSize1.split("|")[2]:null;
		String html=SinglePageDownloader3.getHtml(page.getUrl().toString(),"utf-8");
		//System.out.println(html);
//		try{
//			appDownloadUrl=html.split("#code2")[1];
//			appDownloadUrl=appDownloadUrl.replace("').qrcode({width: 100,        height:100,text:'", "");
//			int i=0;
//			for(i=0;;i++)
//			{
//				if(appDownloadUrl.indexOf(i)=='\'')
//				{
//					break;
//				}
//						
//			}
//			appDownloadUrl=appDownloadUrl.substring(0, i);
//			appDownloadUrl=appDownloadUrl.split(";")[0];
//			appDownloadUrl=appDownloadUrl.replace("'})", "");
//		}
//		catch(Exception e){}
	try{
		appDownloadUrl=getStrings(html);
	}
	catch(Exception e){}
		appDescription=page.getHtml().xpath("//div[@class='jianjie']").toString();
		appDescription=usefulInfo(appDescription);
		appScrenshot=page.getHtml().xpath("//div[@id='focus_m']/ul/li/a/@href").all();
		

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
	 private static String getStrings(String html) {
	      //  String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
	        Pattern p = Pattern.compile("'#code2'\\).qrcode(.*?)type=1");
	        Matcher m = p.matcher(html);
	        String returnStr="";
	        ArrayList<String> strs = new ArrayList<String>();
	        while (m.find()) {
	        	
	            strs.add(m.group(1));            
	        } 
	        for (String s : strs){
	            //System.out.println("hello"+s);
	        	String string=s.replace("({width: 100,        height:100,text:'", "");
	        	returnStr=string+"type=1";
	        	System.out.println(returnStr);
	        	break;
	        }   
	        return returnStr;
	        
	    }
	
	
	

}
