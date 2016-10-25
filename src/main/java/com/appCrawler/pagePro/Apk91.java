package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk91_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 91手机门户 http://apk.91.com/
 * Apk91.java #90
 * 搜索结果中翻页的url是通过script代码构造的,因此翻页的链接也可以构造方式得到，而后面很多页都是无关的结果，每页有20个结果，这里取前9页
 * 在app的具体介绍页面内，有其他版本的下载链接，这里也加入到链表里
 * 爬取过多会被封ip
 * @author DMT
 */
public class Apk91 implements PageProcessor{
	private Logger logger = LoggerFactory.getLogger(Apk91.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page		http://apk.91.com/soft/Android/search/3_5_0_0_%E4%BB%99%E5%89%91%E5%A5%87%E4%BE%A0%E4%BC%A0		
		if(page.getUrl().regex("http://apk\\.91\\.com/soft/Android/search.*").match()){
			//app的具体介绍页面											
			List<String> url1 = page.getHtml().links("//ul[@class='search-list clearfix']").regex("http://apk\\.91\\.com/Soft/Android.*").all();
			//System.out.println(page.getHtml().toString());
			//添加下一页url(翻页)  手动构造
			String currenturl=page.getUrl().toString();
			char pagenum=(char)currenturl.codePointAt(38);
			List<String> url2 = page.getHtml().links("//div[@class='page_footer']").regex("这是不会匹配到的").all();
			if(pagenum == 49)
			for(int i=1;i<1;i++){
				currenturl=currenturl.substring(0,38)+(++pagenum)+currenturl.substring(39, currenturl.length());
				url2.add(currenturl);
			}
			
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
		else if(page.getUrl().regex("http://apk\\.91\\.com/Soft/Android.*").match()){
			//app的具体介绍页面加入到链表  start
			List<String> url1 = page.getHtml().links("//ul[@class='version_list']").regex("http://apk\\.91\\.com/Soft/Android.*").all();
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
			// end
			
//			//获取页面信息
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
//			String nameString=page.getHtml().xpath("//div[@class='s_title clearfix']/h1/text()").toString();			
//				appName =nameString;
//				
//			appDetailUrl = page.getUrl().toString();
//			
//			appDownloadUrl = page.getHtml().xpath("//div[@class='fr s_btn_box clearfix']/a/@href").toString();
//			
//			String osPlatformString = page.getHtml().xpath("//ul[@class='s_info']/li[4]/text()").toString();
//			if(osPlatformString != null)
//				osPlatform = osPlatformString.substring(osPlatformString.indexOf("：")+1,osPlatformString.length());
//			
//			String versionString = page.getHtml().xpath("//ul[@class='s_info']/li[1]/text()").toString();
//			if(versionString != null)
//				appVersion = versionString.substring(versionString.indexOf("：")+1,versionString.length());
//			
//			String sizeString = page.getHtml().xpath("//ul[@class='s_info']/li[3]/text()").toString();
//			if(sizeString != null)
//				appSize = sizeString.substring(sizeString.indexOf("：")+1,sizeString.length());
//			
//			String updatedateString = page.getHtml().xpath("//ul[@class='s_info']/li[5]/text()").toString();
//			if(updatedateString != null)
//				appUpdateDate = updatedateString.substring(updatedateString.indexOf("：")+1,updatedateString.length());
//			
//			String typeString = "apk";
//				appType =typeString;
//			
//			String venderString =page.getHtml().xpath("//ul[@class='s_info']/li[6]/text()").toString();
//			if(venderString != null)
//				appvender = venderString.substring(venderString.indexOf("：")+1,venderString.length());
//			
//			String DownloadedTimeString = page.getHtml().xpath("//ul[@class='s_info']/li[2]/text()").toString();
//			if(DownloadedTimeString != null)
//				appDownloadedTime = DownloadedTimeString.substring(DownloadedTimeString.indexOf("：")+1,DownloadedTimeString.length());		
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
			return Apk91_Detail.getApkDetail(page);
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
