package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Zhaichong_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 宅虫网
 * 网站主页：http://www.zhaichong.com/
 * Aawap #588
 * @author lisheng
 */


public class Zhaichong implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Zhaichong.class);

	public Apk process(Page page) {
	
		if("http://www.zhaichong.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://game.zhaichong.com/shouyou/index.html");
			//page.addTargetRequest("http://apk.3310.com/game/jsmx/list_103_1.html");
			return null;
		}
//	System.out.println(page.getUrl());
		if(page.getUrl().regex("http://game\\.zhaichong\\.com/shouyou/index.*").match())
		{//
			//System.out.println("hello");
			List<String> apps=page.getHtml().links("//div[@class='gxdq_cate']").all();
			List<String> pages=page.getHtml().links("//div[@class='pages']").all();
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
		if(page.getUrl().regex("http://game.zhaichong.com/shouyou/\\d+/\\d+\\.html").match())
		{
			
			Apk apk = Zhaichong_Detail.getApkDetail(page);
			
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
