package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.Sz1001_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 1001下载乐园  http://www.sz1001.net/
 * Aawap #223
 * @author DMT
 */


public class Sz1001 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if("http://www.sz1001.net/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.sz1001.net/sort/346_1.htm");
			page.addTargetRequest("http://www.sz1001.net/sort/347_1.htm");
			return null;
		}
		if(page.getHtml().links().regex("http://www\\.sz1001\\.net/sort/346_\\d+\\.htm").match()
				||page.getUrl().regex("http://www\\.sz1001\\.net/sort/347_\\d+\\.htm").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@id='list_content']/div/div/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@class='tsp_nav']/a/@href").all();
			apps.addAll(pages);
			//urList.addAll(urlList2);
			//urList.add("http://www.sz1001.net/sort/351_1.htm");
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
		if(page.getUrl().regex("http://www\\.sz1001\\.net/soft/\\d+\\.htm.*").match())
		{
			
			Apk apk = Sz1001_Detail.getApkDetail(page);
			
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
