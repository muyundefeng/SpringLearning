package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Yizhigame_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 一指手游
 * 网站主页：http://www.yizhigame.cn/portal.php
 * Aawap #628
 * @author lisheng
 */
public class Yizhigame implements PageProcessor{
	Site site = Site.me().setCharset("gbk").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.151501.cn/plugin.php\\?id=genee_sydownload:sy.*").match())
		{
			System.out.println(page.getHtml());
			List<String> apps=page.getHtml().xpath("//ul[@class='game-poker']/li/a/@href").all();
			System.out.println(apps);
			page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://www.151501.cn/sygame-\\d+\\.html").match())
		{
				return Yizhigame_Detail.getApkDetail(page);
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
