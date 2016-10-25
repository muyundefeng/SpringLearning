package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Cn314_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 228  中国派   http://www.cn314.com/game/padgame/
 * Aawap #228
 * @author DMT
 */


public class Cn314 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
		if("http://www.cn314.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.cn314.com/soft/padsoft/");
			page.addTargetRequest("http://www.cn314.com/game/padgame/");
			return null;
		}
		
		if(page.getUrl().regex("http://www\\.cn314\\.com/game/padgame/.*").match()
				||page.getUrl().regex("http://www\\.cn314\\.com/soft/padsoft/.*").match())
		{
			 List<String> apps=page.getHtml().xpath("//ul[@class='list']/li/a/@href").all();
	 		 List<String> pages=page.getHtml().xpath("//div[@class='fenye clearfix']/a/@href").all();
	 		 System.out.println(pages);
	 		 apps.addAll(pages);
	 		 for(String str:apps)
	 		 {
	 			 if(PageProUrlFilter.isUrlReasonable(str))
	 			 {
	 				 page.addTargetRequest(str);
	 			 }
	 		 }
		}
	
		//提取页面信息
		if((page.getUrl().regex("http://www\\.cn314\\.com/soft/padsoft/.+").match()
				||page.getUrl().regex("http://www\\.cn314\\.com/game/padgame/.+").match())
				&&!page.getUrl().toString().contains("index")
				&&!page.getUrl().regex("http://www\\.cn314\\.com/soft/padsoft/index_\\d+\\.html").match())
		{
			Apk apk = Cn314_Detail.getApkDetail(page);
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
