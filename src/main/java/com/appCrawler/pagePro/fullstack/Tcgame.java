package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tcgame_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 天畅游戏
 * 网站主页：http://www.tcgame.com.cn/
 * @id 533
 * @author lisheng
 */


public class Tcgame implements PageProcessor{

	Site site = Site.me().setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.tcgame.com.cn/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.tcgame.com.cn/gamecenter/danji/1");
			page.addTargetRequest("http://www.tcgame.com.cn/gamecenter/wangyou/1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www\\.tcgame\\.com\\.cn/gamecenter/wangyou/\\d+").match()
				||page.getHtml().links().regex("http://www\\.tcgame\\.com\\.cn/gamecenter/danji/\\d+").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().xpath("//div[@class='content']/a/@href").all();
	 		List<String> pages=page.getHtml().links("//div[@class='footly']").all();
	 		System.out.println(apps);
	 		//apps.addAll(categoryList);
	 		for(String str:apps)
	 		{
	 			if(!str.startsWith("http://down."))
	 			{
	 				page.addTargetRequest(str);
	 			}
	 		}
	 		page.addTargetRequests(pages);
			
	}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.tcgame\\.com.cn/danji/.*\\.html").match()
				||page.getUrl().regex("http://www\\.tcgame\\.com.cn/wangyou/.*\\.html").match())
		{
			
			Apk apk = Tcgame_Detail.getApkDetail(page);
			
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
