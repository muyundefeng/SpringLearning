package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Rjbos_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 626
 * 博士软件
 * 搜索接口：http://www.rjbos.com/Search.asp?KeyWord=qq&typ=%C8%AB%B2%BF%C8%ED%BC%FE
 *@author lisheng
 *
 */
public class Rjbos implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.rjbos.com/Search.asp\\?KeyWord=.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@id='content']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='page']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www.rjbos.com/wap/\\d+\\.html").match())
			{
				return Rjbos_Detail.getApkDetail(page);
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
