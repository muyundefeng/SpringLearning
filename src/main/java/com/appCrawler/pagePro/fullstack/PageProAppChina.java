package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProAppChina_Detail;
import com.appCrawler.utils.PropertiesUtil;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;

/**
 * 应用汇[中国] app搜索抓取 url:http://www.appchina.com/sou/MT 5
 * 添加请求头的cookie信息后，可以顺利爬取
 * 
 * @version 1.0.0
 */
public class PageProAppChina implements PageProcessor {
	static boolean flag = true;
	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProAppChina.class);

	// 定义网站编码，以及间隔时间
	Site site = Site
			.me()
			.setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval())
			.addCookie(
					"Cookie",
					"_ga=GA1.2.954657431.1431479287; Hm_lvt_c1a192e4e336c4efe10f26822482a1a2=1441883409,1442194106,1442197239; session=4984a0de057033b2cf19bce0206bf47f; CNZZDATA1255679161=1113939593-1441878025-%7C1447117810");

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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (page.getUrl().toString().equals("http://www.appchina.com/")) {
			page.addTargetRequest("http://www.appchina.com/category/30/1_1_1_3_0_0_0.html");
			page.addTargetRequest("http://www.appchina.com/category/40/1_1_1_3_0_0_0.html");// 游戏
		}
		// 获取搜索页面
		if (page.getUrl()
				.regex("http://www\\.appchina\\.com/category/40/\\d+_1_1_3_0_0_0\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.appchina\\.com/category/30/\\d+_1_1_3_0_0_0\\.html")
						.match()) {
			LOGGER.debug("match success, url:{}", page.getUrl());

			// 获取详细链接，以及分页链接
			List<String> urlList = page.getHtml()
					.links("//ul[@class='app-list']")
					.regex("http://www\\.appchina\\.com/app/.+").all();
			LOGGER.info("add appdetail:"+urlList.size());
			List<String> pageList = page.getHtml()
					.links("//div[@class='discuss_fangye']").all();
			urlList.addAll(pageList);
			for (String temp : urlList) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}

			// 打印搜索结果url
			LOGGER.debug("app info results urls: {}", page.getTargetRequests());
		}

		// 获取信息
		if (page.getUrl().regex("http://www\\.appchina\\.com/app/.+").match()) {

			Apk apk = PageProAppChina_Detail.getApkDetail(page);

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
