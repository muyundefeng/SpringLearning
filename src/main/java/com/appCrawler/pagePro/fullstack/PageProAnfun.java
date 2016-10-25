package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProAnfun_Detail;
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
 * 安粉[中国] id = 45
 * 游戏分类下的应用打不开
 */
public class PageProAnfun implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProAnfun.class);

	// 定义网站编码，以及间隔时间
	Site site = Site
			.me()
			.setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval())
			.addCookie(
					"Cookie",
					"PHPSESSID=acrpihdkusr2u2kp97r2sr9d30; CNZZDATA1253445757=1913284411-1438240505-%7C1447287555");

	/**
	 * process the page, extract urls to fetch, extract the data and store
	 *
	 * @param page
	 */
	public Apk process(Page page) {
		LOGGER.debug("crawler url: {}", page.getUrl());
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// if(page.getUrl().toString().equals("http://www.appfun.cn/")){
		// page.addTargetRequest("http://www.appfun.cn/soft/applist/cid/9/page/1");
		// page.addTargetRequest("");
		// }

		if (page.getUrl().toString().equals("http://www.appfun.cn/")) {
			for (int i = 1; i < 519; i++) {
				page.addTargetRequest("http://www.appfun.cn/game/applist/cid/15/page/"
						+ i);// 添加所有的应用
			}
			for (int i = 1; i < 1462; i++) {
				page.addTargetRequest("http://www.appfun.cn/game/applist/cid/1/page/"
						+ i);// 添加所有的游戏
			}
		}

		if (page.getUrl().regex("http://www\\.appfun\\.cn/game/applist/cid/.*")
				.match()) {
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='content-categoryCtn-content clearfix']")
					.regex("http://www\\.appfun\\.cn/app/info/.*")
					.all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
			LOGGER.info("Add "+cacheSet.size()+" pages");
		}

		// if (page.getUrl().regex("http://www\\.appfun\\.cn/.*").match()) {
		// LOGGER.debug("match success, url:{}", page.getUrl());
		//
		// // 获取详细链接，以及分页链接
		// List<String> urlList =
		// page.getHtml().links().regex("http://www\\.appfun\\.cn/.*").all();
		//
		// Set<String> cacheSet = Sets.newHashSet();
		// cacheSet.addAll(urlList);
		//
		// for (String temp : cacheSet) {
		// if(PageProUrlFilter.isUrlReasonable(temp))
		// page.addTargetRequest(temp);
		// }
		//
		// // 打印搜索结果url
		// LOGGER.debug("app info results urls: {}", page.getTargetRequests());
		// }

		// 获取信息
		if (page.getUrl().regex("http://www\\.appfun\\.cn/app/info/.*").match()) {
			Apk apk = PageProAnfun_Detail.getApkDetail(page);

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
	public Site getSite() {
		return site;
	}

	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
}
