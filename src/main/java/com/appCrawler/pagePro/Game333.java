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
import com.appCrawler.pagePro.apkDetails.Game333_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 游乐园手游
 * 网站主页：http://www.game333.net/az/dj/
 * 渠道编号：391
 * http://zhannei.baidu.com/cse/search?s=10070652399699743547!%!%!%entry=1!%!%!%q=*#*#*#!%!%!%nsid=5!%!%!%click=1
 * @author lisheng
 */
public class Game333 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/search\\?s=.*").match()){
			List<String> resultList=page.getHtml().xpath("//div[@id='results']/div/h3/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(pageList);
			cacheSet.addAll(resultList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
		}
		//提取页面信息
				if(page.getUrl().regex("http://www\\.game333\\.net/az/\\d+\\.html").match())
				{
					
					return Game333_Detail.getApkDetail(page);
					
					
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
