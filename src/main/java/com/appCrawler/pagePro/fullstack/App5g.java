package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App5g_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 5g手游网
 * 网站主页：http://www.5g.com/
 * Aawap #518
 * @author lisheng
 */


public class App5g implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(App5g.class);

	public Apk process(Page page) {
	
		if("http://www.5g.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.5g.com/youxi/list.html?platform=Android&order=new");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.5g\\.com/youxi/list\\.html.*").match())
		{
	 		List<String> apps=page.getHtml().links("//div[@class='play_list_photoBoxB clearFix']").all();
	 		List<String> pages=page.getHtml().links("//div[@id='divPage']").all();
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
		if(page.getUrl().regex("http://www\\.5g\\.com/youxi/\\d+\\.html").match())
		{
			
			Apk apk = App5g_Detail.getApkDetail(page);
			
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
