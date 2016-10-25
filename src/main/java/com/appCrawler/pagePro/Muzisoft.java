package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.functors.ForClosure;

import com.appCrawler.pagePro.apkDetails.Muzisoft_Detail;
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
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 木子安卓http://www.muzisoft.com/
 * Muzisoft #217
 * @author DMT
 * @modiufy author lisheng
 */
public class Muzisoft implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		System.out.println(SinglePageDownloader.getHtml(page.getUrl().toString()));
		if(page.getUrl().regex("http://www\\.muzisoft\\.com/plus/search.*").match())
		{
			if(flag==1)
			{
				List<String> pages=page.getHtml().xpath("//div[@class='page']/table/tbody/tr/td/a/@href").all();
				if(pages.size()>4)
				{
					for(int i=0;i<=4;i++)
					{
						page.addTargetRequest(pages.get(i));
					}
				}
				else{
					page.addTargetRequests(pages);
				}
				flag++;
			}
			List<String> apkList=page.getHtml().xpath("//div[@class='newslist']/div/a/@href").all();
			for(String url:apkList)
			{
				if(PageProUrlFilter.isUrlReasonable(url))
				{
					page.addTargetRequest(url);
				}
			}
		}
//		{
//			Page jsPage = null;
//			try {
//				jsPage = getDynamicPage(page.getUrl().toString());
//				System.out.println(jsPage.getHtml().toString());
//			} catch (FailingHttpStatusCodeException e) {
//				e.printStackTrace();
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			//app的具体介绍页面	http://aawap.net/aawap/detail/1926ce78-64e9-486d-85a0-744edefc9822
//			List<String> url1 = jsPage.getHtml().xpath("//div[@class='searchitem']/dl/dt/a/@href").all();
//
//			//添加下一页url(翻页)
//			//List<String> url2 = page.getHtml().links("//div[@class='mdpage']").regex("http://www\\.ruan8\\.com/search\\.php\\?.*").all();
//			//url1.addAll(url2);
//			
//			//remove the duplicate urls in list
//			HashSet<String> urlSet = new HashSet<String>(url1);
////			String regex1 = "(?=(http://www.muzisoft.com/soft/.*.html)).*$";//"[\\S]+['html\\?ch=']+\\d$";
////			Pattern p1 = Pattern.compile(regex1);
////			Matcher m;
//			//add the urls to page
//			Iterator<String> it = urlSet.iterator();
//			while(it.hasNext()){
//				String url = it.next();
//			//	if(p1.matcher(url).matches()){
//					page.addTargetRequest(url);
//					//System.out.println("match:" + url);
//			//	}
//			}
//		}
//		
		//the app detail page
		if(page.getUrl().regex("http://www\\.muzisoft\\.com/soft/\\d+\\.html").match()){//http://www\\.ruan8\\.com/soft.*
			
			return Muzisoft_Detail.getApkDetail(page);
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
