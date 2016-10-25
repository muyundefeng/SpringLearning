package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Tvhome_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #590
 * 电视之家·http://soft.tvhome.com/?pser_key=qq&ptype=3
 *@author lisheng
 *
 */
public class Tvhome implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://soft.tvhome.com/\\?pser_key.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='gameList']").all();
			for(String str:apps)
			{
				if(str.contains("detail"))
				{
					page.addTargetRequest(str);
				}
			}

		}
		if(page.getUrl().regex("http://soft.tvhome.com/detail/\\d+\\.html").match())
		{
				return Tvhome_Detail.getApkDetail(page);
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
