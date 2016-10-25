package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #218
 * 手游世界http://www.shouyou520.com/
 * 搜索接口：http://www.shouyou520.com/game.php?keyword=%E8%B7%91%E9%85%B7
 * @author lisheng
 *
 */
public class Shouyou520 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		
		if(flag==1)
		{
			String pageNo=page.getHtml().xpath("//div[@class='pages']/ul/li[6]/span/strong[1]/text()").toString();
			int number=Integer.parseInt(pageNo);
			System.out.println(number);
			String pages=page.getHtml().xpath("//div[@class='pages']/ul/li[4]/a/@href").toString();
			System.out.println("****"+pages);
			if(number<5)
			{
				page.addTargetRequests(page.getHtml().xpath("//div[@class='pages']/ul/li/a/@href").all());
			}
			else{
				for(int i=1;i<=5;i++)
				{
					System.out.println(pages.replace("page="+number, "page="+i));
					page.addTargetRequest(pages.replace("page="+number, "page="+i));
				}
			}
			flag++;
		}
		if(page.getUrl().regex("http://www\\.shouyou520\\.com/game\\.php.*").match())
		{
			List<String> urllist=page.getHtml().xpath("//div[@class='gameList']/dl/dt/a/@href").all();
			for(String url:urllist)
			{
				if(PageProUrlFilter.isUrlReasonable(url))
				{
					page.addTargetRequest(url);
				}
			}
		}
		  if (page.getUrl().regex("http://www\\.shouyou520\\.com/game/.*").match()) {
	        	return Shouyou520_Detail.getApkDetail(page);

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
