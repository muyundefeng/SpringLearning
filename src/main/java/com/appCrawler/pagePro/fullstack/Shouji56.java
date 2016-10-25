package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Shouji56_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 56手机游戏
 * 网站主页：http://www.shouji56.com/
 * 渠道编号：406
 * @author lisheng
 */


public class Shouji56 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.shouji56.com/azgame/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.shouji56.com/azgame/kapai/");
			page.addTargetRequest("http://www.shouji56.com/azsoft/");
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.shouji56\\.com/azgame/.*").match()
				||page.getHtml().links().regex("http://www\\.shouji56\\.com/azsoft/.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//ul[@class='height2']/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//div[@id='game_listCon']/ul/li/div[@class='boxmiddle']/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='pager']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.shouji56\\.com/soft/.*").match()
				||page.getUrl().regex("http://www\\.shouji56\\.com/game/.*").match())
		{
			
			Apk apk = Shouji56_Detail.getApkDetail(page);
			
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
