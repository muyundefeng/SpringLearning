package com.appCrawler.pagePro.fullstack;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Mi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
			//.addCookie("Cookie", "muuid=1431567819898_2635; JSESSIONID=aaaQRjAXrX1PnfKkmh3cv; __utma=127562001.333111101.1431569978.1437032332.1447299473.5; __utmb=127562001.3.10.1447299473; __utmc=127562001; __utmz=127562001.1437032332.4.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic");

	private Logger LOGGER = LoggerFactory.getLogger(Mi.class);
	//这个hashmap用来保存下载到的页面的hash值，查找有哪些页面没有找到正确的页面
	//key保存的是页面string的hash值，value保存的页面的url
	HashMap<String, String>  pageHash = new LinkedHashMap<String, String>();

	public Apk process(Page page) {
		//System.out.println(page.getHtml().toString());
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		String string1 = SinglePageDownloader.getHtml("http://app.mi.com/category/2#page=0");
		String string2 = SinglePageDownloader.getHtml("http://app.mi.com/category/2#page=2");
		System.out.println(string1);
		System.out.println(String.valueOf(string1.hashCode()));
		System.out.println(String.valueOf(string2.hashCode()));
		
		
		
		
		if(page.getUrl().toString().equals("http://app.mi.com/detail/1359")){
			page.addTargetRequest("http://app.mi.com/");
		}
		
		if(page.getUrl().toString().equals("http://app.mi.com/")){
			pageHash.put(String.valueOf(page.getUrl().toString().hashCode()), page.getUrl().toString());
			List<String> url_categoryList = page.getHtml().links("//ul[@class='category-list']").all();
			for (String string : url_categoryList) {
				page.addTargetRequest(string);
				for(int j=0;j<67;j++){
					page.addTargetRequest(string+"#page="+j);
					
				}
			}
		}
		else {
			String  currentPageHash = String.valueOf(page.getHtml().toString().hashCode());
			if(pageHash.containsKey(currentPageHash)){
				LOGGER.info("return the same page:"+page.getUrl().toString()+" and "+pageHash.get(currentPageHash));
			}
			else pageHash.put(currentPageHash, page.getUrl().toString());
		}
		//System.out.println(page.getHtml().toString());
		List<String> urls =page.getHtml().links().regex("http://app\\.mi\\.com/.*").all() ;
		
 		LOGGER.info("Add "+urls.size()+" pages");
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urls);
		for (String temp : cacheSet) {
			if(PageProUrlFilter.isUrlReasonable(temp) 
					&& !temp.contains("http://app.mi.com/download/")
					&& !temp.contains("http://app.mi.com/login?"))				
				page.addTargetRequest(temp);
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://app\\.mi\\.com/detail.*").match()){
			//System.out.println(page.getHtml().toString());
			Apk apk = Mi_Detail.getApkDetail(page);
			
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
