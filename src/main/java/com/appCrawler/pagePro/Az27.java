package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Az27_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 爱趣安卓游戏
 * http://www.27az.com/game/?key=qq
 * @id 421
 * @author lisheng
 */
public class Az27 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	private static int flag=1;
	@Override
	public Apk process(Page page) {
	
		if(page.getUrl().regex("http://www\\.27az\\.com/game/\\?key.*").match())
		{
			List<String> apps=page.getHtml().xpath("//ul[@class='app_list']/li/a/@href").all();
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://www\\.27az\\.com/Android/View/\\d+/").match()
				||page.getUrl().regex("http://www\\.27az\\.com/ol/View/\\d+/").match()
				||page.getUrl().regex("http://www\\.27az\\.com/game/View/\\d+/").match())
			{
				return Az27_Detail.getApkDetail(page);
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
