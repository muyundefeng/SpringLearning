package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.D9soft_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 第九软件园
 * http://www.d9soft.com/
 * Aawap #469
 * @author lisheng
 */
public class D9soft implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://s\\.d9soft\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			for(String str:apps)
			{
				if(str.startsWith("http://az.d9soft"))
				{
					page.addTargetRequest(str.replace("az", "www").replace("app", "android/app"));
				}
			}
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='pageFooter']/a/@href").all();
			page.addTargetRequests(pages);
			
		}
		if(page.getUrl().regex("http://www\\.d9soft\\.com/android/game/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.d9soft\\.com/android/app/\\d+\\.html").match())
			{
				return D9soft_Detail.getApkDetail(page);
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
