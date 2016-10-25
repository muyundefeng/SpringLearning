package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Jz5u_Detail;
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
 * Jz5u绿色下载 http://www.jz5u.com/
 * Jz5u #273
 * @author tianlei
 */

public class Jz5u implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);

	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://www.jz5u.com/soft/search.*").match()){
	        List<String> urlList = page.getHtml().xpath("//div[@id='searchpageTitle']/span/b/a/@href").all();
            List<String> nextPage = page.getHtml().xpath("//td[@id='tablebody1']/a/@href").all();

			HashSet<String> urlSet = new HashSet<String>();
			urlSet.addAll(urlList);
			urlSet.addAll(nextPage);
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				String url = it.next();
                page.addTargetRequest(url);
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://www.jz5u.com/Soft/phone/Android/.*").match()){//http://www\\.ruan8\\.com/soft.*
			Apk apk = Jz5u_Detail.getApkDetail(page);	
			
			return apk;
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
