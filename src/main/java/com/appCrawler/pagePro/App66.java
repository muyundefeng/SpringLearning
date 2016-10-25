package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App66_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 66软件园
 * 网站主页：http://www.pn66.com/
 * Aawap #520
 * @author lisheng
 */
public class App66 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.pn66\\.com/e/search/result.*").match()
				||page.getUrl().regex("http://www\\.pn66\\.com/e/search/.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apps=page.getHtml().links("//ul[@class='list-all']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='page-num']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.pn66\\.com/html/\\d+\\.html").match())
			{
				return App66_Detail.getApkDetail(page);
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
