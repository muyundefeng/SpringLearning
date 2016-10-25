package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Downbank_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 下载银行 http://www.downbank.cn
 * Downbank #132
 * (1)下载链接有防盗链设置
 * @author DMT
 */


public class Downbank implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.downbank.cn")){
			page.addTargetRequest("http://www.downbank.cn/s/177_1.htm");
		}
	
		if(page.getUrl().regex("http://www.downbank.cn/s/177_\\d+\\.htm").match()) 
		{
			List<String> url_detail=page.getHtml().links("//div[@class='cp-main']").all();
			List<String> url_page=page.getHtml().links("//div[@class='mainNextPage']").all();
			url_detail.addAll(url_page);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_detail);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
		}
		
	
		//提取页面信息
		if(	page.getUrl().regex("http://www\\.downbank\\.cn/s/\\d+\\.htm").match() ){
	
			
			Apk apk = Downbank_Detail.getApkDetail(page);
			
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
