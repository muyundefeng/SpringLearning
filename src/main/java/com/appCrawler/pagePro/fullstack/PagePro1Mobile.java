package com.appCrawler.pagePro.fullstack;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.appCrawler.pagePro.apkDetails.PagePro1Mobile_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 第一手机 app搜索抓取 url:http://www.1mobile.tw/index.php?c=search.json&keywords=MT
 * 2015年12月3日11:08:02
 * 
 * @author DMT
 * @version 1.0.0
 */
public class PagePro1Mobile implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PagePro1Mobile.class);

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
		// System.out.println("pagehtml="+page.getHtml().toString());

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 添加所有分类
		if (page.getUrl().toString().equals("http://www.1mobile.tw/")) {
			page.addTargetRequests(page.getHtml()
					.links("//dl[@class='list_cnt']")
					.regex("http://www\\.1mobile\\.tw/category/[a-z-]+").all());

		}

		if (page.getUrl()
				.regex("http://www\\.1mobile\\.tw/category/[a-z-]+/\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.1mobile\\.tw/category/[a-z-]+")
						.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//ul[@class='cnt_list clearfix']")
					.regex("http://www\\.1mobile\\.tw/[a-z-]+\\d+\\.html")
					.all();
			List<String> pageList = page
					.getHtml()
					.links("//div[@class='page mt20']")
					.regex("http://www\\.1mobile\\.tw/category/[a-z-]+/\\d+\\.html")
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
		// 获取信息
		if (page.getUrl().regex("http://www\\.1mobile\\.tw/[a-z-]+\\d+\\.html").match()) {
			Apk apk = PagePro1Mobile_Detail.getApkDetail(page);

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