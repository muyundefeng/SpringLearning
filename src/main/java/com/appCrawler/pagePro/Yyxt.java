package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Yyxt_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 优优下载
 * 网站主页：http://www.yyxt.com/
 * @id 535
 * @author lisheng
 */
public class Yyxt implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.yyxt\\.com/search.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='searchlist']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='searchpage']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.yyxt\\.com/android/\\d+\\.html").match())
			{
				return Yyxt_Detail.getApkDetail(page);
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
