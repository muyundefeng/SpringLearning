package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 应用市场
 * http://m.apk.tw/
 * Aawap #632
 * @author lisheng
 */
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;


public class Mapk_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mapk_Detail.class);
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
		appName = page.getHtml().xpath("//div[@class='detailInfo mt-5']/h3[@class='mt-10']/text()").toString();	
		appVersion = page.getHtml().xpath("//div[@class='property mt-10']/ul/li[1]/text()").toString();
		try{
			appVersion = appVersion.split(":")[1];
			appSize = page.getHtml().xpath("//div[@class='property mt-10']/ul/li[2]/text()").toString();
			appSize = appSize.split(":")[1];
			appDownloadedTime = page.getHtml().xpath("//div[@class='property mt-10']/ul/li[3]/text()").toString();
			appDownloadedTime = appDownloadedTime.split("：")[1];
			appCategory = page.getHtml().xpath("//div[@class='property mt-10']/ul/li[4]/a/text()").toString();
			appUpdateDate = page.getHtml().xpath("//div[@class='property mt-10']/ul/li[7]/text()").toString();
			appUpdateDate =appUpdateDate.split("：")[1];
		}
		catch(Exception e){}
	
		appDetailUrl = page.getUrl().toString();
		appDownloadUrl = page.getHtml().xpath("//div[@class='download']/ul/li[1]/a/@href").toString();
		String raw = SinglePageDownloader.getHtml(page.getUrl().toString());
		String raw1 = raw.split("window.configJson =")[1];
		String json = raw1.split("</script>")[0];
		
		json = json.replace("	//軟體截圖： width & height = 小圖寬高	", "").replace("//軟體其他版本", "").replace("Screenshot", "\"Screenshot\"").replace("OrderVersion", "\"OrderVersion\"");
		//System.out.println(json);
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			Map<String,Object> map = objectMapper.readValue(json, Map.class);
			List<Map<String, Object>> list = (List<Map<String, Object>>)map.get("Screenshot");
			List<String> pics = new ArrayList<String>();
			for(Map<String, Object> map1:list)
			{
				pics.add(map1.get("BigImg").toString());
			}
			appScrenshot = pics;
		}
		catch(Exception e){e.printStackTrace();}
		appDescription = page.getHtml().xpath("//div[@class='aboutApp']").toString();
		appDescription = usefulInfo(appDescription);
		
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
	
	
	
	

}
