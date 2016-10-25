package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;


import com.appCrawler.pagePro.apkDetails.Mobage_Detail;

import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 梦宝谷  http://www.mobage.cn/
 * Mobage #356
 * @author tianlei
 */
public class Mobage implements PageProcessor{
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
		System.out.println("here");
		if(page.getUrl().regex("http://www.mobage.cn/gamelist.kwtype=0&k=.*").match()){
			List<String> urlList=page.getHtml().xpath("//div[@class='cate_1']/a/@href").all();
			List<String> nextPage = page.getHtml().xpath("//div[@class='pagelist']/a/@href").all();
			cacheSet.addAll(urlList);
			cacheSet.addAll(nextPage);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
		}
	
		//提取页面信息
		if(page.getUrl().regex("http://www.mobage.cn/gamedetail/.*").match()){
			Apk apk = Mobage_Detail.getApkDetail(page);
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
