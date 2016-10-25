package com.appCrawler.pagePro.apkDetails;
/**
 * 酷派coolmarket http://www.coolmart.net.cn/
 * Coolmarket #209
 * @author DMT
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Coolmarket_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Coolmarket_Detail.class);
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
		String info=null;
		int appId=getAppId(page.getUrl().toString());
		String infourl="http://api.aps.qq.com/wapapi/getDetail?appId="+appId+"&channel=77905&platform=touch&network_type=unknown&resolution=1366x768";
		String htmlinfo=SinglePageDownloader.getHtml(infourl,"get",null);
		
		appDetailUrl=page.getUrl().toString();
		appName=getAppName(htmlinfo);
		appDownloadedTime=getTime(htmlinfo);
		if(getSize(htmlinfo)!=null)
		{
		appSize = convertFileSize(Integer.parseInt(getSize(htmlinfo)));
		}
		appScrenshot=getImage(htmlinfo);
		appCategory=getCatagory(htmlinfo);
		appVersion=getVersion(htmlinfo);
		appDescription=getDescription(htmlinfo);
		appDownloadUrl =getDownloadUrl(htmlinfo);
		
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
						
		}
		else return null;
		
		return apk;
	}

	
	private static int getAppId(String str){
    	String tmp=null;
    	int id;
		String regex="appid=(.*)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){       	
        	tmp=matcher.group(1).toString();       	
        }
        id=Integer.parseInt(tmp);
    	return id;   	
    }
	
	private static String getAppName(String str){
    	String tmp=null;
		String regex="\"name\": \"([^\"]*)\",";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);  
        if(matcher.find()){       	
        	tmp=matcher.group(1).toString();       	
        }  
    	return tmp;   	
    }
	private static String getTime(String str){
    	String tmp=null;
		String regex="\"userCount\": ([^,]*)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);  
        if(matcher.find()){       	
        	tmp=matcher.group(1).toString();       	
        }  
    	return tmp;   	
    }
	private static String getSize(String str){
    	String tmp=null;
		String regex="\"size\": ([^,]*)";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);  
        if(matcher.find()){       	
        	tmp=matcher.group(1).toString();       	
        }  
    	return tmp;   	
    }
	private static List<String> getImage(String str){
    	List<String> tmp=new ArrayList<String>();
    	// "originalURL": "http://pp.myapp.com/ma_pic2/0/shot_1_1_1443586264/0",
		String regex="\"originalURL\": \"([^\"]*)\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str);  
        while(matcher.find()){        	
        	tmp.add(matcher.group(1).toString());       	
        }
    	return tmp;   	
    }
    private static String getCatagory(String str){
    	String tmp=null;
    	String regex="\"secCate\": \"([^\"]*)\"";
    	Pattern pattern=Pattern.compile(regex);
    	Matcher matcher=pattern.matcher(str);
    	if(matcher.find())
    		tmp=matcher.group(1).toString();
    	return tmp;
    }
    private static String getVersion(String str){
    	String tmp=null;
    	//"versionName": "4.47",
    	String regex="\"versionName\": \"([^\"]*)\"";
    	Pattern pattern=Pattern.compile(regex);
    	Matcher matcher=pattern.matcher(str);
    	if(matcher.find())
    		tmp=matcher.group(1).toString();
    	return tmp;
    }
    private static String getDescription(String str){
    	String tmp=null;
    	 //"des": "hold住你的
    	String regex="\"des\": \"([^\"]*)\"";
    	Pattern pattern=Pattern.compile(regex);
    	Matcher matcher=pattern.matcher(str);
    	if(matcher.find())
    		tmp=matcher.group(1).toString();
    	return tmp;
    }
    private static String getDownloadUrl(String str){
    	String tmp=null;
    	 //"url": "http://dd.myapp.com/16891/13EB8487446DEAE3F902F2C52A820394.apk?fsname=com.ireadercity.c7_4.47_447.apk",
    	String regex="\"url\": \"([^\"]*)\"";
    	Pattern pattern=Pattern.compile(regex);
    	Matcher matcher=pattern.matcher(str);
    	if(matcher.find())
    		tmp=matcher.group(1).toString();
    	return tmp;
    }
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }
}
