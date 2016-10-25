package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Byfen_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 229  百分网  http://android.byfen.com/
 * Aawap #229
 * @author DMT
 */


public class Byfen implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://android.byfen.com/".equals(page.getUrl().toString()))
		{
			List<String> gameCategory=page.getHtml().xpath("//div[@class='less-box']/ul/li/a/@index").all();
			List<String> category=new ArrayList<String>();
			System.out.println(gameCategory);
			for(String str:gameCategory)
			{
				category.add("http://android.byfen.com/game_type.html?sort=update&lang=&type="+str);
			}
			page.addTargetRequests(category);
			//page.addTargetRequests(gameCategory);
			page.addTargetRequest("http://android.byfen.com/soft.html");
			return null;
		}
		//System.out.println(page.getHtml().toString());
		if(page.getHtml().links().regex("http://android\\.byfen\\.com.*").match())
		{
			if(page.getUrl().toString().equals("http://android.byfen.com/soft.html"))
			{
				List<String> appTypes=page.getHtml().xpath("//div[@class='class']/div[2]/ul/li/a/@href").all();
				//System.out.println(appTypes);
//				List<String> category=new ArrayList<String>();
//				System.out.println(appTypes);
//				for(String str:appTypes)
//				{
//					category.add("http://android.byfen.com/soft.html?sort=update&type="+str);
//				}
				page.addTargetRequests(appTypes);
			}
			List<String> apps=page.getHtml().xpath("//ul[@class='mask-124']/li/dl/dt/a/@href").all();
			List<String> apps1=page.getHtml().xpath("//ul[@class='high-list mask-124']/li/dl/dt/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@class='pagination']/ul/li/a/@href").all();
			page.addTargetRequests(apps);
			page.addTargetRequests(apps1);
			page.addTargetRequests(pages);
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://android\\.byfen\\.com/app/\\d+").match())
		{
			Apk apk = Byfen_Detail.getApkDetail(page);
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
