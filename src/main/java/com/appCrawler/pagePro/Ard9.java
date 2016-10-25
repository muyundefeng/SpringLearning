package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Ard9_Detail;
import com.appCrawler.utils.MyNicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
/**
 * ard9  http://www.ard9.com/soft/
 * Ard9 #215
 * @author DMT
 */
public class Ard9 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	private static int count=1;
	@Override
	public Apk process(Page page) {
		
		//获取搜索的url地址
		if(count==1)
		{
			//System.out.println("he");
			List<String> pages=page.getHtml().xpath("//ul[@class='pagelist']/table/tbody/tr/td/a/@href").all();
			if(pages.size()<=4)
			{
				page.addTargetRequest(page.getUrl().toString());
				page.addTargetRequests(pages);
			}
			else{
				for(int i=0;i<=4;i++)
				{
					page.addTargetRequest(page.getUrl().toString());
					//System.out.println(pages.get(i));
					page.addTargetRequest(pages.get(i));
				}
			}
			count++;
			//return null;
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(page.getUrl().regex("http://www\\.ard9\\.com/plus/search\\.php.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apkList=page.getHtml().xpath("//div[@class='resultlist']/ul/li/h3/a/@href").all();
			page.addTargetRequests(apkList);
			//List<String> pages=page.getHtml().xpath("//ul[@class='pagelist']/table/tbody/tr/td/a/@href").all();
		    //page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.ard9\\.com/.*/[0-9]+\\.html").match()
				&&!page.getUrl().regex("http://www\\.ard9\\.com/soft/list_2_\\d+\\.html").match()
				&&!page.getUrl().regex("http://www\\.ard9\\.com/game/list_3_\\d+\\.html").match()
				&&!page.getUrl().toString().contains("news")){
			return Ard9_Detail.getApkDetail(page);
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
