package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Zuimei_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 最美应用
 * 网站主页：http://zuimeia.com/
 * Aawap #634
 * @author lisheng
 */


public class Zuimei implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Zuimei.class);

	public Apk process(Page page) {
	
		if("http://zuimeia.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://zuimeia.com/community/app/hot/?page=1&platform=2");
			return null;
		}
	
		if(page.getHtml().links().regex("http://zuimeia.com/community/app/hot/.*\\?page=\\d+&platform=2").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='community-tag']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='community-app-list-wrapper']").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page-direct']/a/@href").all();
	 		apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		System.out.println(pages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://zuimeia.com/community/app/\\d+.*").match())
		{
			
			Apk apk = Zuimei_Detail.getApkDetail(page);
			
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
