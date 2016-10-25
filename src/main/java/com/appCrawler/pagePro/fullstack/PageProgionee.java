package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProgionee_Detail;
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
 * 金立游戏 app搜索抓取 url:http://game.gionee.com/Front/Index/index/
 * 详情页的链接是重定向的
 *
 * @version 1.0.0
 */
public class PageProgionee implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProgionee.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	/**
	 * process the page, extract urls to fetch, extract the data and store id:46
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
		if (page.getUrl().toString()
				.equals("http://game.gionee.com/Front/Index/index/")) {
			page.addTargetRequest("http://game.gionee.com/Front/Category/index/?category=100&flag=0&page=1");// 添加游戏索引

		}

		if (page.getUrl()
				.regex("http://game\\.gionee\\.com/Front/Category/index/\\?category=100&flag=0&page=\\d+")
				.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//ul[@class='game_list clearfix']")
					.regex("http://game\\.gionee\\.com/Front/Index/tj/\\?type=1&_url=http%3A%2F%2Fgame.gionee.com%2FFront.*")
					.all();
			
			
			//System.out.println("urlList.size()"+urlList.size());
			List<String> pageList = page.getHtml()
					.links("//div[@class='pagination paddingright10']").all();

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
		if (page.getUrl()
				.regex("http://game\\.gionee\\.com/Front/Index/tj/\\?type=1&_url=http%3A%2F%2Fgame.gionee.com%2FFront.*")
				.match()) {
			Apk apk = PageProgionee_Detail.getApkDetail(page);

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
