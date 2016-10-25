package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Downxia_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 当下软件园
 * 网站主页：http://www.downxia.com/android/
 * Aawap #575
 * @author lisheng
 */


public class Downxia implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Downxia.class);

	public Apk process(Page page) {
	
		if("http://www.downxia.com/android/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.downxia.com/android/azyx.html");
			page.addTargetRequest("http://www.downxia.com/android/azrj.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.downxia\\.com/android/azrj.*").match()
				||page.getHtml().links().regex("http://www\\.downxia\\.com/android/azyx.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='ul-game-list fix ul-nobr3 MENU ul-nobr3-g']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pages']").all();
	 		//apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.downxia\\.com/downinfo/\\d+\\.html").match())
		{
			
			Apk apk = Downxia_Detail.getApkDetail(page);
			
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
