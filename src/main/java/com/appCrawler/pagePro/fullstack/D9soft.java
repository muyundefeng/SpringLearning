package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.D9soft_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 第九软件园
 * http://www.d9soft.com/
 * Aawap #469
 * @author lisheng
 */


public class D9soft implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.d9soft.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.d9soft.com/android/game/");
			page.addTargetRequest("http://www.d9soft.com/android/app/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.d9soft\\.com/android/app.*").match()
				||page.getHtml().links().regex("http://www\\.d9soft\\.com/android/game.*").match())
		{
			List<String> categoryList=page.getHtml().links("//div[@class='right-ul-bd']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='app-list clearfix']").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.d9soft\\.com/android/game/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.d9soft\\.com/android/app/\\d+\\.html").match())
		{
			
			Apk apk = D9soft_Detail.getApkDetail(page);
			
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
