package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Androidonline_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 安卓在线
 * 网站主页:http://www.androidonline.net/software/index.html
 * 渠道编号:376
 * @author DMT
 */


public class Androidonline implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	if(page.getUrl().toString().equals(("http://www.androidonline.net/software/index.html")))
	{
		page.addTargetRequest("http://www.androidonline.net/software/tool/");
		page.addTargetRequest("http://www.androidonline.net/pojie/");
		page.addTargetRequest("http://www.androidonline.net/online/js/");
		page.addTargetRequest("http://www.androidonline.net/game/REN/");
		return null;
	}
	if(page.getUrl().regex("http://www\\.androidonline\\.net/.*").match()
			||page.getUrl().toString().contains("list_")
			||page.getUrl().regex("http://www\\.androidonline\\.net/game/REN/list_45_\\d{1,3}\\.html").match()||
			page.getUrl().toString().equals("http://www.androidonline.net/game/REN/"))
	{
 		List<String> categoryList=page.getHtml().xpath("//div[@class='s_con']/span/a/@href").all();
 		System.out.println(categoryList);
 		List<String> apkList=page.getHtml().xpath("//div[@class='game_left']/dl/dt/a/@href").all();
 		List<String> pageList=page.getHtml().xpath("//div[@class='page']/a/@href").all();
 		System.out.println(pageList+"********");
 		apkList.addAll(pageList);
 		apkList.addAll(categoryList);
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
		//提取页面信息
		if(page.getUrl().regex("http://www\\.androidonline\\.net/.*").match()
				&&page.getUrl().regex(".*\\d{3,5}\\.html").match()
				&&!page.getUrl().regex("http://www\\.androidonline\\.net/game/REN/list_45_\\d{1,3}\\.html").match())
		{
			
			Apk apk = Androidonline_Detail.getApkDetail(page);
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
