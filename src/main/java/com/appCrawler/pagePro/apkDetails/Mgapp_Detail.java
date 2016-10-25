package com.appCrawler.pagePro.apkDetails;

/**
 * 应用酷  http://www.mgyapp.com/
 * Mgapp #119
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;

import us.codecraft.webmagic.Page;

public class Mgapp_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Mgapp_Detail.class);

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

		appName = page.getHtml().xpath("//h1[@class='det-title']/text()")
				.toString();
		if (appName == null)
			return null;

		appDetailUrl = page.getUrl().toString();

		appDownloadUrl = page.getHtml()
				.xpath("//li[@class='det-butn']/a[1]/@href").toString();
		String platFormString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[6]/text()").toString();
		osPlatform = platFormString.substring(platFormString.indexOf("：") + 1,
				platFormString.length());

		String versionString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[1]/text()").toString();
		appVersion = versionString.substring(versionString.indexOf("：") + 1,
				versionString.length());

		String sizeString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[4]/text()").toString();
		appSize = sizeString.substring(sizeString.indexOf("：") + 1,
				sizeString.length());

		String updatedateString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[3]/text()").toString();
		appUpdateDate = updatedateString.substring(
				updatedateString.indexOf("：") + 1, updatedateString.length());

		String typeString = "apk";
		appType = typeString;

		String venderString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[7]/text()").toString();
		appVenderName = venderString.substring(venderString.indexOf("：") + 1,
				venderString.length());
		;

		String DownloadedTimeString = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[5]/text()").toString();
		appDownloadedTime = DownloadedTimeString.substring(
				DownloadedTimeString.indexOf("：") + 1,
				DownloadedTimeString.length());
		appDownloadedTime = getFormatedDownloadedTime(appDownloadedTime);

		appDescription = page.getHtml()
				.xpath("//dd[@class='det-intro']/p/text()").toString();
		appScrenshot = page.getHtml()
				.xpath("//ul[@class='pa det-pic-list']//img/@src").all();
		appCategory = page.getHtml().xpath("//h2[@class='fl']/a[3]/text()")
				.toString();
		appTag = page.getHtml()
				.xpath("//ul[@class='det-info-list']/li[9]/a/text()").all()
				.toString();

		// System.out.println("appName="+appName);
		// System.out.println("appDetailUrl="+appDetailUrl);
		// System.out.println("appDownloadUrl="+appDownloadUrl);
		// System.out.println("osPlatform="+osPlatform);
		// System.out.println("appVersion="+appVersion);
		// System.out.println("appSize="+appSize);
		// System.out.println("appUpdateDate="+appUpdateDate);
		// System.out.println("appType="+appType);
		// System.out.println("appVenderName="+appVenderName);
		// System.out.println("appDownloadedTime="+appDownloadedTime);
		// System.out.println("appDescription="+appDescription);
		// System.out.println("appTag="+appTag);
		// System.out.println("appScrenshot="+appScrenshot);
		// System.out.println("appCategory="+appCategory);
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

	private static String getFormatedDownloadedTime(String appDownloadedTime) {
		String formatTimes = "";
		// http://www.mgyapp.com/games/detail-627642?loc=list&ext=listbycid&ext2=0
		// W+次
		// http://www.mgyapp.com/apps/detail-630091?loc=list&ext=listbycid&ext2=0
		// 次
		// 2015年11月10日11:48:40
		if (appDownloadedTime.contains("W+"))
			formatTimes = appDownloadedTime.replace("W+次", "0000");
		else
			formatTimes = appDownloadedTime.replace("次", "");
		return formatTimes;
	}

}
