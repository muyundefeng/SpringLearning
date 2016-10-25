package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Anfan_Detail;
import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 安锋网,网站主页:http://www.anfan.com/wangyou/time.html
 * 渠道编号:332
 * 搜索地址:http://so.anfan.com/cse/search?s=12838058574199100609&q=%C5%DC%BF%E1
 */
public class Anfan implements PageProcessor{
	Site site = Site.me().setCharset("gbk").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://so\\.anfan\\.com/cse/search.*").match()){
		List<String> appList=page.getHtml().xpath("//div[@class='result-list']/div/div[2]/h3/a/@href").all();
		List<String> pageList=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
		page.addTargetRequests(appList);
		page.addTargetRequests(pageList);
		}
		if(page.getUrl().regex("http://www\\.anfan\\.com/android/.*").match()||
				page.getUrl().regex("http://www\\.anfan\\.com/wangyou/.*").match()||
				page.getUrl().toString().startsWith("http://m.anfan")&&
				!page.getUrl().toString().contains("html"))
		{
			
			return Anfan_Detail.getApkDetail(page);
			
			
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
