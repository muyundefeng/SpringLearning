package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tvhome_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 电视之家
 * 网站主页：http://soft.tvhome.com/category/t0_o1_p0
 * Aawap #590
 * @author lisheng
 */


public class Tvhome implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Tvhome.class);

	public Apk process(Page page) {
	
		if("http://soft.tvhome.com/category/t0_o1_p0".equals(page.getUrl().toString()))
		{
			List<String> categorys = page.getHtml().links("//dl[@class='softWare_list']").all();
			page.addTargetRequests(categorys);
			for(int i=1;i<=9;i++)
			{
				for(int j=1;j<=15;j++)
				{
					page.addTargetRequest("http://soft.tvhome.com/category/t"+j+"_o1_p0/"+i);
				}
			}
			return null;
		}
	
		if(page.getHtml().links().regex("http://soft.tvhome.com/category/.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='gameList']").all();
	 		List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		apps.addAll(pages);
	 		System.out.println(pages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&url.contains("detail")){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://soft.tvhome.com/detail/\\d+\\.html").match())
		{
			
			Apk apk = Tvhome_Detail.getApkDetail(page);
			
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
