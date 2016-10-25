package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Wei2008_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 维维软件园
 * 网站主页：http://www.wei2008.com/sort/486_1.html
 * Aawap #577
 * @author lisheng
 */


public class Wei2008 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Wei2008.class);

	public Apk process(Page page) {
	
		if("http://www.wei2008.com/sort/486_1.html".equals(page.getUrl().toString()))
		{
			List<String> categoryList=page.getHtml().links("//div[@id='catalog']").all();
			page.addTargetRequest("http://www.wei2008.com/sort/493_1.html");
			page.addTargetRequests(categoryList);
			//page.addTargetRequest("http://www.wei2008.com/sort/494_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.wei2008\\.com/sort/\\d+_\\d+\\.html").match())
		{
			
	 		List<String> apps=page.getHtml().links("//div[@class='baseinfo']").all();
	 		//List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		//apps.addAll(categoryList);
	 		//apps.addAll(pages);
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
		if(page.getUrl().regex("http://www\\.wei2008\\.com/downinfo/\\d+\\.html").match())
		{
			
			Apk apk = Wei2008_Detail.getApkDetail(page);
			
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
