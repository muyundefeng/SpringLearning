package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.PageProMoguStore_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 *	蘑菇市场[中国]
 * url:http://www.mogustore.com/
 * 时间：2015年12月28日10:09:47
 * @version 1.0.0
 */

public class PageProMoguStore implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(PageProMoguStore.class);

	public Apk process(Page page) {
		if (page.getUrl().toString().equals("http://www.mogustore.com/")) {
			page.addTargetRequest("http://www.mogustore.com/app.html");
		}
		if (page.getUrl().toString()
				.equals("http://www.mogustore.com/app.html")) {
			List<String> appCategoryList = page.getHtml()
					.links("//div[@class='grid-content leftmenu'][1]/ul").all();
			List<String> gameCategoryList = page.getHtml()
					.links("//div[@class='grid-content leftmenu'][2]/ul").all();
			page.addTargetRequests(appCategoryList);
			page.addTargetRequests(gameCategoryList);
		}
		if (page.getUrl()
				.regex("http://www\\.mogustore\\.com/app_\\d+\\.html.*")
				.match()
				|| page.getUrl()
						.regex("http://www\\.mogustore\\.com/game_\\d+\\.html.*")
						.match()) {
			List<String> url_page = page.getHtml()
					.links("//div[@class='page']").all();

			List<String> url_detail = page.getHtml()
					.links("//div[@class='pro_box app_list app_list_b']").all();

			url_page.addAll(url_detail);

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for (String temp : cacheSet) {
				if (!temp		
						.contains("http://down.mogustore.com/download/")
						&& PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}

		}
		// 提取页面信息
		if (page.getUrl()
				.regex("http://www\\.mogustore\\.com/app_show_\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.mogustore\\.com/game_show_\\d+\\.html")
						.match()) {
			Apk apk = PageProMoguStore_Detail.getApkDetail(page);

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
