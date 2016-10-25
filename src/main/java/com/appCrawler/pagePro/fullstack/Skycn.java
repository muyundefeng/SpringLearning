package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Skycn_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 天空软件站 http://sj.skycn.com/ Skycn #142 提供的搜索接口无法搜索手机应用
 * 
 * 2015年5月27日20:52:52 搜索接口可用，修改中...
 * 
 * @author DMT
 */

public class Skycn implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Skycn.class);

	public Apk process(Page page) {
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		// 加入应用和游戏两类索引页面
		if (page.getUrl().toString().equals("http://sj.skycn.com/") ) {
			page.addTargetRequest("http://sj.skycn.com/game/android/");// 游戏
			page.addTargetRequest("http://sj.skycn.com/soft/android/");// 软件
		}

		if (page.getUrl()
				.regex("http://sj\\.skycn\\.com/game/android/1_1_0_0_0_\\d+\\.html")// 游戏索引
				.match()
				|| page.getUrl()
						.regex("http://sj\\.skycn\\.com/soft/android/1_1_0_\\d+\\.html")// 软件索引
						.match()
				|| page.getUrl().toString()
						.equals("http://sj.skycn.com/game/android/")
				|| page.getUrl().toString()
						.equals("http://sj.skycn.com/soft/android/")) {

			List<String> urlList = page.getHtml()
					.links("//ul[@class='list-content-box clearfix']")
					.regex("http://sj\\.skycn\\.com/soft/\\d+\\.html|http://sj\\.skycn\\.com/game/\\d+\\.html").all();
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

		// 提取页面信息
		if (page.getUrl().regex("http://sj\\.skycn\\.com/soft/\\d+\\.html").match() ||
				page.getUrl().regex("http://sj\\.skycn\\.com/game/\\d+\\.html").match() ) {

			Apk apk = Skycn_Detail.getApkDetail(page);

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
