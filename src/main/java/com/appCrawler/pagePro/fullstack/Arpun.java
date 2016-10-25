package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Arpun_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * ARP联盟  http://www.arpun.com/
 * Aawap #496
 * @author lisheng
 */


public class Arpun implements PageProcessor{

	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.arpun.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.arpun.com/sj/androidsoft/sort0303/list_303_1.html");
			page.addTargetRequest("http://www.arpun.com/sj/androidgame/sort0287/list_287_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.arpun\\.com/sj.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//ul[@id='xixigo']/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//table[@class='mainAreaTable mainListTable']").all();
	 		List<String> pages=page.getHtml().links("//table[@class='Tableborder5']").all();
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
		if(page.getUrl().regex("http://www\\.arpun\\.com/sj/androidgame/.*/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.arpun\\.com/sj/androidsoft/.*/\\d+\\.html").match()
				&&!page.getUrl().toString().contains("list"))
		{
			
			Apk apk = Arpun_Detail.getApkDetail(page);
			
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
