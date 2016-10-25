package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App6ddd_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 六度下载
 * 网站主页：http://www.6ddd.com/
 * Aawap #599
 * @author lisheng
 */


public class App6ddd implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(App6ddd.class);

	public Apk process(Page page) {
	
		if("http://www.6ddd.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.6ddd.com/soft/sjyx/Android/index_2.html");
			page.addTargetRequest("http://www.6ddd.com/soft/shoujiruanjian/Android/index_2.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.6ddd.com/soft/shoujiruanjian/Android/.*").match()
				||page.getHtml().links().regex("http://www.6ddd.com/soft/sjyx/Android/.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='s-bd cf']").all();
	 		//List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		
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
		if(page.getUrl().regex("http://www.6ddd.com/soft/\\d+\\.html").match())
		{
			
			Apk apk = App6ddd_Detail.getApkDetail(page);
			
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
