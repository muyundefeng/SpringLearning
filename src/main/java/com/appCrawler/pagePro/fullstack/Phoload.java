package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Phoload_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * phoload
 * 网站主页：http://www.phoload.com/
 * Aawap #653
 * @author lisheng
 */


public class Phoload implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.phoload.com/".equals(page.getUrl().toString()))
		{
			List<String> categorys = page.getHtml().links("//div[@id='sidebar']").all();
			page.addTargetRequests(categorys);
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.phoload.com/category/.+").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='subcat']").all();
	 		List<String> apps=page.getHtml().links("//div[@class='applistresult']").all();
	 		//List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www.phoload.com/software/.+").match())
		{
			
			Apk apk = Phoload_Detail.getApkDetail(page);
			
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
