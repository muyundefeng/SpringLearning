package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App962_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 962  http://www.962.net/
 * Aawap #538
 * @author lisheng
 */
public class App962 implements PageProcessor{
	Site site = Site.me().setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://so\\.962\\.net/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.962\\.net/azgame/\\d+\\.html").match()
				||page.getUrl().regex("http://962\\.net/azgame/\\d+\\.html").match())
			{
				return App962_Detail.getApkDetail(page);
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
