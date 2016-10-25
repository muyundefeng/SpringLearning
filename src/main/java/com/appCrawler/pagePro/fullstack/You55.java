package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.You55_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * #370
 * 55游·http://www.55you.com/tafang/
 * 搜索接口无效
 * @author Administrator
 *
 */

public class You55 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
	if(page.getHtml().links().regex("http://www\\.55you\\.com/.*").match())
	{
		List<String> categoryList=page.getHtml().xpath("//ul[@class='dq_text']/li/a/@href").all();
 		List<String> apkList=page.getHtml().xpath("//ul[@class='tg_box_con']/li/a/@href").all();
 		List<String> sortList=page.getHtml().xpath("//span[@class='right dir_tt']/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@class='pn_pages pd-b']/a/@href").all();
 		apkList.addAll(categoryList);
 		apkList.addAll(sortList);
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("down")){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.55you\\.com/.*/\\d*\\.html").match())
		{
			
			Apk apk = You55_Detail.getApkDetail(page);
			
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
