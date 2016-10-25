package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Edown7_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 创意软件园
 * 网站主页：http://www.7edown.com/greensoft/new_Property_8.html
 * Aawap #576
 * @author lisheng
 */


public class Edown7 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Edown7.class);

	public Apk process(Page page) {
	
		if("http://www.7edown.com/greensoft/new_Property_8.html".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.7edown.com/softsort/soft_206_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.7edown\\.com/softsort/soft.*").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@id='Catalog']").all();
	 		List<String> apps=page.getHtml().links("//ul[@id='mynewlist']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pagination']").all();
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
		if(page.getUrl().regex("http://www\\.7edown\\.com/soft/down/soft_\\d+\\.html").match())
		{
			
			Apk apk = Edown7_Detail.getApkDetail(page);
			
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
