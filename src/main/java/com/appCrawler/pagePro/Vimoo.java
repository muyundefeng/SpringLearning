package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anfone_Detail;
import com.appCrawler.pagePro.apkDetails.Vimoo_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #178 vimoo
 * 
 * http://www.vimoo.cn/lists/l22_1/
 * @author DMT
 *
 */
public class Vimoo implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Vimoo.class);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		logger.info("call in Anfone.process()" + page.getUrl());
		//index page		http://www.vimoo.cn/search.asp?keys=%D3%A6%D3%C3&search.x=0&search.y=0		
		if(page.getUrl().regex("http://www\\.vimoo\\.cn/search\\.asp.*" ).match() ){
			//app的具体介绍页面								http://www.anfone.com/soft/54475.html			
			List<String> url1 = page.getHtml().links("//div[@class='newstitlelistbox']").regex("http://www\\.vimoo\\.cn/contents/.*").all();
			
			//remove the duplicate urls in list
			HashSet<String> urlSet = new HashSet<String>(url1);
			
			//add the urls to page
			Iterator<String> it = urlSet.iterator();
			while(it.hasNext()){
				page.addTargetRequest(it.next());
				
			}

		}
		
		//the app detail page
		else if(page.getUrl().regex("http://www\\.vimoo\\.cn/contents/.*").match()){

			
			return Vimoo_Detail.getApkDetail(page);
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
