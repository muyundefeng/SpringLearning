package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.U66_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 66u手机频道  http://android.66u.com/
 * @id 430
 * @author lisheng
 */
public class U66 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://sy\\.66u\\.com/plus/search\\.php.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='item']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='searchpages']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://android\\.66u\\.com/azyx/.*/\\d+_\\d+\\.html").match()
				||page.getUrl().regex("http://ku\\.66u\\.com/\\d+\\.html").match())
			{
				return U66_Detail.getApkDetail(page);
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
