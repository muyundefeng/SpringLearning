package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.PageProIdreamsky_Detail;
import com.appCrawler.pagePro.apkDetails.Yruan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * idreamsky 
 * url:http://www.idreamsky.com/games/index/page/1
 * id:48
 * @version 1.0.0
 */

public class PageProIdreamsky implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(PageProIdreamsky.class);

	public Apk process(Page page) {

		if (page.getUrl().regex("http://www\\.idreamsky\\.com/games/index/page/\\d+").match()) {
		
			List<String> urlList_detail = page.getHtml()
					.links("//div[@class='game-list']").all();
			List<String> urlList_page = page.getHtml()
					.links("//div[@class='pagination']").all();
			urlList_detail.addAll(urlList_page);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList_detail);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
		}

		// 提取页面信息 http://www.yruan.com/softdown.php?id=8443&phoneid=
		if (page.getUrl().regex("http://www\\.idreamsky\\.com/games/show/\\d+").match()) {

			Apk apk = PageProIdreamsky_Detail.getApkDetail(page);

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
