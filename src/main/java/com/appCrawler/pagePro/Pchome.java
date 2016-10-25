package com.appCrawler.pagePro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.appCrawler.pagePro.apkDetails.Pchome_Detail;
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
 * Pchome[中国] http://www.pchome.net/
 * Pchome #56
 * @author tianlei
 */
public class Pchome implements PageProcessor{
	Site site = Site.me().setCharset("gbk").setRetryTimes(0).setSleepTime(3);
	private static int flag=1;
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {

		if(page.getUrl().regex("http://download\\.pchome\\.net/search.*").match()){
			
	        List<String> urlList = page.getHtml().xpath("//div[@class='dl-computerlist-middle overflow mg-top20']/a/@href").all();  
	        System.out.println(urlList);
			List<String> pages=page.getHtml().xpath("//div[@class='dl-pages fl']/a/@href").all();
			System.out.println(pages);
			System.out.println(pages.size());
			if(flag==1)
			{
				if(pages.size()>=2)
				{
					String url=pages.get(1);
					String url1=url.replace("2.html", "");
					System.out.println(url1);
					for(int i=1;i<=5;i++)
					{
						System.out.println(url1+i+".html");
						page.addTargetRequest(url1+i+".html");
					}
				}
				else{
					page.addTargetRequests(pages);
				}
				flag++;
			}
	        HashSet<String> urlSet = new HashSet<String>();
			urlSet.addAll(urlList);			
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				String url = it.next();
				String urltmp = url.replace("detail", "down");
				page.addTargetRequest(urltmp);
			}
		}
		
		//the app detail page
		if(page.getUrl().regex("http://download\\.pchome\\.net/mobile.*").match()
				||page.getUrl().regex("http://download\\.pchome\\.net/tv/tvgame.*").match()){//http://www\\.ruan8\\.com/soft.*
			Apk apk = Pchome_Detail.getApkDetail(page);			
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
