package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.K391_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 391k
 * 网站主页：http://www.391k.com/
 * @id 513
 * @author lisheng
 */


public class K391 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.391k.com/".equals(page.getUrl().toString()))
		{
			//System.out.println(page.getHtml());
			//page.putField("Accept-Encoding", "gzip, deflate, sdch");
			page.addTargetRequest("http://www.391k.com/azgame/kapai/");
			page.addTargetRequest("http://www.391k.com/azsoft/azshejiao/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.391k\\.com/azsoft/.*").match()
				||page.getHtml().links().regex("http://www\\.391k\\.com/azgame/.*").match())
		{
			List<String> categorys=page.getHtml().links("//div[@id='leftnav']").all();
			page.addTargetRequests(categorys);
	 		List<String> apps=page.getHtml().links("//div[@id='listcon']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pager']").all();
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
		if(page.getUrl().regex("http://www\\.391k\\.com/azgame/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.391k\\.com/azsoft/\\d+\\.html").match())
		{
			
			Apk apk = K391_Detail.getApkDetail(page);
			
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
