package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App7399_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 7399手机软件
 * 网站主页：http://app.7399.com/
 * @id 424
 * @author lisheng
 */
public class App7399 implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://ss\\.7399\\.com/cse/search\\?q=.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://app\\.7399\\.com/android/\\d+\\.html").match())
			{
				return App7399_Detail.getApkDetail(page);
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
