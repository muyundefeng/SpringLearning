package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Crsky_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #196 非凡软件站 Crsky http://android.crsky.com/
 * 
 * @author DMT
 *
 */
public class Crsky implements PageProcessor {

	Site site = Site.me().setCharset("gb2312")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Crsky.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://android.crsky.com/")) {
			page.addTargetRequest("http://android.crsky.com/game/");// 添加游戏索引
			page.addTargetRequest("http://android.crsky.com/app/");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://android\\.crsky\\.com/app/index_\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://android\\.crsky\\.com/game/index_\\d+\\.html")
						.match()
				|| page.getUrl().toString()
						.equals("http://android.crsky.com/game/")
				|| page.getUrl().toString()
						.equals("http://android.crsky.com/app/")) {// 获取所有分类页
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='list_line']")
					.regex("http://android\\.crsky\\.com/soft/\\d+\\.html")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pagination']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		// 提取页面信息 http://android.crsky.com/soft/63434.html 
		if (page.getUrl().regex("http://android\\.crsky\\.com/soft/\\d+\\.html").match()) {

			Apk apk = Crsky_Detail.getApkDetail(page);

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
