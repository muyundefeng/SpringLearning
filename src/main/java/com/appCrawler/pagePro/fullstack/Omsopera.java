package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Omsopera_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Opera http://android.oms.apps.opera.com/zh_cn/
 * Aawap #234
 * @author DMT
 */


public class Omsopera implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		//boolean flag=true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(page.getUrl().toString().equals("http://android.oms.apps.opera.com/zh_cn/"))
		{
			page.addTargetRequest("http://android.oms.apps.opera.com/zh_cn/ajax.php?a=side");
			return null;
		}
		if("http://android.oms.apps.opera.com/zh_cn/ajax.php?a=side".equals(page.getUrl().toString()))
		{
			List<String> categorylist=page.getHtml().xpath("//ul[@class='categories']/li/a/@href").all();
			System.out.println(categorylist);
			page.addTargetRequests(categorylist);
		}
		if(page.getHtml().links().regex("http://android\\.oms\\.apps\\.opera\\.com/zh_cn/.+").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='product']/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@class='footer']/a/@href").all();
			//apps.addAll(categorylist);
			apps.addAll(pages);
			System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
					System.out.println(url.toString());
				}
			}
			
		}
		
 		
		
		
		
		//提取页面信息
		//if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/\\w{4}/\\d{5}\\.html").match())
		if(page.getUrl().regex("http://android\\.oms\\.apps\\.opera\\.com/zh_cn/.*").match()
				&&page.getUrl().toString().contains("pos"))
		{
			Apk apk = Omsopera_Detail.getApkDetail(page);
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
