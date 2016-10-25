package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.App293294_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 安卓市场
 * 网站主页：http://www.293294.com/
 * Aawap #554
 * @author lisheng
 */

public class App293294 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.293294\\.com/index.php.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='app-list boutique-cnt']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='pagebar']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.293294\\.com/app/\\d+\\.html").match())
			{
				return App293294_Detail.getApkDetail(page);
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
