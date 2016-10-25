package com.appCrawler.pagePro.apkDetails;
/**
 * 搜枣应用
 * 网站主页：http://www.yx93.com/index.html
 * 渠道编号：400
 * @author lisheng
 */
import java.util.List;

import org.eclipse.jetty.util.statistic.SampleStatistic;
import org.slf4j.LoggerFactory;

import com.appCrawler.utils.PropertiesUtil;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Yx93_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Yx93_Detail.class);
	private static String appURl="";
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
		//page.getHtml()
		appName = page.getHtml().xpath("//div[@class='md_top']/dl/dt/h1/stang/text()").toString();	
		appDownloadUrl=page.getHtml().xpath("//div[@class='zhushoubtns']/a[1]/@onclick").toString();
		appURl=page.getUrl().toString();
		//sSystem.out.println(appDownloadUrl);
		if(appDownloadUrl!=null&appDownloadUrl.contains("Downhttpids"))
		{
			String temp[]=appDownloadUrl.split(",");
			String appId=temp[0].replace("Downhttpids('", "").replace("'", "");;
			String fString=temp[1].replace("')", "").replace("'", "");
			String param="http://www.yx93.com/down.aspx?r=f"+"&f="+fString+"&Id="+appId;
			String str=SinglePageDownloader.getHtml(param);
			appDownloadUrl=Tostartthedownload(appId,str);
		}
		else{
			appDownloadUrl=page.getHtml().xpath("//a[@class='DownbuttonImgBlue']/@onclick").toString();
			//System.out.println(appDownloadUrl);
			String appId=appDownloadUrl.replace("Downhttpidss('", "").replace("')", "");
			//String appId=temp[0].replace("Downhttpids('", "").replace("'", "");;
			//String fString=temp[1].replace("')", "").replace("'", "");
			String param="http://www.yx93.com/down.aspx?r=r&Id="+appId;
			String str=SinglePageDownloader.getHtml(param);
			appDownloadUrl=Tostartthedownload(appId,str);
		}
		
		appVersion=page.getHtml().xpath("//div[@class='md_top']/dl/div").toString();
		String raw=usefulInfo(appVersion);
		//System.out.println(raw);
		appVersion=raw.split("版本")[1];
		appVersion=appVersion.split("语言：")[0];
		if(appVersion.contains("&nbsp;"))
		{
			appVersion=appVersion.replace("&nbsp;", "");
		}
		if(appVersion.contains("："))
		{
			appVersion=appVersion.replace("：", "");
		}
		appDetailUrl=page.getUrl().toString();
		appSize=page.getHtml().xpath("//div[@class='param']").toString();
		String raw1=usefulInfo(appSize);
		appSize=(raw1.split("大小：")[1]).split("固件：")[0];
		if(appSize.contains("&nbsp;"))
		{
			appSize=appSize.replace("&nbsp;", "");
		}
		osPlatform=(raw1.split("固件：")[1]).split("更新时间：")[0];
		if(osPlatform.contains("&nbsp;"))
		{
			osPlatform=osPlatform.replace("&nbsp;", "");
		}
		appUpdateDate=raw1.split("更新时间：")[1];
		appScrenshot=page.getHtml().links("//div[@class='screenshot-out']").all();
		appDescription=page.getHtml().xpath("//div[@class='d_details']").toString();
		appDescription=usefulInfo(appDescription);
		
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
	
	//下面这段代码是将js代码转化成java代码来处理相关的下载链接的生成
	 public static String  Loaddownload(String o, String oo) 
	    {
	     String sooo = strfun("1"),soooo = strfun("3");
	     String soo[] = strfun("2").split("");
	     String os[] = "yuioptrewqhgfdsajklmnbzxcv".split(""); 
	     String ooo[] = sooo.split(""), oooo[] = soooo.split(""), oooooo []= strfun("5").split("|"), ooooo []= oo.split(""); 
	     oo = ""; 
	     o = o.toUpperCase().replace(":", "").replace(".", "").replace("/", ""); 
	     System.out.println(o);
	     String so = strfun("4");
	    // System.out.println(so);
	     for (int j = 0; j < ooooo.length; j++) 
	     	{ 
	     		 if (sooo.contains(ooooo[j])) 
	     		 	{
	     		 	 for (int i = 0; i < oooo.length; i++) 
	     		 	 	{ 
	     		 	 		if (ooooo[j].equals( ooo[i])) 
	     		 	 			{ 
	     		 	 				if (o.contains(oooooo[0])&& so.contains(oooooo[3]) && o.contains(oooooo[2]) && so.contains(oooooo[1])) 
	     		 	 					
	     		 	 					oo += oooo[i] ;
	     		 	 			} 
	     		 	 		} 
	     		 	 } 
	     			else 
	 				 { 
			 			for (int i = 0; i < soo.length; i++) 
			 				{ 
			 					if (ooooo[j].equals( os[i])) 
			 						{ 
			 							if (o.contains(oooooo[0]) && so.contains(oooooo[3]) && o.contains(oooooo[2]) && so.contains(oooooo[1])) 
			 								oo += soo[i] ;
			 						}
			 				} 
	 				 } 
	     		}
	 			System.out.println(oo);
	 			return oo;
	     			//godown(oo) ;

	     	}
	    
	    public static String strfun(String o) 
	    { 
	    	if (o.equals("1")) 
	    		return ";321456987'!#$^YUIOPTREWQHJKLGFDSAVCXZBNM0_- :."; 
	    	if (o.equals("4")) 
	    		//return null;
	    		//return "http://www.yx93.com/game/29/29748.html".toUpperCase().replace(":", "").replace("/", "").replace(".", "").replace("COM", "CN").replace("YX93", "SOZAO").replace("X159", "SOZAO"); 
	    		return appURl.toUpperCase().replace(":", "").replace("/", "").replace(".", "").replace("COM", "CN").replace("YX93", "SOZAO").replace("X159", "SOZAO"); 
	    	if (o.equals("2")) 
	    		return "qwertyuiopasdfghjklzxcvbnm"; 
	    	if (o.equals("5")) 
	    		return "SOZAOCN|SOZAO|APK|HTTP"; 
	    	if (o.equals("3")) 
	    		return "QWERTYUIOPASDFGHJKLZXCVBNM0123456789.:/_-=&? ()" ;
	    	else
	    		return null;
	    }
	    public static void Tipsaboutit() 
	    { 
	    	System.out.println("很抱歉，下载出现了点错误"); 
	    }
	    public static String Tostartthedownload(String o, String oo) 
	    { 
	    	if (oo.indexOf("Android|") != -1) 
	    		{ 
	    			oo = oo.replace("Android|", "");
	    			//System.out.println(oo);
	    			return Loaddownload("http://sozao.cn/" + o + "/" + o + ".apk", oo); 
	    		} 
	    	else 
	    		Tipsaboutit(); 
	    	return null;
	    }
}
