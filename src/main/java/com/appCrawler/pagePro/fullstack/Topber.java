package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Topber_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓市场
 * 网站主页：http://www.topber.com/
 * @id  465
 * @author lisheng
 */


public class Topber implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Topber.class);

	public Apk process(Page page) {
	
		if("http://www.topber.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.topber.com/soft/1.html");
			page.addTargetRequest("http://www.topber.com/game/1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.topber\\.com/soft/\\d+\\.html").match()
				||page.getHtml().links().regex("http://www\\.topber\\.com/game/\\d+\\.html").match())
		{
	 		List<String> apps=page.getHtml().links("//div[@class='applist']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pages']").all();
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
		if(page.getUrl().regex("http://www\\.topber\\.com/app/\\d+\\.html").match())
		{
			
			Apk apk = Topber_Detail.getApkDetail(page);
			
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
