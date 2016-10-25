package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Ouwan_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 偶玩游戏 http://www.ouwan.com/gamestore/
 * 渠道编号：325
 * http://www.ouwan.com/search/?q=%E8%B7%91%E9%85%B7&from=search
 * 
 */
public class Ouwan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.ouwan\\.com/search/\\?q=.*").match()){										
			List<String> listUrl = page.getHtml().xpath("//div[@class='search-main left']/div/div/a/@href").all();
	        for(String str:listUrl)
	        {
	        	if(!str.contains("download"))
	        	{
	        		page.addTargetRequest(str);
	        	}
	        }
			//page.addTargetRequests(listUrl);
		}
		if(page.getUrl().regex("http://www\\.ouwan\\.com/.*").match())
		{
			return Ouwan_Detail.getApkDetail(page);
			
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
