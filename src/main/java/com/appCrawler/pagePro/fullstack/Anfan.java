package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Anfan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安锋网,网站主页:http://www.anfan.com/wangyou/time.html
 * 渠道编号:332
 * @author DMT
 */


public class Anfan implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	if(page.getHtml().links().regex("http://www\\.anfan\\.com/wangyou/time-\\d.*\\.html").match())
	{
 		List<String> urlList=page.getHtml().xpath("//div[@class='gbox']/ul/li/div/a/@href").all();
 		List<String> urlList1=page.getHtml().xpath("//div[@id='d_page_to_t']/a/@href").all();
 		urlList.addAll(urlList1);
 		page.addTargetRequest("http://www.anfan.com/android/android.html");
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		if(page.getUrl().regex("http://www\\.anfan\\.com/android/.*").match()||
				page.getUrl().regex("http://www\\.anfan\\.com/wangyou/.*").match()||
				page.getUrl().toString().startsWith("http://m.anfan")&&
				!page.getUrl().toString().contains("html"))
		{
			
			Apk apk = Anfan_Detail.getApkDetail(page);
			
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
