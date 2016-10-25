package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Bote_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 伯特手机市场
 * 网站主页：http://www.shouji.com/index
 * Aawap #540
 * @author lisheng
 */


public class Bote implements PageProcessor{

	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Bote.class);

	public Apk process(Page page) {
	
		if("http://www.shouji.com/index".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.shouji.com/apps-0-latest_1");
			page.addTargetRequest("http://www.shouji.com/games-0-latest_1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.shouji.com/games-0-latest_\\d+").match()
				||page.getHtml().links().regex("http://www.shouji.com/apps-0-latest_\\d+").match())
		{
			List<String> apps=page.getHtml().links("//li[@class='hbox']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='r-page']").all();
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
		if(page.getUrl().regex("http://www\\.shouji\\.com/download-\\d+\\.html").match())
		{
			
			Apk apk = Bote_Detail.getApkDetail(page);
			
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
