package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Pk52_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 52pk
 * 网站主页：http://down.52pk.com/
 * @id 464
 * @author lisheng
 */
public class Pk52 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://down\\.52pk\\.com/plus/search_new.html.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='list-view']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='pages']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://down\\.52pk\\.com/android/\\d+\\.shtml").match())
			{
				return Pk52_Detail.getApkDetail(page);
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
