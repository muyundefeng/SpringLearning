package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.A4399_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 4399手机开放平台  http://a.4399.cn/
 * A4399 #349
 * @author tianlei
 */


public class A4399 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		//System.out.println(page.getHtml().toString());
		if("http://a.4399.cn/".equals(page.getUrl().toString())){
			for(int i =2;i<=12;i++)
				cacheSet.add("http://a.4399.cn/game-kid-"+i+".html");		
		}else if(page.getUrl().regex("http://a.4399.cn/game-kid.*").match()){
			List<String> urlList=page.getHtml().xpath("//h3[@class='appname']/a/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='ks_num']/a/@href").all();		
			cacheSet.addAll(nextPage);
			cacheSet.addAll(urlList);
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}

		//提取页面信息
		if(page.getUrl().regex("http://a.4399.cn/game-id.*").match()){
			
			Apk apk = A4399_Detail.getApkDetail(page);	
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
