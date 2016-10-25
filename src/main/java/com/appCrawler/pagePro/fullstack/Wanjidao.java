package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Wanjidao_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 玩机岛
 * 网站主页：http://www.wanjidao.com/
 * Aawap #547
 * @author lisheng
 */


public class Wanjidao implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://www.wanjidao.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.wanjidao.com/danji/lists-0-0-0-0-0-1");
			page.addTargetRequest("http://www.wanjidao.com/wangyou/lists-0-0-0-0-0-0-1");
			return null;
		}
	
		if(page.getHtml().links().regex("http://www.wanjidao.com/wangyou/lists-0-0-0-0-0-0-\\d+").match()
				||page.getHtml().links().regex("http://www.wanjidao.com/danji/lists-0-0-0-0-0-\\d+").match())
		{
			//List<String> categoryList=page.getHtml().xpath("//div[@class='apply-menu']/ul/li/a/@href").all();
	 		List<String> apps=page.getHtml().links("//ul[@class='app-list clearfix']").all();
	 		List<String> pages=page.getHtml().links("//div[@class='pager']").all();
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
		if(page.getUrl().regex("http://www\\.wanjidao\\.com/wangyou/\\w+").match()
				||page.getUrl().regex("http://www\\.wanjidao\\.com/danji/.*").match()
				&&!page.getUrl().toString().contains("lists"))
		{
			
			Apk apk = Wanjidao_Detail.getApkDetail(page);
			
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
