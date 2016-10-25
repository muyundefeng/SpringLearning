package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ouwan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 偶玩游戏 http://www.ouwan.com/gamestore/
 * 渠道编号：325
 * 该网站所以页面寻找不到，采用全爬.
 * @author DMT
 */


public class Ouwan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getHtml().links().regex("http://www\\.ouwan\\.com/.*").match())
	{
		List<String> urlList = page.getHtml().links().regex("http://www\\.ouwan\\.com/.*").all();
		page.addTargetRequests(urlList);
	}
	if(page.getUrl().regex("http://www\\.ouwan\\.com/.*").match())
	{
		Apk apk = Ouwan_Detail.getApkDetail(page);
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
