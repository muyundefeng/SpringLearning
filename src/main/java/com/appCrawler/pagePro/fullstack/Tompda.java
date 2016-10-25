package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.appCrawler.pagePro.apkDetails.Shouyou520_Detail;
import com.appCrawler.pagePro.apkDetails.Tompda_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Tompda http://android.tompda.com/
 * Aawap #236
 * @author DMT
 */


public class Tompda implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		if("http://android.tompda.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://android.tompda.com/game/0-12-1-0-1");
			page.addTargetRequest("http://android.tompda.com/0-7-1-0-1");
			return null;
		}
	
		if(page.getUrl().regex(("http://android\\.tompda\\.com/game/0-12-1-0-\\d+")).match()
				||page.getUrl().regex(("http://android\\.tompda\\.com/0-7-1-0-\\d+")).match())
		{
		//if (page.getUrl().regex("http://android\\.tompda\\.com/game/0-12-1-0-.*").match()||page.getUrl().regex("http://android\\.tompda\\.com/0-7-1-0-.*").match()) {
			List<String> urlList = page.getHtml().xpath("//div[@class='content_list']/dl/a/@href").all();
			
			//urlList4.addAll(urlList3);
			// 获取分页链接
	        List<String> urlList2 = page.getHtml().xpath("//div[@class='menu_p']/span/a/@href").all();
	       System.out.println(urlList2);
	        //urlList2.addAll(urlList4);
	        urlList.addAll(urlList2);
	        System.out.println(urlList);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for(String url : cacheSet){
				if(PageProUrlFilter.isUrlReasonable(url) ){
					page.addTargetRequest(url);
				}
			}
		}
		if(page.getUrl().regex("http://android\\.tompda\\.com/\\d+\\.html").match()
				||page.getUrl().regex("http://android\\.tompda\\.com/game/\\d+\\.html").match()
				&&!(page.getUrl().regex(("http://android\\.tompda\\.com/game/0-12-1-0-\\d+")).match()
				||page.getUrl().regex(("http://android\\.tompda\\.com/0-7-1-0-\\d+")).match()
				||page.getUrl().toString().equals("http://android.tompda.com/")))
		{
			//System.out.println("hello");
			
			Apk apk = Tompda_Detail.getApkDetail(page);
			
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
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
}
