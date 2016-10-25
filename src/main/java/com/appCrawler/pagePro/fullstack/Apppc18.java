package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Apppc18_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * pc18
 * 网站主页：http://www.pc18.com/
 * Aawap #672
 * @author lisheng
 */


public class Apppc18 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apppc18.class);

	public Apk process(Page page) {
	
		if("http://www.pc18.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.pc18.com/android/list-15-1.html");
			page.addTargetRequest("http://www.pc18.com/android/list-20-1.html");
			return null;
		}
		//if()
		if(page.getHtml().links().regex("http://www.pc18.com/android/list.*").match())
		{
			List<String> categoryList=page.getHtml().links("//dl[@class='dl-link fix']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='ul-game-list fix ul-nobr3 MENU ul-nobr3-g']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pages']").all();
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
		if(page.getUrl().regex("http://www.pc18.com/android/\\d+\\.html").match())
		{
			
			Apk apk = Apppc18_Detail.getApkDetail(page);
			
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
