package com.appCrawler.pagePro;


import java.util.List;

import com.appCrawler.pagePro.apkDetails.Muzhigame_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 拇指游玩,网站主页为:http://www.muzhigame.com/games/list_3_44_0_0_0.html
 * 渠道编号为:329
 * 搜索接口:http://www.muzhigame.com/plus/search.php?searchtype=titlekeyword&typeid=&keyword=%E8%B7%91%E9%85%B7
 * 
 */
public class Muzhigame implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://www\\.muzhigame\\.com/plus/search\\.php\\?searchtype.*").match()){											
			List<String> detailUrl = page.getHtml().xpath("//div[@id='found-list-baidu']/ul/li/h2/u/a/@href").all();
			List<String> poagelist=page.getHtml().xpath("//div[@class='fl dede_pages']/ul/table/tbody/tr/td/a/@href").all();
			page.addTargetRequests(detailUrl);
			page.addTargetRequests(poagelist);
			
		}
		if(page.getUrl().regex("http://www\\.muzhigame\\.com/games/\\d.*/").match())
		{
			
			Apk apk = Muzhigame_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
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
