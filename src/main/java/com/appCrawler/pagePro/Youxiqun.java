package com.appCrawler.pagePro;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.appCrawler.pagePro.apkDetails.Youxiqun_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 游戏群 http://www.youxiqun.com/
 * 渠道编号:383
 * post提交
 */
public class Youxiqun implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.youxiqun\\.com/e/search/index\\.php.*").match())
		{
			List<String> apps=page.getHtml().xpath("//ul[@class='search-result-list width90']/li/a[1]/@href").all();
			page.addTargetRequests(apps);
			
		}
		if(page.getUrl().regex("http://www\\.youxiqun\\.com/game/.*").match())
		{
			return Youxiqun_Detail.getApkDetail(page);
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
