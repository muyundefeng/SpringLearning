package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Xpgod_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 手机天堂
 * url：http://www.xpgod.com/shouji/game/
 *#272
 * @author DMT
 * @modify author lisheng
 */
public class Xpgod implements PageProcessor{
	Site site = Site.me().setCharset("GBK").setRetryTimes(0).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		System.out.println("hello");
		if(page.getUrl().regex("http://so\\.xpgod\\.com/cse/search\\?q=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='result-item result-game-item']/div[1]/a/@href").all();
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			apps.addAll(pages);
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://www\\.xpgod\\.com/shouji/.*").match())
		{
			return Xpgod_Detail.getApkDetail(page);
		}
		return null;
		
	}

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	public static String handleStr(String str)
	{
		if(str!=null)
		{
			String re=null;
			int ednIndex=str.indexOf("Interrelated");
			return re="["+str.substring(11,ednIndex-2)+"]";
		}
		else
		{
			return null;
		}
	}
}
