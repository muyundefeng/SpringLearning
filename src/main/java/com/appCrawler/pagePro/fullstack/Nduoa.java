package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.appCrawler.pagePro.apkDetails.Nduoa_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;


public class Nduoa implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Nduoa.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.nduoa.com/")){
			page.addTargetRequest("http://www.nduoa.com/cat83?&page=1");//应用
			page.addTargetRequest("http://www.nduoa.com/cat82?&page=1");//游戏
		}
		//System.out.println(page.getHtml().toString());
		if(page.getUrl().regex("http://www\\.nduoa\\.com/cat83\\?&page=\\d+").match()
				||	page.getUrl().regex("http://www\\.nduoa\\.com/cat82\\?&page=\\d+").match()){
		
			List<String> url_detail =page.getHtml().links("//div[@class='item']").all() ;
			List<String> url_page =page.getHtml().links("//div[@id='pagination']").all() ;
			url_detail.addAll(url_page);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(url_detail);
		for (String temp : cacheSet) {
			if(PageProUrlFilter.isUrlReasonable(temp) && !temp.startsWith("http://www.nduoa.com/apk/download"))				
				page.addTargetRequest(temp);
		}
		
		}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.nduoa\\.com/apk/detail/.*").match() 
			|| page.getUrl().regex("http://www\\.nduoa\\.com/package/detail/.*").match()){
			
			Apk apk = Nduoa_Detail.getApkDetail(page);
			
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
