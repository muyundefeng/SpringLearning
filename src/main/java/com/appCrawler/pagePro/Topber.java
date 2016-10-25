package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Topber_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 安卓市场
 * 网站主页：http://www.topber.com/qq.html
 * @id  465
 * @author lisheng
 */
public class Topber implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.topber\\.com/.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='applist_search']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='pages']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.topber\\.com/app/\\d+\\.html").match())
			{
				return Topber_Detail.getApkDetail(page);
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
