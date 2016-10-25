package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Veeqi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 维奇网
 * 网站主页：http://www.veeqi.com/
 * Aawap #546
 * @author lisheng
 */


public class Veeqi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.veeqi.com/android/7_1.htm".equals(page.getUrl().toString()))
		{
			for(int i=7;i<=17;i++)
			{
				String url="http://www.veeqi.com/android/"+i+"_1.htm";
				page.addTargetRequest(url);
			}
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.veeqi\\.com/android/\\d+_\\d+\\.htm").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='mod-img-list soft_hover android_down']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page_wrap']").all();
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
		if(page.getUrl().regex("http://www\\.veeqi\\.com/game/\\d+\\.html").match())
		{
			
			Apk apk = Veeqi_Detail.getApkDetail(page);
			
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
