package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk92_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓网 http://www.92apk.com/
 * @id：401
 * @author lisheng
 */


public class Apk92 implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.92apk.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.92apk.com/game/");
			page.addTargetRequest("http://www.92apk.com/soft/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.92apk\\.com/game/.*").match()
				||page.getHtml().links().regex("http://www\\.92apk\\.com/soft/.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//div[@class='childcat']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//div[@class='list-wrapper']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='pages']/a/@href").all();
	 		apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www\\.92apk\\.com/apk/.*").match())
		{
			
			Apk apk = Apk92_Detail.getApkDetail(page);
			
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
