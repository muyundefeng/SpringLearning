package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Jz5u_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Jz5u绿色下载 http://www.jz5u.com/
 * Jz5u #273
 * @author tianlei
 */


public class Jz5u implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
        
		List<String> urlList=page.getHtml().xpath("//div[@class='co_lst']/ul/li/a/@href").all();
		List<String> nextPage = page.getHtml().xpath("//td[@class='tablebody1']/a/@href").all();
        Set<String> cacheSet = Sets.newHashSet();
        cacheSet.addAll(nextPage);
		cacheSet.addAll(urlList);
		for(String url : cacheSet){
		   if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}

		//提取页面信息
		if(nextPage.size() == 0){		
			Apk apk = Jz5u_Detail.getApkDetail(page);		
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
