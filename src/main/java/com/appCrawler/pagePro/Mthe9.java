package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Mthe9_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #169
 * 手游世界http://www.shouyou520.com/
 * 可以通过伪造下载链接来构造下载链接
 * http://android.173sy.com/download/downloadapk.php?id=13425&s=1
 * 将id后的参数修改成相应的apk的id就好
 * @author Administrator
 *
 */
public class Mthe9 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		//if(page.getUrl().regex("http://android\\.173sy\\.com/games/search\\.php\\?key=*").match()){
		if(page.getUrl().regex("http://www\\.shouyou520\\.com/game\\.php\\?keyword=*").match()){
			//app的具体介绍页面											
			List<String> detailUrl = page.getHtml().links("//div[@class='gameList']").all();
			
//			/List<String> pageUrl = page.getHtml().links("//div[@class='pages']").all();
			
			//添加下一页url(翻页)
			List<String> pageUrl = page.getHtml().links("//div[@class='pages']").all();
			
			detailUrl.addAll(pageUrl);
			System.out.println("detailUrl size="+detailUrl.size());
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(detailUrl);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
			}
		}
		String appName = null;				//app名字
		appName = page.getHtml().xpath("//div[@class='gameName']/text()").toString();
		//appName = page.getHtml().xpath("//p[@class='dl_ititle']/text()").toString();
		//the app detail page
		if(appName != null){
//			Apk apk = null;
//			String rawId = page.getHtml().xpath("//p[@class='dbtn_download']/@onclick").get();
//			String id = null;
//			String appDetailUrl = null;			//具体页面url
//			String appDownloadUrl = null;		//app下载地址
//			String osPlatform = null ;			//运行平台
//			String appVersion = null;			//app版本
//			String appSize = null;				//app大小
//			String appUpdateDate = null;		//更新日期
//			String appType = null;				//下载的文件类型 apk？zip？rar？ipa?
//			String appvender = null;			//app开发者  APK这个类中尚未添加
//			String appDownloadedTime=null;		//app的下载次数
//			appName = page.getHtml().xpath("//div[@class='gameName']/text()").toString();	
//			
//			appDetailUrl = page.getUrl().toString();
//			if(page.getHtml().xpath("//div[@class='gameDown']/a[1]/text()").toString().equals("IOS用户下载"))
//			{
//				appDownloadUrl = page.getHtml().xpath("//div[@class='gameDown']/a[2]/@href").toString();
//			}
//			else
//			{
//				appDownloadUrl = page.getHtml().xpath("//div[@class='gameDown']/a[1]/@href").toString();
//			}
//			appSize = page.getHtml().xpath("//div[@class='gameDetail']/dl/dt[5]/text()").toString();
//			appVersion=page.getHtml().xpath("//div[@class='gameDetail']/dl/dd[1]/text()").toString();
//			appUpdateDate=page.getHtml().xpath("//div[@class='gameDetail']/dl/dt[6]/text()").toString();
//			//appDescription=page.getHtml().xpath("//div[@class='gameContent']/p[2]/text()").toString();
//			//appScrenshot=page.getHtml().xpath("//div[@class='tempWrap']/ul/li[1]/img/@src").all();
//		
//			if(appName != null && appDownloadUrl != null){
//				apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
//		}
			
			return Mthe9_Detail.getApkDetail(page);
		}
//
		return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
