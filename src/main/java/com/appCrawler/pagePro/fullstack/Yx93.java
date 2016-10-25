package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Yx93_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 搜枣应用
 * 网站主页：http://www.yx93.com/index.html
 * 渠道编号：400
 * @author lisheng
 */


public class Yx93 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.yx93.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.yx93.com/Game.aspx");
			page.addTargetRequest("http://www.yx93.com/WyGame.aspx");
			page.addTargetRequest("http://www.yx93.com/Soft.aspx");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.yx93\\.com/.+").match())
		{
			List<String> categorylist=page.getHtml().xpath("//div[@class='rtcateadiv']/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//div[@class='appcell']/a[1]/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@id='pages']/a/@href").all();
	 		apps.addAll(pages);
	 		apps.addAll(categorylist);
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
		if(page.getUrl().regex("http://www\\.yx93\\.com/game/\\d+/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.yx93\\.com/soft/\\d+/\\d+\\.html").match())
		{
			
			Apk apk = Yx93_Detail.getApkDetail(page);
			
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
