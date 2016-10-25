package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Liqucn_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 历趣(谷歌电子市场) http://www.liqucn.com/
 * Liqucn #130
 * @author DMT
 */


public class Liqucn implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
	
		if(page.getHtml().links().regex("http://www\\.liqucn\\.com/.*").match())
		{
			
				List<String> urlList2=page.getHtml().xpath("//div[@class='m_sub']/a[3]/@href").all();
				List<String> urlList3=page.getHtml().xpath("//div[@class='m_sub']/a[4]/@href").all();
				List<String> urlList4=page.getHtml().xpath("//div[@class='m_sub']/a[5]/@href").all();
				urlList4.addAll(urlList3);
				urlList4.addAll(urlList2);
				List<String> urlList=page.getHtml().xpath("//ul[@class='app_list']/li/a/@href").all();
				List<String> urlList1=page.getHtml().xpath("//div[@class='page']/a/@href").all();
				urlList.addAll(urlList1);
				urlList.addAll(urlList4);
				Set<String> cacheSet = Sets.newHashSet();
				cacheSet.addAll(urlList);

					for (String temp : cacheSet) {
						if(PageProUrlFilter.isUrlReasonable(temp) && temp.length() < 200)
								page.addTargetRequest(temp);
					}
			}
	
		//提取页面信息
		if(	page.getUrl().regex("http://www\\.liqucn\\.com/.*").match() ){
	
			
			Apk apk = Liqucn_Detail.getApkDetail(page);
			
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
