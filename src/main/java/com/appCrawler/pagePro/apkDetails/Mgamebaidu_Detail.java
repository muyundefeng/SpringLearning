package com.appCrawler.pagePro.apkDetails;
import java.util.List;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;

/**
 * 百度移动网盟-CocosPlay  http://mgame.baidu.com/game/
 * Mgamebaidu #374
 * @author tianlei
 */

public class Mgamebaidu_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mgamebaidu_Detail.class);
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
		
		appName = page.getHtml().xpath("//div[@class='name_xing']/div/text()").toString();
		appDetailUrl = page.getUrl().toString();
		appVersion = page.getHtml().xpath("//table[@class='canshu_table']/tbody/tr[2]/td[2]/text()").toString().replace("版本：", "");
		appDownloadedTime = page.getHtml().xpath("//table[@class='canshu_table']/tbody/tr[2]/td[1]/text()").toString().replace("下载次数：", "").replace("万+", "0000");
		appSize = page.getHtml().xpath("//table[@class='canshu_table']/tbody/tr[1]/td[1]/text()").toString().replace("游戏大小：", "");
		appUpdateDate =page.getHtml().xpath("//table[@class='canshu_table']/tbody/tr[3]/td[1]/text()").toString().replace("更新时间：", "").replace(".", "-");
		appCategory = page.getHtml().xpath("//table[@class='canshu_table']/tbody/tr[1]/td[2]/text()").toString().replace("类别：", "");	
		appDescription = page.getHtml().xpath("//span[@id='gameinfointro']/text()").toString();
		appScrenshot = page.getHtml().xpath("//ul[@class='dk_img_roll_ul']//div/img/@src").all();
		appDownloadUrl = page.getHtml().xpath("//div[@class='down_zj colf f14 tc pointer bgclan']/@onclick").toString().replace("$.loc('", "").replace("')","");		
		
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
		else return null;
		return apk;
	}		

}
