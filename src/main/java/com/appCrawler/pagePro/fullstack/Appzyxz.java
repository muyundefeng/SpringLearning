package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Appzyxz_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 自由下载
 * 网站主页：http://www.zyxz.com/
 * Aawap #676
 * @author lisheng
 */


public class Appzyxz implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Appzyxz.class);

	public Apk process(Page page) {
	
		if("http://www.zyxz.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.zyxz.com/tupianpaizhao/");
			page.addTargetRequest("http://www.zyxz.com/yizhixiuxian/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.zyxz.com/.*").match())
		{
			List<String> categoryList=page.getHtml().links("//div[@class='kuruan bd clearfix bcon category']").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='soft_list bd clearfix bcon']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page_list']").all();
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
		if(page.getUrl().regex("http://www.zyxz.com/.+/\\d+.html").match())
		{
			
			Apk apk = Appzyxz_Detail.getApkDetail(page);
			
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