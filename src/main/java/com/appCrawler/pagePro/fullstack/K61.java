package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.K61_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 61k手游网  http://www.61k.com/game/0-1-0-0-1-0.html
 * Aawap #404
 * @author lisheng
 */


public class K61 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.61k.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.61k.com/game/0-1-0-0-1-0.html");
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.61k\\.com/game/\\d+-1-0-0-1-0\\.html").match())
		{
	 		List<String> apps=page.getHtml().xpath("//ul[@class='lineList']/li/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='pageBox']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.61k\\.com/.*").match()
				&&!page.getUrl().regex("http://www\\.61k\\.com/game/\\d+-1-0-0-1-0\\.html").match())
		{
			
			Apk apk = K61_Detail.getApkDetail(page);
			
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
