package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App47473_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 47473
 * 网站主页：http://www.47473.com/
 * Aawap #536
 * @author lisheng
 */
public class App47473 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://so.47473.com.*").match())
		{
			List<String> apps=page.getHtml().links("//ul[@class='typegames-list clearfix']").all();
			page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://www\\.47473\\.com/game/.*").match()
				&&!page.getUrl().toString().contains("m0_t0_d0"))
			{
				return App47473_Detail.getApkDetail(page);
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
