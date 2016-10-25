package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Qianzhan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 前瞻之家
 * 网站主页：http://www.qianzhan.com/
 * Aawap #598
 * @author lisheng
 */


public class Qianzhan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Qianzhan.class);

	public Apk process(Page page) {
	
		if("http://www.qianzhan.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://yx.qianzhan.com/game/search?q=&type=&theme=&classify=&dimensions=&pn=1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://yx.qianzhan.com/game/search\\?q=&type=&theme=&classify=&dimensions=&pn=\\d+").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//div[@class='g_list']").all();
	 		//apps.addAll(categoryList);
	 		apps.addAll(apps);
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
		if(page.getUrl().regex("http://yx.qianzhan.com/game/page/.*").match())
		{
			
			Apk apk = Qianzhan_Detail.getApkDetail(page);
			
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
