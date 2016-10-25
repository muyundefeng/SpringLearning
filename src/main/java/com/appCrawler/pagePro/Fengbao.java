package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Fengbao_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 风暴网
 * 网站主页：http://www.fengbao.com/
 * Aawap #539
 * @author lisheng
 */
public class Fengbao implements PageProcessor{
	Site site = Site.me().setRetryTimes(2).setSleepTime(3);
	//private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.fengbao\\.com/index\\.php\\?ac=search.*").match())
		{
			List<String> apps=page.getHtml().links("//li[@class='dangge-appSearch clearfix']").all();
			for(String str:apps)
			{
				if(!str.contains("down"))
				{
					page.addTargetRequest(str);
				}
			}
			
			List<String> pages=page.getHtml().links("//li[@class='fenye']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.fengbao\\.com/android/.*/\\d+\\.html").match())
			{
				return Fengbao_Detail.getApkDetail(page);
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
