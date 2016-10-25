package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Shouji86_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 巴乐手机网
 * Aawap #555
 * 网站主页：http://www.86shouji.com/
 * @author lisheng
 */


public class Shouji86 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Shouji86.class);

	public Apk process(Page page) {
	
		if("http://www.86shouji.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.86shouji.com/azrj/1/");
			page.addTargetRequest("http://www.86shouji.com/azyx/1/");
			page.addTargetRequest("http://www.86shouji.com/azwy/1/");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.86shouji\\.com/azrj/\\d+/").match()
				||page.getHtml().links().regex("http://www\\.86shouji\\.com/azyx/\\d+/").match()
				||page.getHtml().links().regex("http://www\\.86shouji\\.com/azwy/\\d+/").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='mod-img-list zm']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='page textct']").all();
	 		//apps.addAll(categoryList);
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
		if(page.getUrl().regex("http://www\\.86shouji\\.com/down/\\d+/").match())
		{
			
			Apk apk = Shouji86_Detail.getApkDetail(page);
			
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
