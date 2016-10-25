package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Cuudo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 酷嘟网 http://game.cuudoo.com/games.html
 * 渠道编号:335
 * @author DMT
 */


public class Cuudo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://game.cuudoo.com/games.html"))
	{
		page.addTargetRequest("http://game.cuudoo.com/games.html");
		page.addTargetRequest("http://game.cuudoo.com/app.html");
		return null;
	}
 		List<String> apkList=page.getHtml().xpath("//div[@class='cover-list33']/ul/li/div/a/table/tbody/tr/td/a/@href").all();
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://game\\.cuudoo\\.com/games_info-\\d*\\.html").match()||page.getUrl().regex("http://game\\.cuudoo\\.com/app_info-\\d*\\.html").match())
		{
			
			Apk apk = Cuudo_Detail.getApkDetail(page);
			
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
