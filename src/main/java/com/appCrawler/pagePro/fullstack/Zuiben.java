package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Zuiben_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 最笨下载
 * 网站主页：http://www.zuiben.com/
 * Aawap #461
 * @author lisheng
 */


public class Zuiben implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.zuiben.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.zuiben.com/a_soft/");
			for(int i=1;i<=17;i++)
			{
				page.addTargetRequest("http://www.zuiben.com/a_game/"+i+"/");
			}
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.zuiben\\.com/a_soft/.*").match()
				||page.getUrl().regex("http://www\\.zuiben\\.com/a_game/.*").match())
		{
	 		List<String> apps=page.getHtml().xpath("//ul[@class='list-all j-hover-1']/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page-num']").all();
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
		if(page.getUrl().regex("http://www\\.zuiben\\.com/a_game/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.zuiben\\.com/a_soft/\\d+\\.html").match())
		{
			
			Apk apk = Zuiben_Detail.getApkDetail(page);
			
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
