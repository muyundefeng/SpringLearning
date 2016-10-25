package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Vipcn_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 清风手机应用商店
 * Aawap #408
 * @author lisheng
 */


public class Vipcn implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.vipcn.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.vipcn.com/shoujiruanjian/shoujiruanjian_SoftTime_Desc_1.html");
			page.addTargetRequest("http://www.vipcn.com/shoujiyouxi/shoujiyouxi_SoftTime_Desc_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.vipcn\\.com/shoujiyouxi/shoujiyouxi_SoftTime_Desc_\\d+\\.html").match()
				||page.getHtml().links().regex("http://www\\.vipcn\\.com/shoujiruanjian/shoujiruanjian_SoftTime_Desc_\\d+\\.html").match())
		{
			List<String> appsAndPage=page.getHtml().links("//div[@class='downlist boxbg lazy clearfix']").all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(appsAndPage);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.vipcn\\.com/shoujiyouxi/.*/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.vipcn\\.com/shoujiruanjian/.*/\\d+\\.html").match()
				&&!page.getUrl().toString().contains("shoujiyouxi_SoftTime_Desc_")
				&&!page.getUrl().toString().contains("shoujiruanjian_SoftTime_Desc_"))
		{
			
			Apk apk = Vipcn_Detail.getApkDetail(page);
			
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
