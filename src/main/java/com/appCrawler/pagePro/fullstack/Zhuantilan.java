package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Zhuantilan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 专题兰
 * 网站主页：http://www.zhuantilan.com/
 * Aawap #586
 * @author lisheng
 */


public class Zhuantilan implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Zhuantilan.class);

	public Apk process(Page page) {
	
		if("http://www.zhuantilan.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.zhuantilan.com/android/list_133_1.html");
			page.addTargetRequest("http://www.zhuantilan.com/andyx/list_515_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.zhuantilan.com/android/list_133_\\d+\\.html").match()
				||page.getHtml().links().regex("http://www.zhuantilan.com/andyx/list_515_\\d+\\.html").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='list_li']").all();
	 		//List<String> apps=page.getHtml().xpath("//div[@id='SoftListBox']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//ul[@class='pagelist']").all();
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
		if(page.getUrl().regex("http://www.zhuantilan.com/android/\\d+\\.html").match()
				||page.getUrl().regex("http://www.zhuantilan.com/andyx/.*").match()
				&&!page.getUrl().toString().contains("list"))
		{
			
			Apk apk = Zhuantilan_Detail.getApkDetail(page);
			
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
