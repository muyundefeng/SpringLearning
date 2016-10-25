package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 


import com.appCrawler.pagePro.apkDetails.PageProIfan178_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 苹果资讯 app搜索抓取
 * #146
 *  @author DMT
 */


public class PageProIfan178 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 加入索引页面
		if (page.getUrl().toString().equals("http://ifan.178.com/")) {
			page.addTargetRequest("http://shouyou.178.com/list/android.html");
			
		}

		if (page.getUrl()
				.regex("http://shouyou\\.178\\.com/list/android_\\d+\\.html")// 索引
				.match()				
				|| page.getUrl().toString()
						.equals("http://shouyou.178.com/list/android.html")) {

			List<String> urlList = page.getHtml()
					.links("//div[@class='tab-content']")
					.regex("http://shouyou\\.178\\.com/\\d+/\\d+\\.html").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}
		//提取页面信息 http://shouyou.178.com/201307/168147281425.html
		if(page.getUrl().regex("http://shouyou\\.178\\.com/\\d+/\\d+\\.html").match()){
	
			
			Apk apk = PageProIfan178_Detail.getApkDetail(page);
			
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
