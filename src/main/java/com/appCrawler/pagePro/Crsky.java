package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Crsky_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #196 非凡软件站
 * Crsky http://android.crsky.com/
 * @author DMT
 *
 */
public class Crsky implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Crsky.class);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page			http://sj.crsky.com/query.aspx?keyword=qq&type=sj&Page=2	
		if(page.getUrl().regex("http://sj\\.crsky\\.com/query\\.aspx\\?keyword=.*" ).match() ){
			//app的具体介绍页面		http://android\\.crsky\\.com/soft/.*								
			List<String> url1 = page.getHtml().links("//div[@class='list_line']").regex("http://android\\.crsky\\.com/soft/.*").all();

			
			List<String> url2 = page.getHtml().links("//div[@class='pagination']").regex("http://sj\\.crsky\\.com/query\\.aspx\\?keyword=.*").all();
			
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
		else if(page.getUrl().regex("http://android\\.crsky\\.com/soft/.*").match()){

			return Crsky_Detail.getApkDetail(page);
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
