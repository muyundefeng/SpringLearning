package com.appCrawler.pagePro.apkDetails;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.bcel.generic.NEW;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Nduoa_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Nduoa_Detail.class);
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
		String nameString=page.getHtml().xpath("//span[@class='title']/text()").toString();			
		appName =nameString;
		
	appDetailUrl = page.getUrl().toString();
	
	appDownloadUrl = page.getHtml().xpath("//div[@class='downloadWrap']/div[1]/a/@href").toString();
	
	String osPlatformString = page.getHtml().xpath("//div[@class='adapt row popup']/h4/text()").toString();
		osPlatform = osPlatformString.substring(osPlatformString.indexOf("：")+1,osPlatformString.length());
	
	String versionString = page.getHtml().xpath("//div[@class='apkinfo']/div[1]/div[2]/span[2]/text()").toString();
		appVersion = versionString.substring(versionString.indexOf("(")+1,versionString.indexOf(")"));
		appVersion = appVersion.replace(" ", "");
	
		String sizeString = page.getHtml().xpath("//div[@class='size row']/text()").toString();
		appSize = sizeString.substring(sizeString.indexOf("：")+1,sizeString.length());
	
	String updatedateString = page.getHtml().xpath("//div[@class='updateTime row']/em/text()").toString();
		appUpdateDate = getFormatedDate(updatedateString);
	
	String typeString = "apk";
		appType =typeString;
	
	appVenderName = page.getHtml().xpath("//div[@class='author row']/span/a/text()").toString();
	
		
	String DownloadedTimeString = page.getHtml().xpath("//div[@class='levelCount']/span[2]/text()").toString();
		appDownloadedTime = DownloadedTimeString.replace("次下载", "").replace(",", "");	
		
		appDescription = page.getHtml().xpath("//div[@id='detailInfo']/div/div//p/text()").all().toString();
		appScrenshot = page.getHtml().xpath("//ul[@class='shotbox']//li/img/@src").all();
		appCategory = page.getHtml().xpath("//div[@id='breadcrumbs']/span[3]/a/text()").toString();
		
//		System.out.println("appName="+appName);
//		System.out.println("appDetailUrl="+appDetailUrl);
//		System.out.println("appDownloadUrl="+appDownloadUrl);
//		System.out.println("osPlatform="+osPlatform);
//		System.out.println("appVersion="+appVersion);
//		System.out.println("appSize="+appSize);
//		System.out.println("appUpdateDate="+appUpdateDate);
//		System.out.println("appType="+appType);
//		System.out.println("appVenderName="+appVenderName);
//		System.out.println("appDownloadedTime="+appDownloadedTime);
//		System.out.println("appDescription="+appDescription);
//		System.out.println("appTag="+appTag);
//		System.out.println("appScrenshot="+appScrenshot);
//		System.out.println("appCategory="+appCategory);

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
		
		return apk;
	}
/*dateString的内容一般为	发布于一个月前	发布于8天前	发布于8小时前
 * */
	@SuppressWarnings("deprecation")
	private static String getFormatedDate(String dateString)
	{		
		if(dateString == null) return null;
		else dateString = dateString.replace("发布于", "");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Date date = new Date();
		int monMinus = 0;
		int dayMinus = 0;
		int hourMinus = 0;
		if(dateString.contains("月前")){
			String monthMinusString = dateString.replace("个月前", "");			
			switch (monthMinusString) {
			case "一":monMinus=1;break;
			case "二":monMinus=2;break;
			case "三":monMinus=3;break;
			case "四":monMinus=4;break;
			case "五":monMinus=5;break;
			case "六":monMinus=6;break;
			case "七":monMinus=7;break;
			case "八":monMinus=8;break;
			case "九":monMinus=9;break;
			case "十":monMinus=10;break;
			case "十一":monMinus=11;break;
			case "十二":monMinus=12;break;
			default:monMinus = 0;
				break;
			}
		}else if(dateString.contains("天前")){
			String dayMinusString = dateString.replace("天前", "");
			dayMinus = Integer.valueOf(dayMinusString);			
		}else if(dateString.contains("小时前")){
			String hourMinusString = dateString.replace("小时前", "");
			hourMinus = Integer.valueOf(hourMinusString);
		}
		
		
		
//		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		
		
		return df.format(new Date(date.getYear(),
				date.getMonth()-monMinus,
				date.getDay()-dayMinus,
				date.getHours()-hourMinus,
				date.getMinutes(),date.getSeconds()));
	}
	
	public static void main(String[] args){
		System.out.println(getFormatedDate("发布于8天前"));
	}
	
	

}
