package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Muzhigame_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 拇指游玩,网站主页为:http://www.muzhigame.com/games/list_3_44_0_0_0.html
 * 渠道编号为:329
 * 
 */


public class Muzhigame implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getUrl().regex("http://www\\.muzhigame\\.com/games/list_3_44_0_0_0\\.html.*").match())
	{
		//System.out.println("hello wolrd");
 		List<String> urlList=page.getHtml().xpath("//div[@id='game-lise-border']/ul/li/div/span/a/@href").all();
 		List<String> urlList2=page.getHtml().xpath("//div[@class='huanye']/ul/li/a/@href").all();
 		urlList.addAll(urlList2);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		if(page.getUrl().regex("http://www\\.muzhigame\\.com/games/\\d*/").match())
		{
			
			Apk apk = Muzhigame_Detail.getApkDetail(page);
			
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
