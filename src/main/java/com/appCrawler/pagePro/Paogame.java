package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Paogame_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #570
 * 泡游网
 * ·http://www.paogame.com/search?kw=qq
 *@author lisheng
 *
 */
public class Paogame implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.paogame.com/search.*").match()&&!page.getUrl().toString().contains("ct"))
		{
			String url=page.getUrl().toString();
			page.addTargetRequest(url+"&ct=soft");
			page.addTargetRequest(url+"&ct=netgame");
			page.addTargetRequest(url+"&ct=game");
			return null;
		}
		if(page.getUrl().regex("http://www\\.paogame\\.com/search.*ct").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='app-list clearfix']").all();
			for(String str:apps)
			{
				if(!str.contains("download"))
					page.addTargetRequests(apps);
			}
			
		}
		if(page.getUrl().regex("http://www\\.paogame\\.com/soft/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.paogame\\.com/netgame/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.paogame\\.com/game/\\d+\\.html").match())
			{
				return Paogame_Detail.getApkDetail(page);
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
