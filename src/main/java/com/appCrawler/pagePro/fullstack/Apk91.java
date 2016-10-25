package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import com.appCrawler.pagePro.apkDetails.Apk91_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 91手机门户 http://apk.91.com/
 * Apk91.java #90
 *
 * @author DMT
 */

public class Apk91 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk91.class);

	public Apk process(Page page) {
	//System.out.println(page.getHtml().toString());
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//page.addTargetRequest("http://apk.91.com/Soft/Android/isest2.kidsplayroomhiddenobjects-1.html");
		//System.out.println(page.getHtml().toString());
//		if(page.getUrl().toString().equals("http://apk.91.com/game")){
//			page.addTargetRequest("http://apk.91.com/soft");
//		}
//		if(page.getUrl().toString().equals("http://apk.91.com/soft/") 
//				|| page.getUrl().toString().equals("http://apk.91.com/game/")){
//			List<String> url_category = page.getHtml().links("//ul[@class='cate_list nav-content']").all();
//			page.addTargetRequests(url_category);
//		}
//		if(page.getUrl().regex("http://apk\\.91\\.com/soft/.*").match() ||
//				page.getUrl().regex("http://apk.91.com/game/.*").match()){
//		List<String> url_detail = page.getHtml().links("//ul[@id='rptSoft']").all() ;
//		List<String> url_page = page.getHtml().links("//ul[@id='rptSoft']").all() ;
		
		List<String> urls = page.getHtml().links().regex("http://apk\\.91\\.com/.*").all() ;
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urls);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url) && !url.contains("http://apk.91.com/soft/Controller.ashx?")){
				page.addTargetRequest(url);
			}
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://apk\\.91\\.com/Soft/Android.*").match()){
			
			Apk apk = Apk91_Detail.getApkDetail(page);
//			if(apk.getAppName() == null || apk.getAppDownloadUrl() == null){
//				LOGGER.info(page.getHtml().toString());
//			}
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
