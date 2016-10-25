package com.appCrawler.pagePro.apkDetails;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Androidmi http://www.Androidmi.com/
 * Androidmi #204
 * @author DMT
 * 
 */
import java.util.List;
import java.util.regex.*;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Androidmi_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Androidmi_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='contents']/table/caption/h5/text()").toString().split(" ")[0];	
		if(appName.contains("《"))
		{
			appName=appName.substring(1);
		}
		appVersion = page.getHtml().xpath("//div[@class='contents']/table/tbody/tr[1]/td[3]/text()").toString().replace("V", "").replace("v", "");
		appSize = page.getHtml().xpath("//div[@class='contents']/table/tbody/tr[2]/td[2]/text()").toString();
		//System.out.println(appSize.charAt('＋'));
		if(appSize.contains("＋"))
		{
			System.out.println(appSize);
			String str[]=appSize.split("\\＋");
			int size1=0;
			for(String str1:str)
			{
				System.out.println(str1);
				str1=str1.replaceAll("M", "");
				size1+=Double.parseDouble(str1);
			}
			appSize=size1+"M";
		}
		appCategory= page.getHtml().xpath("//div[@class='contents']/table/tbody/tr[3]/td[1]/text()").toString();
		osPlatform=page.getHtml().xpath("//div[@class='contents']/table/tbody/tr[3]/td[2]/text()").toString();
		appUpdateDate=page.getHtml().xpath("//div[@class='contents']/table/tbody/tr[5]/th/text()").toString().split(" ")[1];
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = appDetailUrl+"#androidmi_download";
		String info=page.getHtml().xpath("//div[@class='down_intro']").toString();
		appDescription=usefulInfo(info);
		if(appDownloadUrl.contains("#androidmi_download"))
		{
			appDownloadUrl=null;
		}
		
		appScrenshot = page.getHtml().xpath("//div[@class='down_intro']/strong/img/@src").all();
//		
//		if(appDescription ==null){
//			appDescription=page.getHtml().xpath("//div[@class='down_intro']/span/span/span/text()").toString();
//		}
//		
//		if(appDescription ==null){
//			appDescription=page.getHtml().xpath("//div[@class='down_intro']/span/span/text()").toString();
//		}
//		
//		if(appDescription ==null){
//			appDescription=page.getHtml().xpath("//div[@class='down_intro']/text()").toString();
//		}
		
	
	    if(appScrenshot.size() == 0){
			appScrenshot=page.getHtml().xpath("//div[@class='down_intro']/span/span/img/@src").all();
		}
		if(appScrenshot.size() == 0){
			appScrenshot=page.getHtml().xpath("//div[@class='down_intro']/span/span/span/img/@src").all();
		}
		if(appScrenshot.size() == 0){
			appScrenshot=page.getHtml().xpath("//div[@class='down_intro']/img/@src").all();
		}
		if(appScrenshot.size() == 0){
			appScrenshot=getImage(info);
		}	

		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
//					String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
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
   
	private static List<String> getImage(String str){
    	List<String> tmp=new ArrayList<String>();
		String regex="<img.*src=\"([^\"]+?)\".*/>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp.add(matcher.group(1).toString());
        	
        }
    	return tmp;   	
    }
	
	

}
