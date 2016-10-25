package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProDjw_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;
import java.util.Set;

/**
 * 大家玩 app搜索抓取 url:http://android.dajiawan.com/ id:182
 * 
 * @version 1.0.0
 */
public class PageProDjw implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProDjw.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

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
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://android.dajiawan.com/")) {
			page.addTargetRequest("http://android.dajiawan.com/list_game_0_0_1.html");// 添加游戏索引
			page.addTargetRequest("http://android.dajiawan.com/list_soft_0_0_1.html");// 添加应用索引
			page.addTargetRequest("http://android.dajiawan.com/list_online_0_0_1.html");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://android\\.dajiawan\\.com/list_game_0_0_\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://android\\.dajiawan\\.com/list_soft_0_0_\\d+\\.html")
						.match()
				|| page.getUrl()
						.regex("http://android\\.dajiawan\\.com/list_online_0_0_\\d+\\.html")
						.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//div[@class='game_left']").all();
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

		// 获取信息
		if (page.getUrl().regex("http://android\\.dajiawan\\.com/game\\d+/")
				.match()
				|| page.getUrl()
						.regex("http://android\\.dajiawan\\.com/soft\\d+/")
						.match()
				|| page.getUrl()
						.regex("http://android\\.dajiawan\\.com/online\\d+/")
						.match()) {
			Apk apk = PageProDjw_Detail.getApkDetail(page);

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
