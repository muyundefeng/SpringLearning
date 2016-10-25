package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Uc_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * UC应用商店 http://apps.uc.cn/
 * Uc.java #105
 * 2015年7月3日11:46:06  打开http://apps.uc.cn/后页面链接的域名变为了http://android.25pp.com/
 * 2016年1月16日11:16:16 修改索引爬取的策略
 * http://android.25pp.com/game/2008_1_0_1.html# 2008表示类型，第一个1表示星级
 * 现得到类型是2001-2008，星级1-5
 * http://android.25pp.com/software/1002_0_0_1.html 1002表示类型，第一个1表示星级
 * 现得到类型是1001-1008，星级1-5
 * @author DMT
 */


public class Uc implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Uc.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://apps.uc.cn/")){
			for(int i=2001;i<2009;i++){
				for(int j=1;j<6;j++)
					page.addTargetRequest("http://android.25pp.com/game/"+i+"_"+j+"_0_1.html");
			}
			for(int i=1001;i<1009;i++){
				for(int j=1;j<6;j++)
					page.addTargetRequest("http://android.25pp.com/software/"+i+"_"+j+"_0_1.html");
			}
			page.addTargetRequest("http://android.25pp.com/game/0_0_0_1_1.html");
			page.addTargetRequest("http://android.25pp.com/software/0_0_0_1_1.html");
		}
		if(page.getUrl().regex("http://android\\.25pp\\.com/game/\\d+_\\d+_0_1_\\d+\\.html").match()
				|| page.getUrl().regex("http://android\\.25pp\\.com/software/\\d+_\\d+_0_1_\\d+\\.html").match()){
		//System.out.println(page.getHtml().toString());
		List<String> url_page = page.getHtml().links("//div[@class='pagearea']").all() ;
		List<String> url_detail = page.getHtml().links("//div[@class='thelist']").all() ;
		url_page.addAll(url_detail);		
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(url_page);
		for (String temp : cacheSet) {
			if(!temp.startsWith("http://android.25pp.com/game/download/") && PageProUrlFilter.isUrlReasonable(temp) )
						page.addTargetRequest(temp);
		}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://android\\.25pp\\.com/detail_\\d+\\.html").match()){
	
			
			Apk apk = Uc_Detail.getApkDetail(page);
			
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
