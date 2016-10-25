package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.App7399_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 7399手机软件
 * 网站主页：http://app.7399.com/
 * @id 424
 * @author lisheng
 */


public class App7399 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://app.7399.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://app.7399.com/games/p1.html");
			page.addTargetRequest("http://app.7399.com/apps/p1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://app\\.7399\\.com/games/p\\d+\\.html").match()
				||page.getHtml().links().regex("http://app\\.7399\\.com/apps/p\\d+\\.html").match())
		{
	 		List<String> apps=page.getHtml().xpath("//div[@class='items']/ul/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@id='pages']").all();
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
		if(page.getUrl().regex("http://app\\.7399\\.com/android/\\d+\\.html").match())
		{
			
			Apk apk =App7399_Detail.getApkDetail(page);
			
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
