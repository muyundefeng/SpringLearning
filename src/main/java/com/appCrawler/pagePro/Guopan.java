package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;



import com.appCrawler.pagePro.apkDetails.Guopan_Detail;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 果盘游戏  http://www.guopan.cn
 * Guopan #372
 * @author tianlei
 */
public class Guopan implements PageProcessor{
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
		if(page.getUrl().regex("http://www.guopan.cn/gcsearch/.*").match()){			
			List<String> urlList = page.getHtml().xpath("//div[@class='wrap_info']/a/@href").all();
			cacheSet.addAll(urlList);
		}
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		
		if(!page.getUrl().regex("http://www.guopan.cn/gcsearch/.*").match()){
				Apk apk = Guopan_Detail.getApkDetail(page);	
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
