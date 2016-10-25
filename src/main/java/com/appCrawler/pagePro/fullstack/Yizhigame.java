package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yizhigame_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 一指手游
 * 网站主页：http://www.yizhigame.cn/portal.php
 * Aawap #628
 * @author lisheng
 */


public class Yizhigame implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Yizhigame.class);

	public Apk process(Page page) {
	
		if("http://www.yizhigame.cn/portal.php".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.151501.cn/genee_sydownload-sy.html?mod=symore&order=id&page=1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.151501.cn/genee_sydownload-sy.html\\?mod=symore&order=id&page=\\d+").match())
		{
		//	List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='filter-app-list cls']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pg']").all();
	 		//apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www.151501.cn/sygame-\\d+\\.html").match())
		{
			
			Apk apk = Yizhigame_Detail.getApkDetail(page);
			
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
