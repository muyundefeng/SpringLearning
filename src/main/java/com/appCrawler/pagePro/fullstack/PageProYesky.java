package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProYesky_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;
import java.util.Set;

/** 
 * 天极网 http://mydown.yesky.com
 * PageProYesky #148

 */
public class PageProYesky implements PageProcessor{
	private static  Logger logger = LoggerFactory.getLogger(PageProYesky.class);
	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	@Override
	public Apk process(Page page) {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(page.getUrl().toString().equals("http://mydown.yesky.com")){
			page.addTargetRequest("http://mydown.yesky.com/c/113565_10000966_1.shtml");
		}
		if (page.getUrl()
				.regex("http://mydown\\.yesky\\.com/c/113565_10000966_\\d+\\.shtml")// 索引
				.match()) {
		
			List<String> urlList = page.getHtml()
					.links("//div[@class='zd_ul block']/ul")
					.regex("http://mydown\\.yesky\\.com/sjsoft/\\d+/\\d+\\.shtml").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='flym']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		//the app detail page http://mydown.yesky.com/sjsoft/233/11516233.shtml
		 if (page.getUrl().regex("http://mydown\\.yesky\\.com/sjsoft/\\d+/\\d+\\.shtml").match()) {
			Apk apk = PageProYesky_Detail.getApkDetail(page);
			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		}
		else {
			page.setSkip(true);
		}
		logger.info("return from PageProYesky.process()");
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
