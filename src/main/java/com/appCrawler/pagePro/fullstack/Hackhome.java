package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Hackhome_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 网侠手机站
 *  http://www.hackhome.com/
 * Aawap #467
 * @author lisheng
 */


public class Hackhome implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.hackhome.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.hackhome.com/SoftList/Android_89_SoftTime_Desc_1.html");
			page.addTargetRequest("http://www.hackhome.com/SoftList/Android_299_SoftTime_Desc_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.hackhome\\.com/SoftList/Android.*").match())
		{
			List<String> categoryList=page.getHtml().xpath("//li[@id='qcls']/p/a/@href").all();
	 		List<String> appsAndPages=page.getHtml().links("//div[@class='downlist boxbg lazy clearfix']").all();
	 		appsAndPages.addAll(categoryList);
	 		System.out.println(appsAndPages);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(appsAndPages);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.hackhome\\.com/XiaZai/SoftView_\\d+\\.html").match())
		{
			
			Apk apk =Hackhome_Detail.getApkDetail(page);
			
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
