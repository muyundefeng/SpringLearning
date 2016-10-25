package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Guguzhu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 咕咕猪
 * 网站主页：http://www.guguzhu.com/
 * Aawap #670
 * @author lisheng
 */


public class Guguzhu implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Guguzhu.class);

	public Apk process(Page page) {
	
		if("http://www.guguzhu.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.guguzhu.com/android/list_664.html");
			page.addTargetRequest("http://www.guguzhu.com/android/list_665.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.guguzhu.com/android/list.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='new-list-az']").all();
	 		//List<String> apps=page.getHtml().xpath("//div[@id='SoftListBox']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@id='pj']").all();
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
		if(page.getUrl().regex("http://www.guguzhu.com/android/\\d+\\.html").match())
		{
			
			Apk apk = Guguzhu_Detail.getApkDetail(page);
			
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
