package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ddooo_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 多多软件站  http://www.ddooo.com/
 * Aawap #226
 * @author lisheng
 */


public class Ddooo implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.ddooo.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.ddooo.com/softlist/sort_5_184_indate_desc_1.htm");
			return null;
		}
		if(page.getHtml().links().regex("http://www\\.ddooo\\.com/softlist/sort_5_184_indate_desc_\\d+\\.htm").match())
		{
			List<String> urlList=page.getHtml().xpath("//div[@class='tb2 lista']/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("//div[@class='dht']/a/@href").all();
			urlList.addAll(urlList1);
			System.out.println(urlList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
 		
	
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.ddooo\\.com/softdown/\\d+\\.htm").match())
		{
			
			Apk apk = Ddooo_Detail.getApkDetail(page);
			
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
