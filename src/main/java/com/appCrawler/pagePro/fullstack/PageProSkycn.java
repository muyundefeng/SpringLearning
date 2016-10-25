package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.PageProSkycn_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 天空下载  http://www.skycn.com/soft/17.html
 * Aawap #165
 * @author DMT
 */


public class PageProSkycn implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getHtml().links().regex("http://www\\.skycn\\.com/soft/.*").match())
	{
		
 		//List<String> urlList=new ArrayList<String>();
 		List<String> urlList2=page.getHtml().xpath("//div[@class='span4 g-left']/ul/li/a/@href").all();
 		List<String> urlList3=page.getHtml().xpath("//div[@class='tsp_nav']/a/@href").all();
 		//urlList.addAll(urlList2);
 		urlList2.addAll(urlList3);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList2);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.skycn\\.com/soft/appid/.*").match())
		{
			
			Apk apk = PageProSkycn_Detail.getApkDetail(page);
			
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
