package com.appCrawler.pagePro.apkDetails;

/**			
 * 爱吾安卓 http://www.25az.com/
 * Www25az
 * 有些应用放在百度网盘中，无法下载	
 * 下载链接首选提供本地下载的链接，其次选择百度网盘的，百度网盘的提供的是下载页面的链接	
 * @author DMT
 */
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;


import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

public class Www25az_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Www25az_Detail.class);

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

		appName = page.getHtml().xpath("//div[@class='app-msg']/h1/text()")
				.toString();

		appDetailUrl = page.getUrl().toString();

		appDownloadUrl = getDownloadUrl(page.getHtml());

		osPlatform = page.getHtml()
				.xpath("//div[@class='app-msg']/dl/dd[5]/text()").toString();

		appVersion = page.getHtml()
				.xpath("//div[@class='app-msg']/dl/dd[1]/text()").toString();

		appSize = page.getHtml()
				.xpath("//div[@class='app-msg']/dl/dd[2]/text()").toString();

		String updatedateString = page.getHtml()
				.xpath("//div[@class='l']/li[8]/text()").toString();
		if (updatedateString != null && updatedateString.contains("："))
			appUpdateDate = updatedateString.substring(
					updatedateString.indexOf("：") + 1,
					updatedateString.length());

		appType = "apk";
		appDescription = page.getHtml()
				.xpath("//div[@class='app_info']/div[7]/div[2]/text()")
				.toString();
		appScrenshot = page.getHtml()
				.xpath("//div[@class='img_screen']//img/@src").all();
		appCategory = page.getHtml()
				.xpath("//ul[@class='bread-nav']/li[3]/a/text()").toString();
		appUpdateDate = page.getHtml().xpath("//div[@class='app-msg']/div/div[2]/text()").toString();
		appUpdateDate = StringUtils.substringAfter(appUpdateDate, "更新时间：");
		//
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

	private static String getDownloadUrl(Html html) {
		String url = "";
		// 首选本地下载，没有的选择百度网盘的下载地址
		String allurl = html.xpath("//div[@class='app_down']").toString();
		// System.out.println(allurl);
		String tag = "";
		if (allurl.contains("本地下载"))
			tag = "本地下载";
		else
			tag = "百度盘下载";
		List<String> downloadUrl = html.xpath("//div[@class='app_down']/a")
				.all();
		for (String string : downloadUrl) {
			if (string.contains(tag))
				url = "http://www.25az.com"
						+ StringUtils.substringAfter(string, "href=\"");
		}

		return StringUtils.substringBefore(url, "\"");
	}

	// public static void main(String[] args) {
	// String htmlString = SinglePageDownloader
	// .getHtml("http://www.25az.com/Android/View/7442/");
	// System.out.println(getDownloadUrl(new Html(htmlString)));
	// }

}
