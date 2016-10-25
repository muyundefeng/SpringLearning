package com.appCrawler.pagePro.fullstack;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import com.appCrawler.pagePro.apkDetails.PageProAngeeks_Detail;
import com.appCrawler.pagePro.apkDetails.PageProAppSina_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

/**
 * 微博应用中心 app搜索抓取 url:http://app.sina.com.cn/ ID：180
 * 
 * @version 1.0.0
 */
public class PageProAppSina implements PageProcessor {
	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProAppSina.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

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
		try{//23：59开始休眠2分钟
			 Calendar now = Calendar.getInstance();  			
		     int hour = now.get(Calendar.HOUR_OF_DAY);
		     int minute = now.get(Calendar.MINUTE);
		     if(hour == 23 && minute ==59){
		    	 LOGGER.info("Sleeping");
		    	 Thread.sleep(1000*60*2);
		    	 LOGGER.info("Wake up");
		     }
		}catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://app.sina.com.cn/")) {
			page.addTargetRequest("http://app.sina.com.cn/catlist.php?cat=100&secondcat=101&");// 添加应用索引
			page.addTargetRequest("http://app.sina.com.cn/catlist.php?cat=200&secondcat=201&");// 添加游戏索引

		}
		if (page.getUrl()
				.toString()
				.equals("http://app.sina.com.cn/catlist.php?cat=100&secondcat=101&")// 添加应用下的所有分类
				|| page.getUrl()
						.toString()
						.equals("http://app.sina.com.cn/catlist.php?cat=200&secondcat=201&")) {// 添加游戏下的所有分类
			page.addTargetRequests(page.getHtml().links("//div[@class='colL']")
					.all());
		}

		if (page.getUrl()
				.regex("http://app\\.sina\\.com\\.cn/catlist\\.php\\?cat=100&secondcat=\\d+&&page=\\d+")// 应用下的所有翻页
				.match()
				|| page.getUrl()
						.regex("http://app\\.sina\\.com\\.cn/catlist\\.php\\?cat=200&secondcat=\\d+&&page=\\d+")// 游戏下的所有翻页
						.match()
				|| page.getUrl()
						.toString()
						.equals("http://app.sina.com.cn/catlist.php?cat=100&secondcat=101&")
				|| page.getUrl()
						.toString()
						.equals("http://app.sina.com.cn/catlist.php?cat=200&secondcat=201&")) {
			List<String> urlList = page.getHtml()
					.links("//dl[@class='appList']")
					.regex("http://app\\.sina\\.com\\.cn/appdetail\\.php\\?appID=.*").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pages']").all();

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
				.regex("http://app\\.sina\\.com\\.cn/appdetail\\.php\\?appID=.*")
				.match()) {
			Apk apk = PageProAppSina_Detail.getApkDetail(page);

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
