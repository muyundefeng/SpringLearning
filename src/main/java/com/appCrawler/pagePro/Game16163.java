package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game16163_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 网易游戏中心
 * 网站主页：http://www.16163.com/game/
 * Aawap #500
 * @author lisheng
 */
public class Game16163 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/search.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().toString().contains("gonglue")
				||page.getUrl().regex("http://www\\.16163\\.com/\\[a-z]+/\\d+/").match())
		{
			List<String> urls=page.getHtml().links("//div[@class='inner clearfix']").all();
			System.out.println("urls="+urls);
			page.addTargetRequests(urls);
		}
		if(page.getUrl().regex("http://www\\.16163\\.com/[a-z]+/").match()
				&&!page.getUrl().toString().contains("game")
				&&!page.getUrl().toString().contains("search"))
			{
				return Game16163_Detail.getApkDetail(page);
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
