package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #227
 * 游戏基地·http://apk.3310.com/
 *@author lisheng
 *
 */
public class Apk3311 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.3310\\.com/plus/search\\.php\\?keyword=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='search-cont']/ul/li/div/div/a/@href").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			if(flag==1)
			{
				if(pages.size()>7)
				{
					for(int i=0;i<7;i++)
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
		if(page.getUrl().regex("http://apk\\.3310\\.com/apps/\\d+\\.html").match()
				||page.getUrl().regex("http://apk\\.3310\\.com/game/\\d+\\.html").match()
				&&!page.getUrl().toString().contains("keyword"))
			{
				return Apk3310_Detail.getApkDetail(page);
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
