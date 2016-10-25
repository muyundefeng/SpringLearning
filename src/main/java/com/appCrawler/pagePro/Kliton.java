package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Kliton_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #179 Kliton
 * 
 * http://www.kliton.com/download/l3_1/index.html
 * @author DMT
 *
 */
public class Kliton implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Kliton.class);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		logger.info("call in Anfone.process()" + page.getUrl());
//		System.out.println(page.getHtml().toString());
		//index page		http://www.kliton.com/search.asp?keys=%B6%B7%B5%D8%D6%F7&search.x=0&search.y=0
		if(page.getUrl().regex("http://www\\.kliton\\.com/search\\.asp.*" ).match() ){
			//app的具体介绍页面											
			List<String> url1 = page.getHtml().links("//div[@class='newstitlelistbox']").regex("http://www\\.kliton\\.com/.*").all();
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
				
			}

		}
		
		//the app detail page http://www.kliton.com/download/c145/index.html
		else if(page.getUrl().regex("http://www\\.kliton\\.com/download/.*").match()){
			
			return Kliton_Detail.getApkDetail(page);
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
