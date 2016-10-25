package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.App155_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 155游戏厅
 * 网站主页：http://android.155.cn/
 * Aawap #548
 * @author lisheng
 */


public class App155 implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://android.155.cn/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://android.155.cn/game/index_1.html");
			page.addTargetRequest("http://android.155.cn/netgame/index_1.html");
			return null;
		}
	
		if(page.getHtml().links().regex("http://android.155.cn/game/index_\\d+\\.html").match()
				||page.getHtml().links().regex("http://android.155.cn/netgame/index_\\d+\\.html").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='sof_r_center']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='list_page']").all();
	 		List<String> apps1=page.getHtml().links("//div[@class='on_cent_left list_dl3']").all();
	 		//apps.addAll(categoryList);
	 		apps.addAll(pages);
	 		apps.addAll(apps1);
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
		if(page.getUrl().regex("http://android\\.155\\.cn/game/\\d+\\.html").match()
				||page.getUrl().regex("http://wy\\.155\\.cn/spec\\d+/").match())
		{
			
			Apk apk = App155_Detail.getApkDetail(page);
			
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
