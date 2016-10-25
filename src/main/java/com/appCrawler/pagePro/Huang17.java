package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Huang17_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 一起晃手游网  http://www.17huang.com/yx/
 * 渠道编号:366
 * http://www.17huang.com/plus/search.php?q=*#*#*#
 * @author DMT
 */
public class Huang17 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.17huang\\.com/plus/search\\.php\\?q=.*").match()){
			List<String> apkList=page.getHtml().xpath("//div[@class='search_down']/a/@href").all();
			page.addTargetRequests(apkList);
			List<String> apps=page.getHtml().xpath("//div[@class='search_third clearfix']/ul/li/p[1]/a/@href").all();
			page.addTargetRequests(apps);
			page.addTargetRequests(page.getHtml().links("//div[@class='pagelist']").all());
		}
		//提取页面信息
			if(page.getUrl().regex("http://www\\.17huang\\.com/yx/.*").match()
					&&!page.getUrl().toString().equals("http://www.17huang.com/yx/"))
			{
					
				return Huang17_Detail.getApkDetail(page);
			
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
