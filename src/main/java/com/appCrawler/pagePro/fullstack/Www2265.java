package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Www2265_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 2265安卓游戏 http://www.2265.com/ Www2265 #109
 * 
 * @author DMT
 */

public class Www2265 implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(10)
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// System.out.println(page.getHtml().toString());
		if (page.getUrl().toString().equals("http://www.2265.com/")) {
			page.addTargetRequest("http://www.2265.com/danji/1.html");// 单击游戏
			page.addTargetRequest("http://www.2265.com/pojie/1.html");// 破解游戏
			page.addTargetRequest("http://www.2265.com/game_7/1.html");// 手机网游
			page.addTargetRequest("http://www.2265.com/game/1.html");// 安卓游戏
			page.addTargetRequest("http://www.2265.com/soft/1.html");// 安卓软件

		}
		if (page.getUrl().regex("http://www\\.2265\\.com/danji/\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.2265\\.com/pojie/\\d+\\.html")
						.match()
				|| page.getUrl()
						.regex("http://www\\.2265\\.com/game_7/\\d+\\.html")
						.match()
				|| page.getUrl()
						.regex("http://www\\.2265\\.com/game/\\d+\\.html")
						.match()
				|| page.getUrl()
						.regex("http://www\\.2265\\.com/soft/\\d+\\.html")
						.match()) {
			List<String> url_page = page.getHtml()
					.links("//div[@class='list-page']").all();
			List<String> url_detail = page.getHtml()
					.links("//div[@id='listboxsix']").all();
			url_page.addAll(url_detail);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for (String temp : cacheSet) {
				if (!temp.startsWith("http://www.2265.com/down")
						&& PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}

		}
		// 提取页面信息
		if (page.getUrl().regex("http://www\\.2265\\.com/game/android_\\d+\\.html").match()
				|| page.getUrl().regex("http://www\\.2265\\.com/soft/android_\\d+\\.html").match()) {

			Apk apk = Www2265_Detail.getApkDetail(page);

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
