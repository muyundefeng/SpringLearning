package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Bianwanjia_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 便玩家
 * 搜索接口：http://sou.bianwanjia.com/cse/search?s=11834379414783179740&entry=1&q=qq
 * Aawap #402
 * @author lisheng
 */
public class Bianwanjia implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://sou\\.bianwanjia\\.com/cse/search.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='result f s0']/h3/a/@href").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
			if(flag==1)
			{
				if(pages.size()>10)
				{
					for(int i=0;i<10;i++)
					{
						page.addTargetRequest(pages.get(i));
					}
				}
				else{
					page.addTargetRequests(pages);
				}
			}
			flag++;
		}
		if(page.getUrl().regex("http://www\\.bianwanjia\\.com/android/soft/\\d+\\.html").match()
				||page.getUrl().regex("http://www\\.bianwanjia\\.com/android/\\d+\\.html").match())
			{
				return Bianwanjia_Detail.getApkDetail(page);
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
