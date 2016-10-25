package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.GetJar_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * GetJar
 * 网站主页：http://www.getjar.com/
 * Aawap #647
 * @author lisheng
 */
public class GetJar implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.getjar.com/search/\\?q=.*").match())
		{
			List<String> apps = page.getHtml().links("//div[@id='apps']").all();
			//page.addTargetRequests(apps);
			List<String> pages = page.getHtml().links("//div[@class='more']").all();
			String pageNext = page.getHtml().xpath("//a[@id='row_right_arrow']/@href").toString();
			page.addTargetRequests(pages);
			page.addTargetRequests(apps);
			page.addTargetRequest(pageNext);
		}
		if(page.getUrl().regex("http://www.getjar.com/categories/.*").match())
		{
				return GetJar_Detail.getApkDetail(page);
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
