package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Appcc_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 嘻嘻游戏库
 * 网站主页：http://www.wan.cc/list_7-0-1-0-0-0-1.html
 * Aawap #679
 * @author lisheng
 */


public class Appcc implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Appcc.class);

	public Apk process(Page page) {
	
		
	
		if(page.getHtml().links().regex("http://www.wan.cc/list_7-0-1-0-0-0-\\d+.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		//List<String> apps=page.getHtml().xpath("//div[@id='SoftListBox']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='game-lists']").all();
	 		
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
		if(page.getUrl().regex("http://www.wan.cc/soft/.+").match())
		{
			
			Apk apk = Appcc_Detail.getApkDetail(page);
			
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