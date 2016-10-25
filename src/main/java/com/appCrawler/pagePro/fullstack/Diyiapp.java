package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Diyiapp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * diyiapp http://www.diyiapp.com/				未完成
 * Diyiapp #103
 * (1)没有发现翻页
 * (2)搜索结果是使用js写的
 * 
 * 2015年11月4日19:37:48 找到翻页信息
 * @author DMT
 */


public class Diyiapp implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Diyiapp.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://www.diyiapp.com/")){
			page.addTargetRequest("http://www.diyiapp.com/app/l_3_1/");
		}
		if(page.getUrl().regex("http://www\\.diyiapp\\.com/app/l_3_\\d+/").match()){
			//System.out.println(page.getHtml().toString());
			
			List<String> url_page = page.getHtml().links("//div[@class='pager']").all() ;
			List<String> url_detail =page.getHtml().links("//ul[@class='game']").all() ;
			url_page.addAll(url_detail);
 		
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
			}
		}
		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www.diyiapp.com/app/[0-9]+/").match()){
	
			
			Apk apk = Diyiapp_Detail.getApkDetail(page);
			
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
