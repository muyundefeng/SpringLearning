package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Gionee_Detail;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 金立游戏中心（单机）  http://game.gionee.com/
 * Gionee #350
 * @author tianlei
 */
public class Gionee implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		if(page.getUrl().regex("http://game.gionee.com/Front/Search/.*").match()){
			List<String> urlList = page.getHtml().xpath("//ul[@class='search_list']//a[1]/@href").all();
			List<String> nextPage = page.getHtml().xpath("div[@class='pagination']/a/@href").all();
			cacheSet.addAll(urlList);
			cacheSet.addAll(nextPage);
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		
		if(page.getUrl().regex("http://game.gionee.com/Front/Index/.*").match()){			
			Apk apk = Gionee_Detail.getApkDetail(page);	
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
