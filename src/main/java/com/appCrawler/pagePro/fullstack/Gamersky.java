package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





import com.appCrawler.pagePro.apkDetails.Gamersky_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/*
 * 只爬取索引页和详情页*/
import us.codecraft.xsoup.ElementOperator.Regex;

public class Gamersky implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Gamersky.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://shouyou.gamersky.com/")){
			page.addTargetRequest("http://shouyou.gamersky.com/ku/1-0-0-00_1.html");
		}
		if(page.getUrl().regex("http://shouyou\\.gamersky\\.com/ku/1-0-0-00_\\d+\\.html").match()){
			
		List<String> urls =page.getHtml().links("//ul[@class='pictxt']").regex("http://shouyou\\.gamersky\\.com/ku/\\d+\\.shtml").all() ;
		List<String> pageList = page.getHtml().links("//span[@class='pagecss']").regex("http://shouyou\\.gamersky\\.com/ku/1-0-0-00_\\d+\\.html").all();
 		urls.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urls);
		for (String temp : cacheSet) {
			if(PageProUrlFilter.isUrlReasonable(temp))				
				page.addTargetRequest(temp);
		}
		
		}
		//提取页面信息
		if(page.getUrl().regex("http://shouyou\\.gamersky\\.com/ku/\\d+\\.shtml").match()){
			
			
			Apk apk = Gamersky_Detail.getApkDetail(page);
			
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
