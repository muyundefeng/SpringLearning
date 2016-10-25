package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Qqtn_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 腾牛网
 * 网站主页：http://www.qqtn.com/az/
 * Aawap #573
 * @author lisheng
 */


public class Qqtn implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Qqtn.class);

	public Apk process(Page page) {
	
		if("http://www.qqtn.com/az/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.qqtn.com/sj/r_16_1.html");
			page.addTargetRequest("http://www.qqtn.com/sj/r_15_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.qqtn\\.com/sj/r_16_\\d+\\.html").match()
				||page.getHtml().links().regex("http://www\\.qqtn\\.com/sj/r_15_\\d+\\.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@id='softlist']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='tspage']").all();
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
		if(page.getUrl().regex("http://www.qqtn.com/sj/\\d+\\.html").match())
		{
			
			Apk apk = Qqtn_Detail.getApkDetail(page);
			
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
