package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Orsoon_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 未来软件园
 * 网站主页：http://www.orsoon.com/android/
 * Aawap #478
 * @author lisheng
 */


public class Orsoon implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.orsoon.com/android/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.orsoon.com/azyx/dzmx/");
			page.addTargetRequest("http://www.orsoon.com/azrj/sjlt/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.orsoon\\.com/azrj.*").match()
				||page.getHtml().links().regex("http://www\\.orsoon\\.com/azyx.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//div[@class='f_1 a_type2 fc']/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='list_mac1 cf']").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.orsoon\\.com/android/\\d+\\.html").match())
		{
			
			Apk apk = Orsoon_Detail.getApkDetail(page);
			
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
