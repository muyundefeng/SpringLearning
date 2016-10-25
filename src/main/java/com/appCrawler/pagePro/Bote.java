package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Bote_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 伯特手机市场
 * 网站主页：http://www.shouji.com/index
 * Aawap #540
 * @author lisheng
 */
public class Bote implements PageProcessor{
	Site site = Site.me().setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.shouji\\.com/search.*").match())
		{
			List<String> apps=page.getHtml().links("//li[@class='first list-li']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='r-page']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.shouji\\.com/download-\\d+\\.html").match())
			{
				return Bote_Detail.getApkDetail(page);
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
