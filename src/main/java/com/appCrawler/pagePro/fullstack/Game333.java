package com.appCrawler.pagePro.fullstack;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Game333_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游乐园手游
 * 网站主页：http://www.game333.net/az/dj/
 * 渠道编号：391
 * @author lisheng
 */


public class Game333 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getHtml().links().regex("http://www\\.game333\\.net/az/.*").match())
	{
		List<String> categoryList=page.getHtml().xpath("//li[@id='list2 list']/a/@href").all();
		//page.addTargetRequest("http://www.game333.net/az/ol/");
		//page.addTargetRequest("http://www.game333.net/az/soft/");
 		List<String> apkList=page.getHtml().xpath("//div[@class='list_con clearfix']/li/div/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@id='pagelist']/a/@href").all();
 		apkList.addAll(categoryList);
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.game333\\.net/az/\\d+\\.html").match())
		{
			Apk apk = Game333_Detail.getApkDetail(page);
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
