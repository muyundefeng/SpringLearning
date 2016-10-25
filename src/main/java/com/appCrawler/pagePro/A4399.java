package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;


import com.appCrawler.pagePro.apkDetails.A4399_Detail;

import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 4399手机开放平台  http://a.4399.cn/
 * A4399 #349
 * @author tianlei
 */
public class A4399 implements PageProcessor{
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
		Set<String> cacheSet = Sets.newHashSet();
		if(page.getUrl().regex("http://a.4399.cn/search.html.w=.*").match()){
			List<String> urlList = page.getHtml().xpath("//li[@class='m_game_item']/div/a/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='ks_num']/a/@href").all();	
			cacheSet.addAll(urlList);
			cacheSet.addAll(nextPage);	
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		
		if(page.getUrl().regex("http://a.4399.cn/game-id.*").match()){			
			Apk apk = A4399_Detail.getApkDetail(page);	
			return apk;
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
