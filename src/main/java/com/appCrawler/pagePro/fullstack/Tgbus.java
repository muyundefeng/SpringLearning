package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tgbus_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 巴士手游网
 * 网站主页：http://shouji.tgbus.com/
 * @id 409
 * @author lisheng
 */


public class Tgbus implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Tgbus.class);

	public Apk process(Page page) {
	
		if("http://shouji.tgbus.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://shouji.tgbus.com/catalog/4_android_n_n/");
			return null;
		}
	
		if(page.getUrl().regex("http://shouji\\.tgbus\\.com/catalog/.*").match()
				||"http://shouji.tgbus.com/catalog/4_android_n_n/".equals(page.getUrl().toString()))
		{
	 		List<String> apps=page.getHtml().xpath("//div[@class='gameList cf']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@id='fenye']").all();
	 		apps.addAll(pages);
	 		page.addTargetRequests(apps);
	 		System.out.println(apps);
//			Set<String> cacheSet = Sets.newHashSet();
//			cacheSet.addAll(apps);
//			for(String url : cacheSet){
//				if(PageProUrlFilter.isUrlReasonable(url)){
//					page.addTargetRequest(url);
//				}
//			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://shouji\\.tgbus\\.com.*").match()
				&&!page.getUrl().toString().contains("catalog"))
		{
			
			Apk apk = Tgbus_Detail.getApkDetail(page);
			
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
