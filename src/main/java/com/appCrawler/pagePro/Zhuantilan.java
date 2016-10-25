package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Zhuantilan_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 专题兰
 * 网站主页：http://www.zhuantilan.com/
 * Aawap #586
 * @author lisheng
 */
public class Zhuantilan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		System.out.println("hello world");
	
		if(page.getUrl().regex("http://www.zhuantilan.com/plus/search.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='newest-soft bluea']").all();
			page.addTargetRequests(apps);
			System.out.println(apps);
			List<String> pages=page.getHtml().links("//div[@class='page']").all();
			page.addTargetRequests(pages);
		}
		System.out.println(page.getHtml());
		if(page.getUrl().regex("http://www.zhuantilan.com/android/.*").match()
				||page.getUrl().regex("http://www.zhuantilan.com/andyx/.*").match()
				&&!page.getUrl().toString().contains("list"))
			{
				return Zhuantilan_Detail.getApkDetail(page);
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
