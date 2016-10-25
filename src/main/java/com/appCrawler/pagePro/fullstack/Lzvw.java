package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Lzvw_Detail;
import com.appCrawler.pagePro.apkDetails.Lzvw_Detail2;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 龙珠游乐园  http://www.lzvw.com/
 * Aawap #222
 * @author DMT
 */


public class Lzvw implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if(page.getHtml().links().regex("http://www\\.lzvw\\.com.*").match())
		{
			List<String> urlList=page.getHtml().xpath("//div[@class='menu_links']/ul/li[2]/a/@href").all();
			List<String> urlList1=page.getHtml().xpath("//div[@class='menu_links']/ul/a[2]/@href").all();
			List<String> urlList2=page.getHtml().xpath("//div[@class='listPart_info_cnName']/a/@href").all();
			List<String> urlList3=page.getHtml().xpath("//div[@class='listFoot']/form/a[1]/@href").all();
			List<String> urlList4=page.getHtml().xpath("//div[@class='listFoot']/form/a[2]/@href").all();
			urlList.addAll(urlList1);
			urlList.addAll(urlList2);
			urlList.addAll(urlList3);
			urlList.addAll(urlList4);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
 		
		
		
	
		//提取页面信息
		//if(page.getUrl().regex("http://www\\.shouyou520\\.com/game/\\w{4}/\\d{5}\\.html").match())
		if(page.getUrl().regex("http://www\\.lzvw\\.com/game.*").match())
		//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
		{
			
			Apk apk = Lzvw_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
	if(page.getUrl().regex("http://www\\.lzvw\\.com/soft/.*").match())
		//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
		{
			
			Apk apk = Lzvw_Detail2.getApkDetail(page);
			
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
