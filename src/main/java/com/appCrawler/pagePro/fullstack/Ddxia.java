package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Ddxia_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 豆豆软件下载站 http://www.ddxia.com Ddxia #138 无站内搜索接口，搜索跳到百度搜索
 * 
 * @author DMT
 */

public class Ddxia implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Ddxia.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.ddxia.com")) {
			page.addTargetRequest("http://www.ddxia.com/list/1423.html");
		}
		if (page.getUrl()
				.regex("http://www\\.ddxia\\.com/list/1423_\\d+\\.html")
				.match()
				|| page.getUrl().toString()
						.equals("http://www.ddxia.com/list/1423.html")) {// 获取所有搜索页

			List<String> urlList = page.getHtml()
					.links("//div[@class='rjlb_cnt']")
					.regex("http://www\\.ddxia\\.com/view/\\d+\\.html").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='quotes']")
					.regex("http://www\\.ddxia\\.com/list/1423_\\d+\\.html")
					.all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}
		// 提取页面信息
		if (page.getUrl().regex("http://www\\.ddxia\\.com/view/\\d+\\.html")
				.match()) {

			Apk apk = Ddxia_Detail.getApkDetail(page);

			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		} else {
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
