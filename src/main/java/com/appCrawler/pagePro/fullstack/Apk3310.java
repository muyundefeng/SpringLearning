package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游戏基地  http://apk.3310.com/
 * Aawap #227
 * @author lisheng
 */


public class Apk3310 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://apk.3310.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://apk.3310.com/apps/lttx/list_92_1.html");
			page.addTargetRequest("http://apk.3310.com/game/jsmx/list_103_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://apk\\.3310\\.com/game/.*").match()
				||page.getHtml().links().regex("http://apk\\.3310\\.com/apps/.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//div[@id='SoftListBox']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
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
		if(page.getUrl().regex("http://apk\\.3310\\.com/apps/\\d+\\.html").match()
				||page.getUrl().regex("http://apk\\.3310\\.com/game/\\d+\\.html").match()
				&&!page.getUrl().toString().contains("list"))
		{
			
			Apk apk = Apk3310_Detail.getApkDetail(page);
			
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
