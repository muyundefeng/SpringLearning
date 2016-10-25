package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Androiddrawer_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * androiddrawer  http://www.androiddrawer.com/
 * Aawap #235
 * @author DMT
 */


public class Androiddrawer implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getHtml().links().regex("http://www\\.androiddrawer\\.com/category/.*").match()
				||page.getUrl().toString().equals("http://www.androiddrawer.com/popular-apps/"))
		{	
			//List<String> urlList=new ArrayList<String>();
			List<String> appTypes=page.getHtml().xpath("//div[@class='categoryTitle apps']/div[2]/ul/li/a/@href").all();
			List<String> appTypes1=page.getHtml().xpath("//div[@class='categoryTitle apps']/div[3]/ul/li/a/@href").all();
			appTypes.addAll(appTypes1);
			System.out.println(appTypes);
			List<String> gamesTypes=page.getHtml().xpath("//div[@class='categoryTitle games']/div[2]/ul/li/a/@href").all();
			List<String> gamesTypes1=page.getHtml().xpath("//div[@class='categoryTitle games']/div[3]/ul/li/a/@href").all();
			gamesTypes.addAll(gamesTypes1);
			System.out.println(gamesTypes);
			//urlList.add("http://www.androiddrawer.com/latest-updated-apps");
			List<String> apps=page.getHtml().xpath("//div[@class='box nodate']/a/@href").all();
			System.out.println(apps);
			apps.addAll(appTypes);
			apps.addAll(gamesTypes);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			}
 		
	
		
	
		//提取页面信息
		//if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/\\w{4}/\\d{5}\\.html").match())
		if(page.getUrl().regex("http://www\\.androiddrawer\\.com/.*").match()
				&&!page.getUrl().toString().contains("category"))
		{
			
			Apk apk = Androiddrawer_Detail.getApkDetail(page);
			
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
