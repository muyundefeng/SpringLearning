package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Sz1001_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #223 1001下载乐园 
 * 
 * @author LISHENG
 *
 */
public class Sz1001 implements PageProcessor {
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
    private static int flag=1;
	@Override
	public Apk process(Page page) {
		if (page.getUrl().regex("http://www\\.sz1001\\.net/query\\.asp.*").match()) 
		{
			List<String> apps=page.getHtml().xpath("//div[@id='results']/ul/li/a/@href").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//p[@id='pages']/a/@href").all();
			if(flag==1)
			{
				if(pages.size()>9)
				{
					for(int i=1;i<=9;i++)
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
		if(page.getUrl().regex("http://www\\.sz1001\\.net/soft/\\d+\\.htm.*").match())
		{
		  return Sz1001_Detail.getApkDetail(page);
		}
		
		return null;
	}

	// @Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
