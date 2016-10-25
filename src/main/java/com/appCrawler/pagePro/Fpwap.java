package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;








import com.appCrawler.pagePro.apkDetails.Fpwap_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 飞鹏网http://www.fpwap.com/
 * Fpwap.java  #67
 * 1.搜索页面有两个 ：
 * （1）http://www.fpwap.com/search/index.php?keyword=*#*#*#&platform_id=9; //这个是搜网游
 * （2）http://www.fpwap.com/search/index.php?keyword=*#*#*#&platform_id=3; //这个是搜游戏
 * 2.下载次数时动态获取的
 * @author DMT
 *
 */
public class Fpwap implements PageProcessor{
	Site site =Site.me().setCharset("gb2312").setRetryTimes(3).setSleepTime(2);
	@Override
	public Apk process(Page page) {
		//index page
		if(page.getUrl().regex("http://www\\.fpwap\\.com/search/index.php\\?*").match()){
			//app的具体介绍页面											
			List<String> url1 = page.getHtml().links("//ul[@class='search-result-list width90']").regex("http://www\\.fpwap\\.com/.*").all();

			//添加下一页url(翻页)
			List<String> url2 = page.getHtml().links("//div[@class='page-change']").regex("http://www\\.fpwap\\.com/search/index.php\\?*").all();
			url1.addAll(url2);
	
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		
		//the app detail page
		else	if(page.getUrl().regex("http://www\\.fpwap\\.com/*").match()){
//			Apk apk = null;
//			String appName = null;				//app名字
//			String appDetailUrl = null;			//具体页面url
//			String appDownloadUrl = null;		//app下载地址
//			String osPlatform = null ;			//运行平台
//			String appVersion = null;			//app版本
//			String appSize = null;				//app大小
//			String appUpdateDate = null;		//更新日期
//			String appType = null;				//下载的文件类型 apk？zip？rar？
//			String appVender = null;			//app开发者  APK这个类中尚未添加
//			String appDownloadedTime=null;//app的下载次数
//			
//			String nameString=page.getHtml().xpath("//div[@class='gameinfor-left']/dl/dt/h3/text()").toString();
//			if(nameString != null && nameString.contains("V"))
//			{
//				appName=nameString.substring(0,nameString.indexOf("V")-1);
//				appVersion = nameString.substring(nameString.indexOf("V")+1,nameString.length());
//			}
//			else if(nameString != null && nameString.contains("v"))
//			{
//				appName=nameString.substring(0,nameString.indexOf("v")-1);
//				appVersion = nameString.substring(nameString.indexOf("V")+1,nameString.length());
//			}
//			else if(nameString != null && nameString.contains("."))
//			{
//				appName=nameString.substring(0,nameString.indexOf(".")-1);
//				appVersion = nameString.substring(nameString.indexOf(".")-1,nameString.length());
//			}
//			else 
//			{
//				appName = nameString;
//				appVersion = null;
//			}
//			appDetailUrl = page.getUrl().toString();
//			appDownloadUrl = page.getHtml().xpath("//div[@class='dj-gamelaod']/ul/li/a/@href").toString();
//			osPlatform = null;			
//			String sizeString = page.getHtml().xpath("//div[@class='dj-xx comh3']/div/p[1]/text()").toString();
//				appSize = sizeString.substring(sizeString.indexOf("：")+1,sizeString.length());
//			String updatedateString = page.getHtml().xpath("//div[@class='dj-xx comh3']/div/p[3]/text()").toString();
//				appUpdateDate = updatedateString.substring(updatedateString.indexOf("：")+1,updatedateString.length());
//			appType = "apk";
//			String venderString = page.getHtml().xpath("//div[@class='dj-xx comh3']/div/p[5]/text()").toString();
//				appVender = venderString.substring(venderString.indexOf("：")+1,venderString.length());
//			String downloadedtimeString=page.getHtml().xpath("//div[@class='dj-xx comh3']/div/p[2]/script/@src").toString();
//				appDownloadedTime=downloadedtimeString;
//			String downloadedTimeUrl = "http://www.fpwap.com/"+downloadedtimeString;
//				String line=null;
//				try {
//					//打开一个网址，获取源文件，这个网址里面是一个document.write("****")
//					URL url=new URL(downloadedTimeUrl);
//					BufferedReader reader;
//					reader = new BufferedReader(new InputStreamReader(url.openStream()));
//					line=reader.readLine();
//					//line=document.write('30168');
//				} catch (Exception e) {
//				}
//				if(line != null)
//					appDownloadedTime =line.substring(line.indexOf("(")+2,line.indexOf(")")-1);
//			/*
//			System.out.println("appName="+appName);
//			System.out.println("appDetailUrl="+appDetailUrl);
//			System.out.println("appDownloadUrl="+appDownloadUrl);
//			System.out.println("osPlatform="+osPlatform);
//			System.out.println("appVersion="+appVersion);
//			System.out.println("appSize="+appSize);
//			System.out.println("appUpdateDate="+appUpdateDate);
//			System.out.println("appType="+appType);
//			System.out.println("appvender="+appVender);
//			System.out.println("appDownloadedTime="+appDownloadedTime);
//			 */
//		
//			if(appName != null && appDownloadUrl != null){
//				apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//			}
			
			return Fpwap_Detail.getApkDetail(page);
		}
		
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
