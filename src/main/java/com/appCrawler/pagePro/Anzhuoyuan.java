package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Anzhuoyuan_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 安卓园
 * 网址：http://www.anzhuoyuan.com/
 * Aawap #219
 * @author lisheng
 */
public class Anzhuoyuan implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.anzhuoyuan\\.com/search.*").match())
		{
			//System.out.println(page.getHtml());
			List<String> apps=page.getHtml().xpath("//div[@class='app-box']/div/div/a/@href").all();
			for(String str:apps)
			{
				if(!str.contains("downloads"))
				{
					page.addTargetRequest(str);
				}
			}
			System.out.println(apps);
			String pages=page.getHtml().xpath("//div[@class='pages']/a[2]/@href").toString();
			if(flag<=4)
			{
				page.addTargetRequest(pages);
			}
			flag++;
		}
		if(page.getUrl().regex("http://www\\.anzhuoyuan\\.com/app/info/appid/.*").match())
		{
			return Anzhuoyuan_Detail.getApkDetail(page);
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
