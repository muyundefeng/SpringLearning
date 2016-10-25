package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Shunwang_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 渠道编号：334
 * 网站主页：http://mg.shunwang.com/
 *
 */


public class Shunwang implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://mg.shunwang.com/"))
	{
		page.addTargetRequest("http://app.shunwang.com/?app=games&controller=&action=mg_list");
		return null;
	}
 		List<String> apkList=page.getHtml().xpath("//ul[@class='lib-ct-bd down']/li/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//table[@class='page']/tbody/tr/td/a/@href").all();
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}	
	
		//提取页面信息
		if(page.getUrl().regex("http://mg\\.shunwang\\.com/zlk/.*").match())
		{
			
			Apk apk = Shunwang_Detail.getApkDetail(page);
			
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
