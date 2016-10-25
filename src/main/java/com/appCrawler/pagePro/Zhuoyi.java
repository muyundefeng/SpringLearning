package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Zhuoyi_Detail;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

/**
 * 卓易市场  http://www.zhuoyi.com/
 * Zhuoyi #311
 *伪造搜索接口url地址，其搜索关键字存放在cookies忠厚
 * @modify lisheng
 * @author tianlei
 */
public class Zhuoyi implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	public static int flag=1;
	public static String keyWord;
	public static String cookie;
	public static Map<String, String> map;
	
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://android.tompda.com/.*").match()
				||page.getUrl().regex("http://www.zhuoyi.com/appSearch.*").match())
		{
			if(flag==1)
			{
			//if(page.getUrl().regex("http://android.tompda.com/.*").match())
				keyWord=page.getUrl().toString().split("keyword=")[1];
				System.out.println(keyWord);
				flag++;
			}
			cookie="rankId=99999999; Hm_lvt_c13eee2f775998639a627c51cd2620fe=1461898338,1464273705,1464311672; Hm_lpvt_c13eee2f775998639a627c51cd2620fe=1464311952; Hm_lvt_5423b1ded4398354075d6c88d7586776=1461898338,1464273706,1464311672; Hm_lpvt_5423b1ded4398354075d6c88d7586776=1464311952; CNZZDATA1253159431=2082843098-1461896327-%7C1464311673; searchKeywords="+keyWord;
			map=new HashMap<String,String>();
			map.put("Cookie", cookie);
			String html=SinglePageDownloader.getHtml("http://www.zhuoyi.com/appSearch-1.html","get",map);
			Html html1=Html.create(html);
			List<String> apps=html1.xpath("//div[@class='searchApp']/div[1]/a/@href").all();
			List<String> pages=html1.links("//div[@class='pageDiv']").all();
			apps.addAll(pages);
			System.out.println(apps);
			for(String str:apps)
			{
				page.addTargetRequest("http://www.zhuoyi.com"+str);
			}
			for(int i=1;i<=5;i++)
			{
				page.addTargetRequest("http://www.zhuoyi.com/appSearch-"+i+".html");
			}
			
		}
		if(page.getUrl().regex("http://www.zhuoyi.com/appDetail.*").match())
		{
			Apk apk =null;
			String appName=page.getHtml().xpath("//div[@class='detailName']/h1/text()").toString();
			String raw=page.getHtml().xpath("//div[@class='detailVersion']/text()").toString();
			String appVersion=raw.split("版本：")[1];
			String appSize=raw.split("版本：")[0].replace("软件大小：","");
			String raw1=page.getHtml().xpath("//div[@class='detailTime']/text()").toString();
			String osPlatform=raw1.split("支持系统：")[1];
			String appUpdateDate=raw1.split("支持系统：")[0].replace("更新时间：","");
			Html html=Html.create(SinglePageDownloader.getHtml(page.getUrl().toString()));
			String appDownloadUrl=html.xpath("//div[@class='detailInfo']/div[@class='appDownload']/ul/li[2]/").toString();
			System.out.println(appDownloadUrl);
			appDownloadUrl=appDownloadUrl.split("http")[1].split(".apk")[0];
			appDownloadUrl="http"+appDownloadUrl+".apk";
			List<String> pics=page.getHtml().xpath("//div[@id='getAppImage']/ul/li/img/@src").all();
			String appDes=page.getHtml().xpath("//div[@class='detailContent ps-container']/span/text()").toString();
			if(appName != null && appDownloadUrl != null){
				apk = new Apk(appName,page.getUrl().toString(),appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,"APK",null);
				apk.setAppDownloadTimes(null);
				apk.setAppVenderName(null);
				apk.setAppTag(null);
				apk.setAppScreenshot(pics);
				apk.setAppDescription(appDes);
				apk.setAppCategory(null);
				
			}
			return apk;
			
			
		}
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static Page getDynamicPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		MyNicelyResynchronizingAjaxController ajaxController = new MyNicelyResynchronizingAjaxController();
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
		//HtmlUnitDriver
		//设置webClient的相关参数
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(ajaxController);
		webClient.getOptions().setTimeout(35000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		//webClient.setAlertHandler( new CollectingAlertHandler(alertHandler));//将JavaScript中alert标签产生的数据保存在一个链表中
		String targetUrl = url;
		//模拟浏览器打开一个目标网址
		HtmlPage rootPage= webClient.getPage(targetUrl);
		//System.out.println(rootPage.asXml());
		Page page = new Page();
		page.setRawText(rootPage.asXml());
		page.setUrl(new PlainText(url));
		page.setRequest(new Request(url));
		return page;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
