package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.App910_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 九玩游戏
 * 渠道编号：323
 * 网站主页：http://www.910app.com/apps/singleplayer_taxonomy
 * 搜索链接：http://www.910app.com/apps/search?k=%E8%B7%91%E9%85%B7
 */
public class App910 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		
		if(page.getUrl().regex("http://www\\.910app\\.com/apps/search\\?k=.*").match()){										
			//获取app链接
			List<String> appList=page.getHtml().xpath("//div[@class='aprh_a_r']/div/div/a/@href").all();
			page.addTargetRequests(appList);
		}
		if(page.getUrl().regex("http://www\\.910app\\.com/apps/view/.*").match())
		{
			
			return App910_Detail.getApkDetail(page);
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
