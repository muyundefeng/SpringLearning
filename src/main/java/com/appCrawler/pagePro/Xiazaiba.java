package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Xiazaiba_Detail_ForSearch;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #198 下载吧
 * Xiazaiba http://a.xiazaiba.com/
 * 通过这个url可以找到搜索结果：
 * http://so.xiazaiba.com/route.php?ct=search_new&ac=auto2&is_ajax=1&q=qq
 * &jsonpCallback=jQuery172034958995692431927_1439520086004&_=1439520207463
 * 
 * @author DMT
 *
 */
public class Xiazaiba implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Xiazaiba.class);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page		http://so.xiazaiba.com/route.php?ct=search_new&q=qq&phone=2
		if(page.getUrl().regex("http://so.xiazaiba.com/route\\.php\\?ct=search_new.*" ).match() ){
	
			
			
			List<String> url1 = page.getHtml().links("//ul[@class='cur-cat-list']").regex("http://www\\.xiazaiba\\.com/html/.*").all();

			
			List<String> url2 = page.getHtml().links("//div[@class='page-num']").regex("http://so.xiazaiba.com/route\\.php\\?ct=search_new.*").all();
			
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
		else if(page.getUrl().regex("http://www\\.xiazaiba\\.com/html/.*").match()){

			return Xiazaiba_Detail_ForSearch.getApkDetail(page);
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
