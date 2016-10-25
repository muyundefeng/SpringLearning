package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.Uuu9_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #225
 * 游久网http://sjdb.uuu9.com/
 *@author lisheng
 *
 */
public class Uuu9 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://so\\.yoyojie\\.com/game/.*").match())
		{
			List<String> apps=page.getHtml().xpath("//ul[@id='gamesearch-list']/li/div[@class='game-info-img']/a/@href").all();
			System.out.println(apps);
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='list-page clear_fix']/ul/li/a/@href").all();
			System.out.println(pages);
			page.addTargetRequests(pages);
		}
//			if(flag==1)
//			{
//				if(pages.size()>5)
//				{
//					for(int i=0;i<5;i++)
//					{
//						page.addTargetRequest(pages.get(i));
//					}
//				}
//				else{
//					page.addTargetRequests(pages);
//				}
//				flag++;
//			}
//		}
			
		if(page.getUrl().regex("http://sjdb\\.uuu9\\.com/[a-zA-Z]+/").match()
				||page.getUrl().regex("http://game\\.yoyojie\\.com/game/.*").match())
			{
				return Uuu9_Detail.getApkDetail(page);	
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
