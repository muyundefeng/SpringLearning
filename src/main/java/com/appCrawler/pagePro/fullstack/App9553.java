package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App9553_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 9553软件园
 * 网站主页：http://www.9553.com/
 * Aawap #493
 * @author lisheng
 */


public class App9553 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(App9553.class);

	public Apk process(Page page) {
	
		if("http://www.9553.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.9553.com/android/258_1.htm");
			page.addTargetRequest("http://www.9553.com/android/263_1.htm");
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.9553\\.com/android/.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> categoryList=page.getHtml().xpath("//div[@class='tags-ht']/dl[1]/dd/ul/li/a/@href").all();
	 		System.out.println(categoryList);
			List<String> apps=page.getHtml().xpath("//ul[@class='list-bd']/li/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page-bd']/a/@href").all();
	 		apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.9553\\.com/android/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.9553\\.com/soft/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.9553\\.com/game/\\d+\\.htm").match())
		{
			
			Apk apk = App9553_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
