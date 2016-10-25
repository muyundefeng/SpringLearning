package com.appCrawler.pagePro;


import java.util.List;


import com.appCrawler.pagePro.apkDetails.Anzhuome_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 安卓迷
 * 网站主页：http://www.anzhuo.me/
 * @id 531
 * @author lisheng
 *
 */
public class Anzhuome implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(page.getUrl().regex("http://www\\.anzhuo\\.me/plus/search.*").match())
		{
			List<String> apps =page.getHtml().xpath("//div[@class='post-list']/ul/li/h2/a/@href").all();
			List<String> pages=page.getHtml().links("//div[@class='pages']").all();
			apps.addAll(pages);
			//System.out.println(page.getHtml());
			page.addTargetRequests(apps);
			System.out.println(apps);
		}
		if(page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoruanjian/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.anzhuo\\.me/anzhuoyouxi/\\d+\\.html").match())
			{
				return Anzhuome_Detail.getApkDetail(page);
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
