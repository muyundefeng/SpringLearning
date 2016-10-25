package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.util.UrlEncoded;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App15153_Detail;
import com.appCrawler.pagePro.apkDetails.App155_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 15153游戏网站
 * 网站主页：http://www.15153.com/android/list/
 * Aawap #549
 * @author lisheng
 */
public class App15153 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.15153\\.com/search.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@id='loadFlow']").all();
			List<String> pages=page.getHtml().links("//div[@class='page']").all();
			page.addTargetRequests(pages);
			page.addTargetRequests(apps);
		}
		
//		
		if(page.getUrl().regex("http://www\\.15153\\.com/android/app/\\d+\\.html").match())
		{
			return App15153_Detail.getApkDetail(page);
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
