package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App3322_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 3322软件基地
 * 网站主页:http://www.3322.cc/search.asp?wd=qq&Submit=%CB%D1+%CB%F7
 * Aawap #585
 * @author lisheng
 */
public class App3322 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.3322.cc/search.*").match())
		{
			List<String> appsAndPages=page.getHtml().links("//div[@class='rjlist_l3']").all();
			page.addTargetRequests(appsAndPages);
			//List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			
		}
		if(page.getUrl().regex("http://www\\.3322\\.cc/app/\\d+\\.html").match())
			{
				return App3322_Detail.getApkDetail(page);
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
