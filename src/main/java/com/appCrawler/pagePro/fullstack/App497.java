package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App497_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 电玩497
 * 网站主页：http://www.497.com/
 * Aawap #515
 * @author lisheng
 */


public class App497 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.497.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.497.com/az/");
			page.addTargetRequest("http://www.497.com/azsoft/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.497\\.com/azsoft/.*").match()
				||page.getHtml().links().regex("http://www.497.com/az/.*").match())
		{
	 		List<String> apps=page.getHtml().links("//div[@id='listcon']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pager']").all();
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
		if(page.getUrl().regex("http://www\\.497\\.com/app/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.497\\.com/appsoft/\\d+\\.html").match())
		{
			
			Apk apk = App497_Detail.getApkDetail(page);
			
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
