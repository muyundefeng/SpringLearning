package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Qmy9_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 梦游游戏  http://www.9qmy.com/
 * Aawap #230
 * @author DMT
 */


public class Qmy9 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if("http://www.9qmy.com/index.html".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.9qmy.com/gcategory_402881eb3a907a4c013a907e21060001_1.html");
			return null;
		}
		if("http://www.9qmy.com/gcategory_402881eb3a907a4c013a907e21060001_1.html".equals(page.getUrl().toString()))
		{
			List<String> categoryList=page.getHtml().xpath("//ul[@class='geme_list']/li/p/a/@href").all();
			for(String url:categoryList)
			{
				if(PageProUrlFilter.isUrlReasonable(url)
						&&!url.contains("downloadapk"))
				{
					page.addTargetRequest(url);
				}
			}
			//page.addTargetRequests(categoryList);
			page.addTargetRequest("http://www.9qmy.com/gcategory_402881eb3a907a4c013a907e21060001_1.html");
			return null;
		}
		if(page.getHtml().links().regex("http://www\\.9qmy\\.com/gcategory_.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='game_list_left']/div/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			apps.addAll(pages);
			System.out.println(apps);
			for(String url:apps)
			{
				if(PageProUrlFilter.isUrlReasonable(url)
						&&!url.contains("downloadapk"))
				{
					page.addTargetRequest(url);
				}
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.9qmy\\.com/gamedetail.*").match())
		{
			
			Apk apk = Qmy9_Detail.getApkDetail(page);
			
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
