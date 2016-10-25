package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Fpwap_Detail;
import com.appCrawler.pagePro.apkDetails.Ruan8_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.gargoylesoftware.htmlunit.AlertHandler;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Ruan8 implements PageProcessor{
	//Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(0);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(10).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Ruan8.class);

	public Apk process(Page page) {
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.ruan8.com/")){
			page.addTargetRequest("http://www.ruan8.com/android/1.html");		//添加软件索引
			page.addTargetRequest("http://mgame.ruan8.com/asub_1/");			//添加游戏索引
		}else if(page.getUrl().toString().equals("http://www.ruan8.com/android/1.html") 
				|| page.getUrl().toString().equals("http://mgame.ruan8.com/asub_1/")){
			List<String> urlList = page.getHtml().links("//div[@class='mdcata']").all();
			page.addTargetRequests(urlList);
		}
		if(page.getUrl().regex("http://www\\.ruan8\\.com/android/.*").match()
				|| page.getUrl().regex("http://mgame\\.ruan8\\.com/.*").match()){
		
		List<String> url_detail = page.getHtml().links("//div[@id='myTab2_con0']").all() ;
		List<String> url_pages = page.getHtml().links("//div[@class='mdpage']").all();
		url_detail.addAll(url_pages);
//		for (String temp : urls) {
//			System.out.println(temp);
//			
//		}

		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(url_detail);
		for (String temp : cacheSet) {
			if(PageProUrlFilter.isUrlReasonable(temp))				
				page.addTargetRequest(temp);
		}
		
		}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.ruan8\\.com/soft.*").match()
				|| page.getUrl().regex("http://mgame\\.ruan8\\.com/agame.*").match()){
			
			Apk apk = Ruan8_Detail.getApkDetail(page);
			
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
