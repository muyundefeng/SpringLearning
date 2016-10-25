package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 手游520  http://www.shouyou520.com/
 * Aawap #218
 * @author DMT
 * 
 */


public class Shouyou520 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getUrl().toString().equals("http://www.shouyou520.com/"))
		{
			page.addTargetRequest("http://www.shouyou520.com/game/list_1_1.html");
			return null;
		}
		if(page.getHtml().links().regex("http://www\\.shouyou520\\.com/game.*").match())
		{
			List<String> urllist=page.getHtml().xpath("//div[@class='gameList']/dl/dt/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("//div[@class='pages']/ul/li/a/@href").all();
			urllist.addAll(urlList1);
			System.out.println(urllist);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urllist);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/[a-zA-Z]+/\\d+\\.html").match()
				&&!page.getUrl().toString().contains("list"))
		{
			
			Apk apk = Shouyou520_Detail.getApkDetail(page);
			
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
