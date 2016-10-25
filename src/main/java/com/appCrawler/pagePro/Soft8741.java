package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Soft8741_Detail2;
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
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 安卓地带  http://www.8471.com/ruanjian
 * Soft8471 #210
 * @author DMT
 */

public class Soft8741 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	
	@Override
	public Apk process(Page page) {
		System.out.println(page.getHtml().toString());
		//index page		http://aawap.net/aawap/search?searchName=%E6%96%97%E5%9C%B0%E4%B8%BB		
		if(page.getUrl().regex("http://www\\.8471\\.com/plus/search\\.php\\?keyword=.*").match()){
			{
				System.out.println(page.getHtml());
				List<String> apkList=page.getHtml().xpath("//dd[@id='shd']/b/a/@href").all();
				List<String> pageList=page.getHtml().xpath("//dd[@id='pg']/a/@href").all();
				apkList.addAll(pageList);
				for(String str:apkList)
				{
					if(PageProUrlFilter.isUrlReasonable(str))
					{
						page.addTargetRequest(str);
					}
				}
			}
			
		//the app detail page
		if(page.getUrl().regex("http://www\\.8471\\.com/\\d+").match()){
			return Soft8741_Detail2.getApkDetail(page);
		}
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

//	public static Page getDynamicPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
//		MyNicelyResynchronizingAjaxController ajaxController = new MyNicelyResynchronizingAjaxController();
//		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
//		//HtmlUnitDriver
//		//设置webClient的相关参数
//		webClient.getOptions().setJavaScriptEnabled(true);
//		webClient.getOptions().setCssEnabled(false);
//		webClient.setAjaxController(ajaxController);
//		webClient.getOptions().setTimeout(35000);
//		webClient.getOptions().setThrowExceptionOnScriptError(false);
//		//webClient.setAlertHandler( new CollectingAlertHandler(alertHandler));//将JavaScript中alert标签产生的数据保存在一个链表中
//		String targetUrl = url;
//		//模拟浏览器打开一个目标网址
//		HtmlPage rootPage= webClient.getPage(targetUrl);
//		//System.out.println(rootPage.asXml());
//		Page page = new Page();
//		page.setRawText(rootPage.asXml());
//		page.setUrl(new PlainText(url));
//		page.setRequest(new Request(url));
//		return page;
//	}
//	@Override
//	public List<Apk> processMulti(Page page) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	private static String getUrl(String str){
//    	String tmp=null;
//		String regex="http://www\\.8471\\.com/([^\"]*)";
//		Pattern pattern = Pattern.compile(regex);  
//        Matcher matcher = pattern.matcher(str); 
// 
//        while(matcher.find()){
//        	
//        	tmp=matcher.group(1).toString();
//        	
//        }
//    	return tmp;   	
//    }
//	

}
