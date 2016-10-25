package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Yx93_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 搜枣应用
 * 搜索接口：http://www.yx93.com/So.Aspx?q=qq&Search=
 * 渠道编号：400
 * @author lisheng
 */
public class Yx93 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.yx93\\.com/So\\.Aspx\\?q=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='details']/a/@href").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@id='pages']/a/@href").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.yx93\\.com/game/\\d+/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.yx93\\.com/soft/\\d+/\\d+\\.html").match())
			{
				return Yx93_Detail.getApkDetail(page);
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
