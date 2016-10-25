package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Zuiben_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 最笨下载
 * 搜索接口：http://zhannei.baidu.com/cse/search?q=qq&s=8724354373865405587&entry=1
 * Aawap #461
 * @author lisheng
 */

public class Zuiben implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://zhannei\\.baidu\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='footer']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.zuiben\\.com/a_game/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.zuiben\\.com/a_soft/\\d+\\.html").match())
			{
				return Zuiben_Detail.getApkDetail(page);
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
