package com.appCrawler.pagePro;

import java.util.List;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * 爱游游戏应用商店  http://www.aiyou.cn/
 * Aiyou #351
 * @author tianlei
 */
public class Aiyou implements PageProcessor{
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
