package com.appCrawler.pagePro;


import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Bufan_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 不凡游戏中心
 * http://games.bufan.com/
 * Aawap #466
 * @author lisheng
 */
public class Bufan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://games\\.bufan\\.com/search.*").match())
		{
			List<String> appsAndpages=page.getHtml().links("//div[@class='this_1036 fl']").all();
	 		System.out.println(appsAndpages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(appsAndpages);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		if(page.getUrl().regex("http://games\\.bufan\\.com/info/\\d+\\.html").match())
			{
				return Bufan_Detail.getApkDetail(page);
			}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
