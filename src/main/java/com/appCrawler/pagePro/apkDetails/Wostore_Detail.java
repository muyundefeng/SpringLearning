package com.appCrawler.pagePro.apkDetails;
/**
 * 联通沃商店  http://mstore.wo.com.cn/index.action
 * Wostore #301
 * @author tianlei
 */
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;


public class Wostore_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Wostore_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='des f_l']/h3/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appVersion = page.getHtml().xpath("//p[@class='app_com color']/text()").toString().split("    ")[1];
		appSize = page.getHtml().xpath("//p[@class='app_com color']/text()").toString().split("    ")[0];
		appDownloadUrl = page.getHtml().xpath("//div[@class='mbtn_area']/a[1]/@href").toString();
    	appDescription = page.getHtml().xpath("//div[@class='app_scr_shot']/div[1]/text()").toString();
		appScrenshot = page.getHtml().xpath("//div[@class='app_scr_shot']/span/img[1]/@src").all();
		appScrenshot.add(page.getHtml().xpath("//div[@class='app_scr_shot']/span/img[2]/@src").toString());
		for (int i =0; i<appScrenshot.size();i++){
			appScrenshot.set(i, "http://mstore.wo.com.cn"+appScrenshot.get(i));
		}
		
	
		

		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
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

}
