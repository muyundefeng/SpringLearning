package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Hookbase_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 顶库软件
 * 网站主页：http://www.hookbase.com/
 * 637
 *@author lisheng
 *
 */
public class Hookbase implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.hookbase.com/.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='softlist']/ul/li/div/a/@href").all();
			page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://www.hookbase.com/azrj/yybf/\\d+\\.html").match())
		{
				return Hookbase_Detail.getApkDetail(page);
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
