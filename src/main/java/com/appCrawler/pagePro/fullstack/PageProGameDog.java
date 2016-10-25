package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProGameDog_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 游戏狗[中国] app搜索抓取 url:http://search.gamedog.cn/app/?keyword=QQ&platform=Android
 * id:12
 * 
 * @version 1.0.0
 */
public class PageProGameDog implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProGameDog.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	// .addCookie("Cookie",
	// "Hm_lvt_469bb5e3f2fcd8034994eefe251cf2e9=1442483476; Hm_lvt_15b67ce53ef6efd896a81316b10e56a4=1442481391");

	/**
	 * process the page, extract urls to fetch, extract the data and store
	 *
	 * @param page
	 */
	@Override
	public Apk process(Page page) {
		LOGGER.debug("crawler url: {}", page.getUrl());
		try {
			Thread.sleep(1000);
		} catch (Exception e) {

		}
		if (page.getUrl().toString().equals("http://android.gamedog.cn/")) {
			page.addTargetRequest("http://android.gamedog.cn/list_online_0_0_0_0_0_1.html");// 网游
			page.addTargetRequest("http://android.gamedog.cn/list_soft_0_0_0_0_0_1.html");// 软件
			page.addTargetRequest("http://android.gamedog.cn/list_game_0_0_0_0_0_1.html");// 单击
			page.addTargetRequest("http://android.gamedog.cn/apad_game_0_0_0_0_0_1.html");// 平板
		}

		// 获取翻页页面
		if (page.getUrl()
				.regex("http://android\\.gamedog\\.cn/.+_0_0_0_0_0_\\d+\\.html")
				.match()) {
			LOGGER.debug("match success, url:{}", page.getUrl());

			List<String> url_detail = page.getHtml()
					.links("//ul[@class='list_yc_big']").all();

			List<String> url_page = page.getHtml()
					.links("//div[@class='page']").all();
			url_detail.addAll(url_page);
			LOGGER.info("This page add " + url_detail.size() + " pages");
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_detail);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
		}

		// 获取信息
		if (page.getUrl().regex("http://android.gamedog.cn/soft/\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://android.gamedog.cn/game/\\d+\\.html")
						.match()
				|| page.getUrl()
						.regex("http://android.gamedog.cn/online/\\d+\\.html")
						.match()) {

			Apk apk = PageProGameDog_Detail.getApkDetail(page);

			page.putField("apk", apk);
			if (page.getResultItems().get("apk") == null) {
				page.setSkip(true);
			}
		} else {
			page.setSkip(true);
		}

		return null;
	}

	/**
	 * get the site settings
	 *
	 * @return site
	 * @see Site
	 */
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
