package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Pk52_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 52pk
 * 网站主页：http://down.52pk.com/
 * @id 464
 * @author lisheng
 */


public class Pk52 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://down.52pk.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://down.52pk.com/android/games-0-1-0-1-0-0.html");
			page.addTargetRequest("http://down.52pk.com/android/games-0-0-0-1-0-0.html");
			return null;
		}
	
		if(page.getUrl().regex("http://down\\.52pk\\.com/android/games-0-0-0-\\d+-0-0\\.html").match()
				||page.getHtml().links().regex("http://down\\.52pk\\.com/android/games-0-1-0-\\d+-0-0\\.html").match())
		{
	 		List<String> apps=page.getHtml().links("//ul[@class='searchlist ztlist']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='listpage']").all();
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
		if(page.getUrl().regex("http://down\\.52pk\\.com/android/\\d+\\.shtml").match())
		{
			
			Apk apk = Pk52_Detail.getApkDetail(page);
			
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
