package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ewan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 上海益玩  http://www.ewan.cn/
 * Ewan #303
 * @author tianlei
 */


public class Ewan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		List<String> urlList=page.getHtml().xpath("//div[@class='games']/ul[6]//label/a/@href").all();
		
		cacheSet.addAll(urlList);	
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www.ewan.cn/game-id-.*").match()){		
			Apk apk = Ewan_Detail.getApkDetail(page);
			
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
