package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Xz7_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
/**
 * 激光下载站
 * 网站主页：http://www.xz7.com/
 * @id 491
 * @author lisheng
 */
public class Xz7 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://s\\.xz7\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().links("//div[@class='result f s0']").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@id='pageFooter']").all();
			page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.xz7\\.com/dir/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.xz7\\.com/soft/\\d+\\.html").match())
			{
				return Xz7_Detail.getApkDetail(page);
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
