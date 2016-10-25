package com.appCrawler.pagePro;

import java.util.List;

import com.appCrawler.pagePro.apkDetails.Mgamebaidu_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * 百度移动网盟-CocosPlay  http://mgame.baidu.com/game/
 * Mgamebaidu #374
 * @author tianlei
 */

public class Mgamebaidu implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(0).setSleepTime(3);
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://mgame\\.baidu\\.com/search/\\?word=.*").match())
		{
			List<String> apps=page.getHtml().xpath("//ul[@class='dk_game_lists_ul']/li/div[1]/a/@href").all();
			page.addTargetRequests(apps);
		}
		if(page.getUrl().regex("http://mgame\\.baidu\\.com/game.*").match())
		{
			return Mgamebaidu_Detail.getApkDetail(page);
		}
		return null;

	}

	@Override
	public Site getSite() {
		return site;
	}

	
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

}
