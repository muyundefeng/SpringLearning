package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Wwweoemarket_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 优亿市场 http://www.eoemarket.com
 * Wwweoemarket #193
 * @author DMT
 */
public class Wwweoemarket implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Wwweoemarket.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		//index page				http://www.eoemarket.com/search_.html?keyword=
		if(page.getUrl().regex("http://www\\.eoemarket\\.com/search_\\.html\\?keyword=.*" ).match() ){
			//app的具体介绍页面								http://www.anfone.com/soft/54475.html			
			List<String> url1 = page.getHtml().links("//div[@class='Rcontainer']").regex("http://www\\.eoemarket\\.com/soft/.+").all();
			List<String> url3 = page.getHtml().links("//div[@class='Rcontainer']").regex("http://www\\.eoemarket\\.com/game/.+").all();

			url1.addAll(url3);
			//添加下一页url(翻页)  
			List<String> url2 = page.getHtml().links("//div[@class='fenye']").regex("http://www\\.eoemarket\\.com/search_\\.html\\?keyword=.*").all();
			
			url1.addAll(url2);
			
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
				
			}

		}
		
		//the app detail page
		else if(page.getUrl().regex("http://www\\.eoemarket\\.com/soft/.+").match() 
				|| page.getUrl().regex("http://www\\.eoemarket\\.com/game/.+").match()){

			
			return Wwweoemarket_Detail.getApkDetail(page);
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
