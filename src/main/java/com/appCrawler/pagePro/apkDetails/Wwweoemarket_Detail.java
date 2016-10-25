package com.appCrawler.pagePro.apkDetails;

/**
 * 优亿市场 http://www.eoemarket.com
 * Wwweoemarket_Detail #193
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;

public class Wwweoemarket_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Wwweoemarket_Detail.class);

	public static Apk getApkDetail(Page page) {
		Apk apk = null;
		String appName = null; // app名字
		String appDetailUrl = null; // 具体页面url
		String appDownloadUrl = null; // app下载地址
		String osPlatform = null; // 运行平台
		String appVersion = null; // app版本
		String appSize = null; // app大小
		String appUpdateDate = null; // 更新日期
		String appType = null; // 下载的文件类型 apk？zip？rar？
		String appVenderName = null; // app开发者 APK这个类中尚未添加
		String appDownloadedTime = null; // app的下载次数
		String appDescription = null; // app的详细描述
		List<String> appScrenshot = null; // app的屏幕截图
		String appTag = null; // app的应用标签
		String appCategory = null; // app的应用类别
//http://www.eoemarket.com/game/376608.html
		
		appName = page.getHtml().xpath("//span[@class='name_appintr']/text()").toString();
		if(appName == null) return null;
//		System.out.println(page.getHtml().toString());
		appDetailUrl = page.getUrl().toString();	
		appDownloadUrl = page.getHtml().xpath("//span[@class='download_intr']/a/@href").toString();
		
		appSize = getDetailInfo(page.getHtml().xpath("//span[@class='info_appintr clearfix']/span[1]/em[1]/text()").toString());
		appVersion = getDetailInfo(page.getHtml().xpath("//span[@class='info_appintr clearfix']/span[1]/em[2]/text()").toString());
		appDownloadedTime = getDetailInfo(page.getHtml().xpath("//span[@class='info_appintr clearfix']/span[1]/em[3]/text()").toString()).replace("万次", "0000");
		
		osPlatform = getDetailInfo(page.getHtml().xpath("//span[@class='info_appintr clearfix']/span[2]/em[1]/text()").toString());		
		appUpdateDate = getDetailInfo(page.getHtml().xpath("//span[@class='info_appintr clearfix']/span[2]/em[3]/text()").toString());
		
		
		appTag = page.getHtml().xpath("//div[@class='detailhot_lable_c clearfix']//em/text()").all().toString();
		appCategory = page.getHtml().xpath("//div[@class='page_info']/ol/li[5]/a/text()").toString();
		appScrenshot = page.getHtml().xpath("//ul[@class='introd_imgs_c clearfix']//img/@src").all();
		appDescription = usefulInfo(page.getHtml().xpath("//div[@class='profile_app mt20']").toString());
		
		
		//
		 System.out.println("appName=" + appName);
		 System.out.println("appDetailUrl=" + appDetailUrl);
		 System.out.println("appDownloadUrl=" + appDownloadUrl);
		 System.out.println("osPlatform=" + osPlatform);
		 System.out.println("appVersion=" + appVersion);
		 System.out.println("appSize=" + appSize);
		 System.out.println("appUpdateDate=" + appUpdateDate);
		 System.out.println("appType=" + appType);
		 System.out.println("appVenderName=" + appVenderName);
		 System.out.println("appDownloadedTime=" + appDownloadedTime);
		 System.out.println("appDescription=" + appDescription);
		 System.out.println("appTag=" + appTag);
		 System.out.println("appScrenshot=" + appScrenshot);
		 System.out.println("appCategory=" + appCategory);

		if (appName != null && appDownloadUrl != null) {
			apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform,
					appVersion, appSize, appUpdateDate, appType, null);
			// Apk(String appName,String appMetaUrl,String appDownloadUrl,String
			// osPlatform ,
			// String appVersion,String appSize,String appTsChannel, String
			// appType,String cookie){
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);

		} else
			return null;

		return apk;
	}
	
	private static String usefulInfo(String allinfoString) {
		String info = null;
		if(allinfoString == null) return null;
		int length =allinfoString.length()+1;
		while (allinfoString.contains("<") && allinfoString.length()<length){
			length = allinfoString.length();
			if (allinfoString.indexOf("<") == 0)
				allinfoString = allinfoString.substring(
						allinfoString.indexOf(">") + 1, allinfoString.length());
			else if (allinfoString.contains("<!--"))
				allinfoString = allinfoString.substring(0,
						allinfoString.indexOf("<!--"))
						+ allinfoString.substring(
								allinfoString.indexOf("-->") + 3,
								allinfoString.length());
			else
				allinfoString = allinfoString.substring(0,
						allinfoString.indexOf("<"))
						+ allinfoString.substring(
								allinfoString.indexOf(">") + 1,
								allinfoString.length());
		}
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
	
	private static String getDetailInfo(String detail) {
		
		if(detail!=null && !detail.endsWith("："))
			return detail.split("：")[1];
		else return null;
		
	}
	
	
}
