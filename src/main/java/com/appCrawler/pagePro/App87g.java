package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App87g_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 87g
 * 网站主页：http://www.87g.com/
 * Aawap #582
 * @author lisheng
 */
public class App87g implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.87g\\.com/search\\.php.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='bor fff fix']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='page tc']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.87g\\.com/youxi/\\d+\\.html").match())
			{
				return App87g_Detail.getApkDetail(page);
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
