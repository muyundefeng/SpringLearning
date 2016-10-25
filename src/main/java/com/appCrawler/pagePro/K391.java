package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.K391_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 391k
 * 网站主页：http://www.391k.com/
 * @id 513
 * @author lisheng
 */
public class K391 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://so\\.391k\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			List<String> pages=page.getHtml().links("//div[@id='footer']").all();
			page.addTargetRequests(apps);
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.391k\\.com/azgame/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.391k\\.com/azsoft/\\d+\\.html").match())
		{
			return K391_Detail.getApkDetail(page);
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
