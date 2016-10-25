package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Wishdown_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 心愿下载
 * 网站主页：http://www.wishdown.com/
 * 渠道编号：544
 * @author lisheng
 */


public class Wishdown implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.wishdown.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.wishdown.com/soft/list_1_414.html");
			page.addTargetRequest("http://www.wishdown.com/soft/list_1_413.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.wishdown\\.com/soft/list_\\d+_413\\.html").match()
				||page.getHtml().links().regex("http://www\\.wishdown\\.com/soft/list_\\d+_414\\.html").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='down_list_start']").all();
	 		List<String> pages=page.getHtml().links("//div[@id='showpagedown']").all();
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!page.getUrl().toString().contains("#softdown")){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.wishdown\\.com/soft/\\d+\\.html").match())
		{
			
			Apk apk = Wishdown_Detail.getApkDetail(page);
			
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
