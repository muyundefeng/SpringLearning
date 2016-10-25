package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Mkzoo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 游戏猿
 * 网站主页：http://www.mkzoo.com/danji/index.html
 * Aawap #629
 * @author lisheng
 */


public class Mkzoo implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.mkzoo.com/danji/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.mkzoo.com/danji/index.html");
			page.addTargetRequest("http://www.mkzoo.com/game/index.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.mkzoo.com/game/index.*").match()
				||page.getHtml().links().regex("http://www.mkzoo.com/danji/index.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='lineList']").all();
	 		List<String> apps1=page.getHtml().links("//ul[@class='blockList']").all();
	 		List<String> page1 = page.getHtml().links("//div[@class='pageBox']").all();
	 		
	 		apps.addAll(apps1);
	 		apps.addAll(page1);
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
		if(page.getUrl().regex("http://www.mkzoo.com/\\d+/").match())
		{
			
			Apk apk = Mkzoo_Detail.getApkDetail(page);
			
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
