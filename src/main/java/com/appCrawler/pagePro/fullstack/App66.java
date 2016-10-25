package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App66_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 66软件园
 * 网站主页：http://www.pn66.com/
 * Aawap #520
 * @author lisheng
 */


public class App66 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.pn66.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.pn66.com/downlist/android_148/");
			page.addTargetRequest("http://www.pn66.com/downlist/android_149/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.pn66\\.com/downlist/android_149/.*").match()
				||page.getHtml().links().regex("http://www\\.pn66\\.com/downlist/android_148/.*").match())
		{
	 		List<String> apps=page.getHtml().links("//ul[@class='list-all j-hover-1']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='ylmf-page']").all();
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
		if(page.getUrl().regex("http://www\\.pn66\\.com/html/\\d+\\.html").match())
		{
			
			Apk apk = App66_Detail.getApkDetail(page);
			
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
