package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App3322_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 3322软件基地
 * 网站主页:http://www.3322.cc/
 * Aawap #585
 * @author lisheng
 */


public class App3322 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(App3322.class);

	public Apk process(Page page) {
	
		if("http://www.3322.cc/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.3322.cc/az/180-1.html");
			page.addTargetRequest("http://www.3322.cc/az/192-2-1.html");
			page.addTargetRequest("http://www.3322.cc/az/192-1-1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.3322.cc/az/192-\\d+-\\d+\\.html").match()
				||page.getHtml().links().regex("http://www.3322.cc/az/180-\\d+\\.html").match())
		{
			List<String> appsAndPage=page.getHtml().links("//div[@class='azlist_mz']").all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(appsAndPage);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.3322\\.cc/app/\\d+\\.html").match())
		{
			
			Apk apk = App3322_Detail.getApkDetail(page);
			
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
