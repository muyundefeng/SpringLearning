package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.D_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 当乐安致 http://www.d.cn/
 * 类名 #134
 * 有三种不同的页面分类，分别是网游、应用和游戏
 * @author DMT
 */


public class D implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(D.class);

	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//加入网游、应用和游戏三类索引页面
		if(page.getUrl().toString().equals("http://www.d.cn/")){
			page.addTargetRequest("http://android.d.cn/game/list_1_0_0_0_0_0_0_0_0_0_0_1_0.html");//游戏
			page.addTargetRequest("http://android.d.cn/software/list_1_0_0_1.html");//软件
			page.addTargetRequest("http://android.d.cn/netgame/list_1_0_0_0_0_0_1.html");//网游
		}
		if(page.getUrl().regex("http://android\\.d\\.cn/game/list_1_0_0_0_0_0_0_0_0_0_0_\\d+_0\\.html").match()){//游戏
			
			List<String> urlList =page.getHtml().links("//div[@class='content clearfix']/div[1]").regex("http://android\\.d\\.cn/game/\\d+\\.html").all() ;
			List<String> pageList =page.getHtml().links("//div[@class='page']").all() ;
			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			
		}
		
		if(page.getUrl().regex("http://android\\.d\\.cn/software/list_1_0_0_\\d+\\.html").match()){//软件
			
			List<String> urlList =page.getHtml().links("//div[@class='content clearfix']/div[1]").regex("http://android\\.d\\.cn/software/\\d+\\.html").all() ;
			List<String> pageList =page.getHtml().links("//div[@class='page']").all() ;
			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			
		}
		
		if(page.getUrl().regex("http://android.d.cn/netgame/list_1_0_0_0_0_0_1.html").match()){//网游
			
			List<String> urlList =page.getHtml().links("//div[@class='content clearfix']/div[1]").regex("http://ng.d.cn/.+/").all() ;
			List<String> pageList =page.getHtml().links("//div[@class='page']").all() ;
			
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			
		}
		
	
		//提取页面信息
		if(	page.getUrl().regex("http://android\\.d\\.cn/game.*html").match()
				|| page.getUrl().regex("http://android\\.d\\.cn/software/\\d+\\.html").match()
	            || page.getUrl().regex("http://ng.d.cn/.+/").match() ){
	
			
			Apk apk = D_Detail.getApkDetail(page);
			
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
