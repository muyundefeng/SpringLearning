package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.U66_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 66u手机频道  http://android.66u.com/
 * @id 430
 * @author lisheng
 */


public class U66 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://android.66u.com/".equals(page.getUrl().toString()))
		{
//			List<String> categorylist=page.getHtml().xpath("//div[@class='game-type-list']/a/@href").all();
//			page.addTargetRequests(categorylist);
			page.addTargetRequest("http://android.66u.com/azyx/");
			page.addTargetRequest("http://ku.66u.com/list-0-1-0-0-0-0-0-2.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://android\\.66u\\.com/azyx.*").match()
				||page.getUrl().regex("http://ku\\.66u\\.com/list-0-1-0-0-.*").match())
		{
	 		List<String> apps=page.getHtml().xpath("//div[@class='article-list-box']/ul/li/div[@class='article-list-c1']/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page bottom-page']").all();
	 		List<String> pages1=page.getHtml().links("//div[@class='page']").all();
	 		List<String> apps1=page.getHtml().links("//div[@class='mod-box-game']").all();
	 		//apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		apps.addAll(apps1);
	 		apps.addAll(pages1);
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
		if(page.getUrl().regex("http://android\\.66u\\.com/azyx/.*/\\d+_\\d+\\.html").match()
				&&!page.getUrl().toString().contains("list")
				||page.getUrl().regex("http://ku\\.66u\\.com/\\d+\\.html").match())
		{
			
			Apk apk = U66_Detail.getApkDetail(page);
			
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
