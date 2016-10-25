package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game7723_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 7723手机游戏  http://www.7723.cn/
 * Aawap #422
 * @author lisheng
 */


public class Game7723 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.7723.cn/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.7723.cn/android/new/0-0-0-1.htm");
			page.addTargetRequest("http://www.7723.cn/netgame/new/0-0-1.htm");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.7723\\.cn/android/new/0-0-0-\\d+\\.htm").match()
				||page.getHtml().links().regex("http://www\\.7723\\.cn/netgame/new/0-0-\\d+\\.htm").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='azl_l']/ul/li/div[@class='fl azll_tu']/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='al_page page']/a/@href").all();	 		
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
		if(page.getUrl().regex("http://www\\.7723\\.cn/game/\\d+\\.htm").match())
		{
			
			Apk apk =Game7723_Detail.getApkDetail(page);
			
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
