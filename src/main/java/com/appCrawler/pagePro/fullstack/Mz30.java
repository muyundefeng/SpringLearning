package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Mz30_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 *安卓商城
 *渠道编号：354
 *网站主页：http://www.mz30.cn/
 */


public class Mz30 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);
	public List<String> webapkList=null;
	public Apk process(Page page) {
	
	if(page.getUrl().toString().equals("http://www.mz30.cn/"))
	{
		page.addTargetRequest("http://www.mz30.cn/app/soft/application.html");
		page.addTargetRequest("http://www.mz30.cn/app/soft/game.html");
		//page.addTargetRequest("http://www.189store.com/index/toMkt?type=3");
		page.addTargetRequest("http://www.mz30.cn/app/webgame/webgame.html");
		return null;
	}
	if(page.getUrl().toString().equals("http://www.mz30.cn/app/soft/application.html")||
			page.getUrl().toString().equals("http://www.mz30.cn/app/soft/game.html"))
	{
		List<String> cateGoryList=page.getHtml().xpath("//div[@class='sort-r-2coldiv']/ul/li/a/@href").all();
		System.out.println(cateGoryList);
		page.addTargetRequests(cateGoryList);
		return null;
	}
	//List<String> webapkList=null;
//	if(page.getUrl().toString().equals("http://www.mz30.cn/app/webgame/webgame.html"))
//	{
//		List<String> apkList=page.getHtml().xpath("//div[@class='content-box']/div[1]/div/div/div[2]/ul/li/a/@href").all();
//		List<String> apkList1=page.getHtml().xpath("//div[@class='content-box']/div[3]/div/div[2]/div/div/div/div[2]/ul/li/a/@href").all();
//		apkList.addAll(apkList1);
//		webapkList=apkList;
//	}
	if(page.getUrl().regex("http://www\\.mz30\\.cn/app/soft/sort/.*").match())
	{
		List<String> apkList=page.getHtml().xpath("//div[@class='box-lr20']/div/div[1]/a/@href").all();
		List<String> pageList=page.getHtml().xpath("//ul[@class='pagebar']/li/a/@href").all();
		System.out.println(pageList);
		apkList.addAll(pageList);
		if(webapkList!=null)
		{
			apkList.addAll(webapkList);
		}
		Set<String> cacheSet = Sets.newHashSet();
		cacheSet.addAll(apkList);
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
	}
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.mz30\\.cn/app/soft/\\d*\\.html").match()||page.getUrl().regex("http://www\\.mz30\\.cn/app/webgame/webdetail/\\d*\\.html").match())
			//if(page.getUrl().regex("http://www\\.mz30\\.cn/app/.*").match())
		{
			//System.out.println(page.getHtml().toString());
			Apk apk = Mz30_Detail.getApkDetail(page);
			
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
