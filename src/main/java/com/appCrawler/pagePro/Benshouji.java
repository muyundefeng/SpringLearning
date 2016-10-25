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
import com.appCrawler.pagePro.apkDetails.Benshouji_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 笨手机
 * 网站主页：http://www.benshouji.com/baoku/android_0_0_0_0
 * 渠道编号：392
 * 搜索接口：http://so.benshouji.com/?q=%E8%B7%91%E9%85%B7
 * @author lisheng
 */
public class Benshouji implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://so\\.benshouji\\.com/\\?q=.*").match()
				||page.getUrl().regex("http://so\\.benshouji\\.com/index\\.php\\?q=.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@class='container']/div/div/div/a/@href").all();
			List<String> pageList=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			apkList.addAll(pageList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(apkList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)){
					page.addTargetRequest(url);
				}
			}
			
		}
		//提取页面信息
				if(page.getUrl().regex("http://www\\.benshouji\\.com/.*").match()
						&&!page.getHtml().links().regex("http://www\\.benshouji\\.com/baoku/android_0_0_0_\\d+/").match())
				{
					
					return Benshouji_Detail.getApkDetail(page);
					
				
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
