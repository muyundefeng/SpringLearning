package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Ddooo_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * #226
 * 多多软件站  http://www.ddooo.com/
 *@author lisheng
 *
 */
public class Ddooo implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.ddooo\\.com/Search\\.asp\\?keyword=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//div[@class='tb2 lista']/a/@href").all();
			System.out.println(apps);
			page.addTargetRequests(apps);
			List<String> pages=page.getHtml().xpath("//div[@class='dht']/form/div/a/@href").all();
			page.addTargetRequests(pages);
//			if(flag==1)
//			{
//				if(pages.size()>7)
//				{
//					for(int i=0;i<7;i++)
//					{
//						page.addTargetRequest(pages.get(i));
//					}
//				}
//				else{
//					page.addTargetRequests(pages);
//				}
//			}
//			flag++;
		}
		if(page.getUrl().regex("http://www\\.ddooo\\.com/softdown/\\d+\\.htm").match())
			//if(page.getUrl().equals("http://www.shouyou520.com/game/tfcl/66452.html")){
			{
				return Ddooo_Detail.getApkDetail(page);
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
