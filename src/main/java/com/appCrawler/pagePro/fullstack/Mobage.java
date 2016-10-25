package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.appCrawler.pagePro.apkDetails.Mobage_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 梦宝谷  http://www.mobage.cn/
 * Mobage #356
 * @author tianlei
 */


public class Mobage implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
		System.out.println("here");
		if(page.getUrl().regex("http://www.mobage.cn/gamelist/.*").match()){
			List<String> urlList=page.getHtml().xpath("//div[@class='cate_1']/a/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='pagelist']/a/@href").all();
			cacheSet.addAll(urlList);
			cacheSet.addAll(nextPage);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www.mobage.cn/gamedetail/.*").match()){
	
			
			Apk apk = Mobage_Detail.getApkDetail(page);
			
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
