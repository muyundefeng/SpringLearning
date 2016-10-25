package com.appCrawler.pagePro.apkDetails;

import java.io.UnsupportedEncodingException;
/**
 * itools http://android.itools.cn/
 * Itools #192
 * @author DMT
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;

public class Itools_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Itools_Detail.class);

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
		
		appName = page.getHtml().xpath("//dl[@class='fl']/dt/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='fl w140']/a/@href").toString();
		appCategory = page.getHtml().xpath("//dl[@class='fl']/dd[1]/span[1]/text()").toString();
		appTag = page.getHtml().xpath("//dl[@class='fl']/dd[1]/span[2]/text()").toString();
		appSize = page.getHtml().xpath("//dl[@class='fl']/dd[2]/text()").toString();
		appVersion = page.getHtml().xpath("//dl[@class='fl']/dd[3]/text()").toString();
		osPlatform = page.getHtml().xpath("//dl[@class='fl']/dd[4]/text()").toString();
		appUpdateDate = page.getHtml().xpath("//dl[@class='fl']/dd[5]/text()").toString();
		appVenderName = page.getHtml().xpath("//dl[@class='fl']/dd[6]/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='details_pic']//img/@src").all();
		appDescription = usefulInfo(page.getHtml().xpath("//div[@class='details_apptext']").toString());
		
		if(appDownloadUrl!=null)
		{
			try {
				appDownloadUrl = java.net.URLEncoder.encode(appDownloadUrl, "utf-8");
				appDownloadUrl=appDownloadUrl.replace("%3A", ":").replace("%2F", "/").replace("%3F", "?").replace("%3D", "=");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		
		
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
	
	
}
