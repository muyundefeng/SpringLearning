package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Wankr_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/*
 * 渠道编号:384
 * 网站主页:http://www.wankr.com.cn/gamecenter/3-0-0-0-0
 */


public class Wankr implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	//System.out.println(page.getHtml());
	try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(page.getHtml().links().regex("http://www\\.wankr\\.com\\.cn/gamecenter/3-0-0-0-0\\?page=\\d+").match())
	{
 		List<String> apkList=page.getHtml().xpath("//div[@id='container']/div[@class='grid']/ul/li/span/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//ul[@id='yw1']/li/a/@href").all();
 		//System.out.println(apkList);
 		//System.out.println(pageList);
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.wankr\\.com\\.cn/gamecenter/\\d+").match()
				&&!page.getUrl().toString().contains("page"))
		{
			//System.out.println(page.getHtml().toString());
			Apk apk = Wankr_Detail.getApkDetail(page);
			
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
