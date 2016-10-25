package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mthe9_Detail;
import com.appCrawler.pagePro.apkDetails.Mthe9_Detail2;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 九城游戏中心  http://m.the9.com/html/Tegrazhuanqu/
 * Aawap #232
 * @author DMT
 */


public class Mthe9 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getHtml().links().regex("http://m\\.the9\\.com/html.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/xinwen/.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/hudong/.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/yxgl/.*").match())
		{
			
			List<String> urlList=page.getHtml().xpath("li[@class='clearfix']/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("ul[@class='pagelist']/li/a/@href").all();
			urlList.addAll(urlList1);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!page.getUrl().regex("http://m\\.the9\\.com/html/xinwen/.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/hudong/.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/yxgl/.+").match()){
					System.out.println(url);
					page.addTargetRequest(url);
				}
			}
		}
			if(page.getHtml().links().regex("http://m\\.the9\\.com/html/netgame.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/xinwen/.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/hudong/.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/yxgl/.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/youxipingce/.*").match())
			{
				
				List<String> urlList3=page.getHtml().links().regex("http://m\\.the9\\.com/html.*").all();
				//urlList.addAll(urlList3);
				Set<String> cacheSet1 = Sets.newHashSet();
				cacheSet1.addAll(urlList3);
				for(String url : cacheSet1){
					if(PageProUrlFilter.isUrlReasonable(url)&&!page.getUrl().regex("http://m\\.the9\\.com/html/xinwen/.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/hudong/.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/yxgl/.+").match()){
						page.addTargetRequest(url);
					}
				}
		}
		
 		
	
		
	
		//提取页面信息
		//if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/\\w{4}/\\d{5}\\.html").match())
		if(page.getUrl().regex("http://m\\.the9\\.com/html/netgame.*").match())
		//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
		{
			
			
			Apk apk = Mthe9_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
		if(page.getUrl().regex("http://m\\.the9\\.com/html/downloads/.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/downloads/list.+").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/downloads/newgame.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/downloads/freegame.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/downloads/hotgame.*").match()&&!page.getUrl().regex("http://m\\.the9\\.com/html/downloads/paidgame.*").match())
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				
				Apk apk = Mthe9_Detail2.getApkDetail(page);
				
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
