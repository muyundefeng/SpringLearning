package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Znds_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 智能电视网
 * 网站主页：http://downso.znds.com/
 * Aawap #638
 * @author lisheng
 */


public class Znds implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://down.znds.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://down.znds.com/apk/tv/list_7_1.html");
			page.addTargetRequest("http://down.znds.com/apk/app/list_1_1.html");
			page.addTargetRequest("http://down.znds.com/apk/game/list_2_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://down.znds.com/apk/game/list_2_\\d+.html").match()
				||page.getHtml().links().regex("http://down.znds.com/apk/app/list_1_\\d+.html").match()
				||page.getHtml().links().regex("http://down.znds.com/apk/tv/list_7_\\d+.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='case2']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='dede_pages']").all();
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
		if(page.getUrl().regex("http://down.znds.com/apk/.*").match()
				&&!page.getUrl().toString().contains("list"))
		{
			
			Apk apk = Znds_Detail.getApkDetail(page);
			
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
