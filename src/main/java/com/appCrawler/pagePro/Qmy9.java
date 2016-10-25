package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Qmy9_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #230
 * 梦游游戏  http://www.9qmy.com/
 * 详情页url链接必须参照搜索页面
 * @author Administrator
 *
 */
public class Qmy9 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		
		if(page.getUrl().regex("http://www\\.9qmy\\.com/front/glist\\.do.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='game_list_left']/div[@class='gll_img']/a[1]/@href").all();
			System.out.println(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='page']/a/@href").all();
			for(String str:apps)
			{
				page.addTargetRequest(str.replace("front/", ""));
			}
			//apps.addAll(pages);
			System.out.println(apps);
			for(String url:apps)
			{
				if(PageProUrlFilter.isUrlReasonable(url)
						&&!url.contains("downloadapk"))
				{
					page.addTargetRequest(url);
					
				}
			}
		}
			
		if(page.getUrl().regex("http://www\\.9qmy\\.com/gamedetail.*").match()
				||page.getUrl().regex(" http://www\\.9qmy\\.com/front/gamedetail.*").match())
			{
			System.out.println("****"+page.getUrl().toString());
				
				return Qmy9_Detail.getApkDetail(page);
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
