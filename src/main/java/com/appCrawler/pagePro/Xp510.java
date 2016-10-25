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
import com.appCrawler.pagePro.apkDetails.Xp510_Detail;
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
 * Xp手机之家  http://www.xp510.com/
 * Xp510 #274
 * @author tianlei
 */

public class Xp510 implements PageProcessor{
	Site site = Site.me().setCharset("UTF-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		String nextUrl = null;
		String nextPage = null;
		if(page.getUrl().regex("http://www.xp510.com/shouji/index.php.m=search.*").match()){
	        List<String> urlList = page.getHtml().xpath("//div[@id='wrap']/ul//span[2]/a/@href").all();
            nextUrl = page.getHtml().xpath("//div[@class='page']").toString();
            System.out.println(nextUrl);
            if(nextUrl != null){
            nextPage = getNextUrl(nextUrl).replace("amp;","");
            }
            System.out.println(nextPage);
			HashSet<String> urlSet = new HashSet<String>();
			urlSet.addAll(urlList);
			if(nextPage != null){
			urlSet.add(nextPage);
			}
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				String url = it.next();
                page.addTargetRequest(url);
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://www.xp510.com/shouji/android/.*").match()){//http://www\\.ruan8\\.com/soft.*
			Apk apk = Xp510_Detail.getApkDetail(page);			
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
	
	private static String getNextUrl(String str){
    	String tmp=null;
		String regex="<a href=\"([^\"]+)\" class=\"a1\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp=matcher.group(1).toString();
        	
        }
    	return tmp;   	
    }

}
