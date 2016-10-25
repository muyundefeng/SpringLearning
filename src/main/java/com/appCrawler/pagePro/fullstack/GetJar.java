package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.GetJar_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * GetJar
 * 网站主页：http://www.getjar.com/
 * Aawap #647
 * @author lisheng
 */


public class GetJar implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.getjar.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.getjar.com/categories/");
			return null;
		}
		if("http://www.getjar.com/categories/".equals(page.getUrl().toString()))
		{
			List<String> categorys = page.getHtml().links("//div[@class='content']").all();
			page.addTargetRequests(categorys);
			return null;
		}
		if(page.getUrl().regex("http://www.getjar.com/categories/.+").match())
		{
			List<String> apps = page.getHtml().links("//div[@id='apps']").all();
			List<String> pages = page.getHtml().links("//div[@class='more']").all();
			String pageNext = page.getHtml().xpath("//a[@id='row_right_arrow']/@href").toString();
			page.addTargetRequests(pages);
			page.addTargetRequests(apps);
			page.addTargetRequest(pageNext);
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www.getjar.com/categories/.*").match())
		{
			
			Apk apk = GetJar_Detail.getApkDetail(page);
			
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
