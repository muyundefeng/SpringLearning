package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Yiwan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 易玩手机商城
 * 网站主页：http://www.yiwan.com/
 * @id 512
 * @author lisheng
 */


public class Yiwan implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Yiwan.class);

	public Apk process(Page page) {
	
		if("http://www.yiwan.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.yiwan.com/az/");
			page.addTargetRequest("http://www.yiwan.com/soft/azsoft/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.yiwan\\.com/soft/azsoft/.*").match()
				||page.getHtml().links().regex("http://www\\.yiwan\\.com/az.*").match())
		{
			List<String> categoryList=page.getHtml().links("//div[@id='leftnav']").all();
	 		List<String> apps=page.getHtml().links("//div[@id='listcon']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pager']").all();
	 		apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www\\.yiwan\\.com/soft/\\d+/").match()
				||page.getUrl().regex("http://www\\.yiwan\\.com/game/\\d+/").match())
		{
			
			Apk apk = Yiwan_Detail.getApkDetail(page);
			
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
