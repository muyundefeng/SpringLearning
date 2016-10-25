package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Mo365_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 摸摸手机游戏
 * 网站主页：http://www.365mo.com/s/g/qq.html
 * @id 425
 * @author lisheng
 */
public class Mo365 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.365mo\\.com/s/g/.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='entry game clearfix']/div[@class='thumb']/a/@href").all();
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://www\\.365mo\\.com/game/\\d+\\.html").match())
			{
				return Mo365_Detail.getApkDetail(page);
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
