package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.appCrawler.pagePro.apkDetails.Diyiapp_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * diyiapp http://www.diyiapp.com/				未完成
 * Diyiapp #103
 * (1)没有发现翻页
 * (2)搜索结果是使用js写的
 * @author DMT
 */
public class Diyiapp implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page				http://www.diyiapp.com/search/?keyword=qq&type=game
		if(page.getUrl().regex("http://api.diyiapp.com/search/.*").match()){
			String jsonUrl=page.getUrl().toString();
			String json=SinglePageDownloader.getHtml(jsonUrl);
			json=json.replace("showResult(", "");
			json=json.replace(")","");
			ObjectMapper objectMapper=new ObjectMapper();
			try
			{
				Map<String, Object> map=objectMapper.readValue(json, Map.class);
				List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("game");
				for(Map<String, Object> map1:list)
				{
					String appId=map1.get("aid").toString();
					String appUrl="http://www.diyiapp.com/app/"+appId+"/";
					page.addTargetRequest(appUrl);
				}
			}
			catch(Exception e){e.printStackTrace();}
		}
			
		//the app detail page
		if(page.getUrl().regex("http://www\\.diyiapp\\.com/app/\\d+/").match())
		{
			return Diyiapp_Detail.getApkDetail(page);
		}
//		if(page.getUrl().regex("http://www\\.ruan8\\.com/soft.*").match()){
//			Apk apk = null;
//			String appName = null;				//app名字
//			String appDetailUrl = null;			//具体页面url
//			String appDownloadUrl = null;		//app下载地址
//			String osPlatform = null ;			//运行平台
//			String appVersion = null;			//app版本
//			String appSize = null;				//app大小
//			String appUpdateDate = null;		//更新日期
//			String appType = null;				//下载的文件类型 apk？zip？rar？ipa?
//			String appvender = null;			//app开发者  APK这个类中尚未添加
//			String appDownloadedTime=null;		//app的下载次数
//			
//			String nameString=page.getHtml().xpath("//div[@class='mdlmenu w750']/h1/text()").toString();			
//				appName =nameString.substring(0,nameString.indexOf("v")-1);
//				
//			appDetailUrl = page.getUrl().toString();
//			
//			appDownloadUrl = page.getHtml().xpath("//div[@class='mdddblist']/p/a/@href").toString();
//			
//			osPlatform = page.getHtml().xpath("//p[@id='os']/text()").toString();
//			
//			String versionString = page.getHtml().xpath("//div[@class='mdlmenu w750']/h1/text()").toString();
//				appVersion = versionString.substring(versionString.indexOf("v")+1,versionString.lastIndexOf(".")+2);
//			
//			String sizeString = page.getHtml().xpath("//ul[@class='mdccs']/li[7]/text()").toString();
//				appSize = sizeString.substring(sizeString.indexOf("：")+1,sizeString.length());
//			
//			String updatedateString = page.getHtml().xpath("//ul[@class='mdccs']/li[8]/text()").toString();
//				appUpdateDate = updatedateString.substring(updatedateString.indexOf("：")+1,updatedateString.length());
//			
//			String typeString = page.getHtml().xpath("//ul[@class='mdccs']/li[3]/text()").toString();
//				appType =typeString.substring(typeString.indexOf("：")+1,typeString.length());
//			
//			appvender=null;
//				
//			String DownloadedTimeString = page.getHtml().xpath("//ul[@class='mdccs']/li[9]/text()").toString();
//				appDownloadedTime = DownloadedTimeString.substring(DownloadedTimeString.indexOf("：")+1,DownloadedTimeString.length());		
//			
//			System.out.println("appName="+appName);
//			System.out.println("appDetailUrl="+appDetailUrl);
//			System.out.println("appDownloadUrl="+appDownloadUrl);
//			System.out.println("osPlatform="+osPlatform);
//			System.out.println("appVersion="+appVersion);
//			System.out.println("appSize="+appSize);
//			System.out.println("appUpdateDate="+appUpdateDate);
//			System.out.println("appType="+appType);
//			System.out.println("appvender="+appvender);
//			System.out.println("appDownloadedTime="+appDownloadedTime);
//		
//			if(appName != null && appDownloadUrl != null){
//				apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			}
//			
//			return apk;
//		}
		
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
