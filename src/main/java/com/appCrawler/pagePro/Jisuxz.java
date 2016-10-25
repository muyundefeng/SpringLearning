package com.appCrawler.pagePro;


import java.util.List;
import com.appCrawler.pagePro.apkDetails.Jisuxz_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 极速下载站
 * 网站主页：http://soso.jisuxz.com/2-qq.html?
 * Aawap #543
 * @author lisheng
 */
public class Jisuxz implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(page.getUrl().regex("http://soso\\.jisuxz\\.com.*").match())
		{

			List<String> apps=page.getHtml().links("//div[@class='listbox']").all();
			System.out.println(apps);
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().links("//div[@class='showpage']").all();
			System.out.println(pages);
			for(String str:pages)
			{
				if(str!=null)
				{
					System.out.println(str);
					page.addTargetRequest(str);
				}
			}
			//page.addTargetRequests(pages);
		}
		if(page.getUrl().regex("http://www\\.jisuxz\\.com/down/\\d+.html").match())
			{
				return Jisuxz_Detail.getApkDetail(page);
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
