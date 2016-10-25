package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Game7723_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 7723手机游戏 http://www.7723.cn/search/game/?keyword=qq
 * Aawap #422
 * @author lisheng
 */
public class Game7723 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.7723\\.cn/search/game/\\?keyword=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='azl_l']/ul/li/div[@class='fl azll_tu']/a/@href").all();
	 		//System.out.println(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='al_page page']/a/@href").all();	 		
	 		apps.addAll(pages);
	 		System.out.println(apps);
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://www\\.7723\\.cn/game/\\d+\\.htm").match())
			{
				return Game7723_Detail.getApkDetail(page);
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
