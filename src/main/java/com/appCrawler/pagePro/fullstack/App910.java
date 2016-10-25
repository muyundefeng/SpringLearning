package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.App910_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 九玩游戏
 * 渠道编号：323
 * 网站主页：http://www.910app.com/apps/singleplayer_taxonomy
 * @author DMT
 */


public class App910 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		//System.out.println(page.getHtml().toString());
	if(page.getUrl().regex("http://www\\.910app\\.com/apps/.*").match())
	{
		//page.addTargetRequest("http://www.910app.com/apps/singleplayer_taxonomy");
		page.addTargetRequest("http://www.910app.com/apps/games_taxonomy");	
 		List<String> urlList2=page.getHtml().xpath("//div[@class='tab_shutwo']/dl/dd/a/@href").all();
 		List<String> urlList3=page.getHtml().xpath("//div[@class='pages']/span/span/a/@href").all();
 		urlList2.addAll(urlList3);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(urlList2);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		if(page.getUrl().regex("http://www\\.910app\\.com/apps/view/.*").match())
		{
			
			Apk apk = App910_Detail.getApkDetail(page);
			
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
