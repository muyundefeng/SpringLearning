package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Redshu_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 红鼠游戏
 * 网站主页：http://www.redshu.com/
 * Aawap #553
 * @author lisheng
 */


public class Redshu implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Redshu.class);

	public Apk process(Page page) {
	
		if("http://www.redshu.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.redshu.com/angame/0_0_id_1");
			page.addTargetRequest("http://www.redshu.com/ansoft/0_0_id_1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.redshu\\.com/ansoft/0_0_id_\\d+").match()
				||page.getHtml().links().regex("http://www\\.redshu\\.com/angame/0_0_id_\\d+").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//ul[@class='m_change_list f-cb']/li/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@class='m-pager']").all();
	 		apps.addAll(pages);
	 		System.out.println(pages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.redshu\\.com/app/\\d+\\.html").match())
		{
			
			Apk apk =Redshu_Detail.getApkDetail(page);
			
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
