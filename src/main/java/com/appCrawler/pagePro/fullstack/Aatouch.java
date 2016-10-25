package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aatouch_Detail;
import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安贝市场  http://app.aatouch.com/
 * Aatouck #43
 * @author tianlei
 */


public class Aatouch implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		//System.out.println(page.getHtml().toString());
		if("http://app.youxibaba.cn/".equals(page.getUrl().toString())){
			cacheSet.add("http://app.youxibaba.cn/soft/applist/cid/9");
			cacheSet.add("http://app.youxibaba.cn/soft/applist/cid/1");
		}
		else{
			String nextPage = null;
			List<String> urlList=page.getHtml().xpath("//div[@class='app-max']/div[2]/h4/a/@href").all();
			nextPage = page.getHtml().xpath("//div[@class='pages']/a[2]/@href").toString();
			cacheSet.addAll(urlList);
			if(nextPage != null){
				cacheSet.add(nextPage);
				}
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		
 		
		
		
	
		//提取页面信息
		if(page.getUrl().regex("http://app\\.youxibaba\\.cn/app/info/appid/.*").match()){
	
			
			Apk apk = Aatouch_Detail.getApkDetail(page);
			
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
