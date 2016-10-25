package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Yxk_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 游戏库
 * 网站主页：http://sj.xiaopi.com/yxk.html
 * @id 407
 * @author lisheng
 */

public class Yxk implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.xiaopi\\.com/search/.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='yx_box']").all();
			page.addTargetRequests(apps);
			page.addTargetRequest(page.getUrl().toString().replace("type=yx", "type=soft"));
			List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.xiaopi\\.com/game|soft/\\d+\\.html").match()
				&&!page.getUrl().regex("http://sj\\.xiaopi\\.com/yxk.*").match()
				&&!page.getUrl().toString().contains("sj"))
			{
				return Yxk_Detail.getApkDetail(page);
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
