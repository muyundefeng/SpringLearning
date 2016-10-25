package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Androidmi_Detail;
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
 * Androidmi http://www.Androidmi.com/
 * Androidmi #204
 * @author DMT
 * 
 */
public class Androidmi implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	
	@Override
	public Apk process(Page page) {
		//index page		http://aawap.net/aawap/search?searchName=%E6%96%97%E5%9C%B0%E4%B8%BB		
		if(page.getUrl().regex("http://www\\.androidmi\\.com/index\\.php\\?m=search.*").match()){
			Page jsPage = null;
			try {
				jsPage = getDynamicPage(page.getUrl().toString());
				//System.out.println(jsPage.getHtml().toString());
			} catch (FailingHttpStatusCodeException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//app的具体介绍页面	http://aawap.net/aawap/detail/1926ce78-64e9-486d-85a0-744edefc9822
			List<String> url1 = jsPage.getHtml().xpath("//div/h5/a/@href").all();
		
			//添加下一页url(翻页)
			List<String> url2 = page.getHtml().links("//div[@class='c wrap']").regex("http://www\\.androidmi\\.com/index\\.php\\?m=search.*").all();
			//System.out.println(url2);
			url1.addAll(url2);
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
		
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				String url = it.next();
			
			//	if(p1.matcher(url).matches()){
					System.out.println(url);
					page.addTargetRequest(url);
					//System.out.println("match:" + url);
			//	}
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://www\\.androidmi\\.com/android-.*").match() &&
				 !page.getUrl().regex("http://www\\.androidmi\\.com/android-25.*").match()	){//http://www\\.ruan8\\.com/soft.*
			
			return Androidmi_Detail.getApkDetail(page);
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
	public static void main(String[]args)
	{
		try
		{
			System.out.println(getDynamicPage("http://www.baidu.com").getHtml().toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
