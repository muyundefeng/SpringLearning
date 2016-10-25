package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yyxt_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 优优下载
 * 网站主页：http://www.yyxt.com/
 * @id 535
 * @author lisheng
 */


public class Yyxt implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.yyxt.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.yyxt.com/downlist/android_260_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.yyxt\\.com/downlist/android.*").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='ArtListJiaoUL']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='ListS']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='ArtlistPage']").all();
	 		apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www\\.yyxt\\.com/android/\\d+\\.html").match())
		{
			
			Apk apk = Yyxt_Detail.getApkDetail(page);
			
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
