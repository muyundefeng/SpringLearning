package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Az27_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 爱趣安卓游戏
 *  http://www.27az.com/
 * @id 421
 * @author lisheng
 */


public class Az27 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.27az.com/".equals(page.getUrl().toString()))
		{
			List<String> categorylist=page.getHtml().xpath("//div[@class='index_nav']/ul/li/a/@href").all();
			page.addTargetRequests(categorylist);
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.27az\\.com/Android|ol|/.*").match())
//				||page.getHtml().links().regex("http://www\\.27az\\.com/Android/Sort/All/Hack/").match()
//				||page.getUrl().regex("http://www.27az.com/Android/Sort/All/CN/").match()
//				||page.getUrl().regex("http://www.27az.com/Android/Sort/All/Big/").match())
		{
	 		List<String> apps=page.getHtml().xpath("//ul[@class='app_list']/li/a/@href").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='pagenav clearfix']/a/@href").all();
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
		if(page.getUrl().regex("http://www\\.27az\\.com/Android/View/\\d+/").match()
				||page.getUrl().regex("http://www\\.27az\\.com/ol/View/\\d+/").match()
				||page.getUrl().regex("http://www\\.27az\\.com/game/View/\\d+/").match())
		{
			
			Apk apk = Az27_Detail.getApkDetail(page);
			
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
