package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Downxia_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 当下软件园
 * 网站主页：http://www.downxia.com/android/
 * Aawap #575
 * @author lisheng
 */
public class Downxia implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www.downxia.com/search.php.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='search-list1']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='page-sy']").all();
			
			if(flag==1)
			{
				if(pages.size()>5)
				{
					for(int i=0;i<5;i++)
					{
						page.addTargetRequest(pages.get(i));
					}
				}
				else{
					page.addTargetRequests(pages);
				}
			}
			flag++;
		}
		if(page.getUrl().regex("http://www\\.downxia\\.com/downinfo/\\d+\\.html").match())
			{
				return Downxia_Detail.getApkDetail(page);
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
