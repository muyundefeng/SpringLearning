package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Bianwanjia_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 便玩家
 * 网站主页：http://www.bianwanjia.com/
 * Aawap #402
 * @author lisheng
 */


public class Bianwanjia implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.bianwanjia.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.bianwanjia.com/android/xiazai/index.html");
			page.addTargetRequest("http://www.bianwanjia.com/android/soft/index.html");
			return null;
		}
	
		if(page.getUrl().regex("http://www\\.bianwanjia\\.com/android/soft/.*").match()
				||page.getUrl().regex("http://www\\.bianwanjia\\.com/android/xiazai/.*").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//ul[@class='dow_list']/li/div[@class='dp']/a/@href").all();
	 		System.out.println(apps);
	 		List<String> apps1=new ArrayList<String>();
	 		for(String str:apps)
	 		{
	 			if(str.contains(".."))
	 			{
	 				apps1.add("http://www.bianwanjia.com/android"+str.replace("..", ""));
	 			}
	 			else{
	 				apps1.add(str);
	 			}
	 		}
	 		List<String> pages=page.getHtml().xpath("//ul[@class='la_paging fr']/li/a/@href").all();
	 		//apps1.addAll(categoryList);
	 		apps1.addAll(pages);
	 		System.out.println(apps1);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apps1);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.bianwanjia\\.com/android/soft/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.bianwanjia\\.com/android/\\d+\\.html").match())
		{
			
			Apk apk = Bianwanjia_Detail.getApkDetail(page);
			
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
