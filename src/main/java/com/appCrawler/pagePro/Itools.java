package com.appCrawler.pagePro;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Itools_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 安丰市场 www.anfone.com
 * Anfone #87
 * (1)搜索结果的翻页url中p=0是第1页
 * @author DMT
 */
public class Itools implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(Itools.class);
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		//index page		http://android.itools.cn/search		
		if(page.getUrl().regex("http://android\\.itools\\.cn/search/.*" ).match() ){
			//app的具体介绍页面									
			List<String> url1 = page.getHtml().links("//ul[@class='ios_app_list']").regex("http://android\\.itools\\.cn/details.*").all();

			 
			List<String> url2 = page.getHtml().links("//div[@class='pages clearfix']").regex("http://android\\.itools\\.cn/search/.*").all();
			
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
		else if(page.getUrl().regex("http://android\\.itools\\.cn/details.*").match()){

			return Itools_Detail.getApkDetail(page);
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
