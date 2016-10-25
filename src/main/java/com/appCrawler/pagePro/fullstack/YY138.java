package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.omg.PortableServer.THREAD_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.YY138_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * http://www.yy138.com/youxi/zuixin/
 * YY138手机游戏中心
 * Aawap #386
 * @author lisheng
 */


public class YY138 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
//	try {
//		Thread.sleep(3000);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
		//System.out.println(page.getHtml().toString());
	if(page.getHtml().links().regex("http://www\\.yy138\\.com/.*").match())
	{
		List<String> categoryList=page.getHtml().xpath("//div[@id='panel']/ul/li/a/@href").all();
 		List<String> apkList=page.getHtml().xpath("//div[@class='listbox clearfix']/ul/li/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@class='page clearfix']/ul/li/a/@href").all();
 		apkList.addAll(categoryList);
 		apkList.addAll(pageList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.yy138\\.com/\\D+").match()
				&&!page.getUrl().toString().contains("zuixin"))
		{
			
			Apk apk = YY138_Detail.getApkDetail(page);
			
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
