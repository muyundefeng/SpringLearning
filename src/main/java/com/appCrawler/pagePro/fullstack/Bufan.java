package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Bufan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 不凡游戏中心
 * http://games.bufan.com/
 * Aawap #466
 * @author lisheng
 */


public class Bufan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://games.bufan.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://games.bufan.com/list-2-0-0-0-1/");
			return null;
		}
	
		if(page.getUrl().regex("http://games\\.bufan\\.com/list-2-0-0-0-\\d+/").match())
		{
	 		List<String> appsAndpages=page.getHtml().links("//div[@class='this_1036 fl']").all();
	 		System.out.println(appsAndpages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(appsAndpages);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://games\\.bufan\\.com/info/\\d+\\.html").match())
		{
			
			Apk apk = Bufan_Detail.getApkDetail(page);
			
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
