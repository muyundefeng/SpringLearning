package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Tompda_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #236
 * Tompda http://android.tompda.com/
 * @author lisheng
 *
 */
public class Tompda implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://android\\.tompda\\.com/0-7-1-0-\\d+\\?keyword=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='content_list']/dl/a/@href").all();
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='content_down']/div/span/a/@href").all();
			if(flag==1)
			{
				if(pages.size()>6)
				{
					for(int i=0;i<6;i++)
					{
						page.addTargetRequest(pages.get(i));
					}
				}
				else{
					page.addTargetRequests(pages);
				}
				flag++;
			}
			apps.addAll(pages);
		}
		if(page.getUrl().regex("http://android\\.tompda\\.com/\\d+\\.html").match()&&!page.getUrl().regex("http://android\\.tompda\\.com/.*").toString().contains("DownLoad")&&!page.getUrl().regex("http://android\\.tompda\\.com/.*").toString().contains("?keyword"))
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				return Tompda_Detail.getApkDetail(page);
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
