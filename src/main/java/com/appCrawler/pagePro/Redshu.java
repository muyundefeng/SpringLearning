package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Redshu_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 红鼠游戏
 * 网站主页：http://so.redshu.com/cse/search?q=qq&s=3709776526053698399&entry=1
 * Aawap #553
 * @author lisheng
 */
public class Redshu implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://so\\.redshu\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.redshu\\.com/app/\\d+\\.html").match())
			{
				return Redshu_Detail.getApkDetail(page);
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
