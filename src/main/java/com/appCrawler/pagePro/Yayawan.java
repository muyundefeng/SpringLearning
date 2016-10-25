package com.appCrawler.pagePro;


import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Yayawan_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 丫丫玩  http://www.yayawan.com
 * Yayawan #362
 * @author tianlei
 */
public class Yayawan implements PageProcessor{
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
		String nextPage = null;

		if(page.getUrl().regex("http://so.yayawan.com/app/.k=.*").match()){
			List<String> urlList = page.getHtml().xpath("//div[@class='list']/ul//h3/a/@href").all();
			nextPage = page.getHtml().xpath("//div[@class='pages']/form/a[2]/@href").toString();
			cacheSet.addAll(urlList);
			if(nextPage != null){
				cacheSet.add(nextPage);
			}

			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}	
		}
		
		if(page.getUrl().regex("http://www.yayawan.com/game/.*/").match()){
			Apk apk = Yayawan_Detail.getApkDetail(page);
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
