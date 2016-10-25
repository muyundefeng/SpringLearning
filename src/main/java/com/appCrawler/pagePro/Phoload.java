package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Phoload_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #653
 * 游戏基地·http://www.phoload.com/search.action?searchString=*#*#*#!%!%!%Submit=Search
 *@author lisheng
 *
 */
public class Phoload implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.phoload.com/search.action\\?searchString.*").match())
		{
			List<String> categoryList=page.getHtml().links("//ul[@class='subcat']").all();
	 		List<String> apps=page.getHtml().links("//div[@class='applistresult']").all();
	 		//List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
	 		apps.addAll(categoryList);
	 		//apps.addAll(pages);
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
				if(page.getUrl().regex("http://www.phoload.com/software/.+").match())
				{
				return Phoload_Detail.getApkDetail(page);
			}
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
