package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App5g_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 5g手游网
 * 网站主页：http://www.5g.com/
 * Aawap #518
 * @author lisheng
 */
public class App5g implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://s\\.5g\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.5g\\.com/youxi/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.5g\\.com/game/\\d+\\.html").match())
			{
				return App5g_Detail.getApkDetail(page);
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
