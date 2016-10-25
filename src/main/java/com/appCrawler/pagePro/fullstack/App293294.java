package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App293294_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓市场
 * 网站主页：http://www.293294.com/
 * Aawap #554
 * @author lisheng
 */


public class App293294 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(App293294.class);

	public Apk process(Page page) {
	
		if("http://www.293294.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.293294.com/app/soft/1.html");
			page.addTargetRequest("http://www.293294.com/app/game/1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.293294\\.com/app/soft/\\d+\\.html").match()
				||page.getHtml().links().regex("http://www\\.293294\\.com/app/game/\\d+\\.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='app-list boutique-cnt']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pagebar']").all();
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
		if(page.getUrl().regex("http://www\\.293294\\.com/app/\\d+\\.html").match())
		{
			
			Apk apk = App293294_Detail.getApkDetail(page);
			
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
