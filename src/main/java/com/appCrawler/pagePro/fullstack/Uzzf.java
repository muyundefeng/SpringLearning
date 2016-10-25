package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Uzzf_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 东坡下载
 * 网站主页：http://www.uzzf.com/
 * Aawap #495
 * @author lisheng
 */


public class Uzzf implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.uzzf.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.uzzf.com/apk/r_138_1.html");
			page.addTargetRequest("http://www.uzzf.com/apk/r_146_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.uzzf\\.com/apk/.*").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='types-list cls']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='filter-app-list cls']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='tsp_nav']").all();
	 		apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www\\.uzzf\\.com/apk/\\d+\\.html").match())
		{
			
			Apk apk =Uzzf_Detail.getApkDetail(page);
			
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
