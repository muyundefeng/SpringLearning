package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Leyoufang_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 乐游坊
 * 网站主页：http://leyoufang.com/androidgames
 * Aawap #675
 * @author lisheng
 */


public class Leyoufang implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Leyoufang.class);

	public Apk process(Page page) {
	
		if("http://leyoufang.com/androidgames".equals(page.getUrl().toString()))
		{
			List<String> categorys = page.getHtml().links("//div[@class='nav_line clear-fix']").all();
			page.addTargetRequests(categorys);
			return null;
		}
	
		if(page.getHtml().links().regex("http://leyoufang.com/gamelist/.*").match()
				||page.getHtml().links().regex("http://leyoufang.com/GameList/.*").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='j-tab']").all();
	 		List<String> apps=page.getHtml().links("//div[@class='glitem']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pages']").all();
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
		if(page.getUrl().regex("http://leyoufang.com/game/\\d+.html").match())
		{
			
			Apk apk = Leyoufang_Detail.getApkDetail(page);
			
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
