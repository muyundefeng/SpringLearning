package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App47473_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 47473
 * 网站主页：http://www.47473.com/
 * Aawap #536
 * @author lisheng
 */


public class App47473 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.47473.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.47473.com/game/m0_t0_d0_x0_l0_c0_e0_b0_f0_p1/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.47473\\.com/game/m0_t0_d0_x0_l0_c0_e0_b0_f0.*").match())
		{
	 		List<String> apps=page.getHtml().links("//div[@class='game-list']").all();
	 		List<String> pages=page.getHtml().links("//div[@id='pager']").all();
	 		apps.addAll(pages);
	 		System.out.println(apps);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.47473\\.com/game/.*").match()
				&&!page.getUrl().toString().contains("m0_t0_d0"))
		{
			
			Apk apk = App47473_Detail.getApkDetail(page);
			
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
