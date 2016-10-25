package com.appCrawler.pagePro;

import java.util.List;
import java.util.Set;

import com.appCrawler.pagePro.apkDetails.Youxibao_Detail;

import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 游戏宝  http://www.youxibao.com/
 * Youxibao #354
 * @author tianlei
 */
public class Youxibao implements PageProcessor{
	Site site = Site.me().setCharset("gb2312").setRetryTimes(0).setSleepTime(3);
	static{
        //System.setProperty("http.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("http.proxyPort", "8001");
        //System.setProperty("https.proxyHost", "proxy.buptnsrc.com");
        //System.setProperty("https.proxyPort", "8001");
		//threadPool = new CountableThreadPool(threadNum);
	}
	@Override
	public Apk process(Page page) {
		if(page.getUrl().regex("http://so.youxibao.com/cse/search.s=17443286359440565813&entry=1&ie=gbk&nsid=1&q=.*").match()){				
			Set<String> cacheSet = Sets.newHashSet();
			List<String> urlList = page.getHtml().xpath("//div[@class='result f s0']/h3/a/@href").all();
			cacheSet.addAll(urlList);		
			for(String url: cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
					page.addTargetRequest(url);
				}
			}
			
		}
		
		if(page.getUrl().regex("http://www.youxibao.com/ku/.*/").match()){
			Apk apk = Youxibao_Detail.getApkDetail(page);		
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
