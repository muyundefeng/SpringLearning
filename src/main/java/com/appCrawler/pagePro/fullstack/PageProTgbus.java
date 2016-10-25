package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
import com.appCrawler.pagePro.apkDetails.PageProTgbus_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 安卓中文网(tgbus)[中国] app搜索抓取 url:http://a.tgbus.com/game/,
 * http://a.tgbus.com/soft/ 9
 * 
 * @version 1.0.0
 */
public class PageProTgbus implements PageProcessor {

	// 日志管理对象
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(PageProTgbus.class);

	// 定义网站编码，以及间隔时间
	Site site = Site.me().setCharset("gb2312")
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
		if (page.getUrl().toString().equals("http://android.tgbus.com/")) {
			page.addTargetRequest("http://android.tgbus.com/soft/more/");// 应用
			page.addTargetRequest("http://android.tgbus.com/game/more/");// 游戏
			
		}

		// 获取搜索页面
		if (page.getUrl().regex("http://android.tgbus.com/soft/more/.*")
				.match()
				|| page.getUrl().regex("http://android.tgbus.com/game/more/.*")
						.match()) {
			LOGGER.debug("match success, url:{}", page.getUrl());

			// 获取详细链接，以及分页链接
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='news2_660_nr']")
					.regex("http://android\\.tgbus\\.com/soft/.*|http://android\\.tgbus\\.com/game/.*")
					.all();
			//手动添加分页	http://android.tgbus.com/soft/more/List_76.shtml   
			//	        http://android.tgbus.com/game/more/List_76.shtml
			List<String> pageList = new LinkedList<String>();
			for(int i=0;i<77;i++){
				pageList.add("http://android.tgbus.com/soft/more/List_"+i+".shtml");
				pageList.add("http://android.tgbus.com/game/more/List_"+i+".shtml");
			}
			urlList.addAll(pageList);
			for (String temp : urlList) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}

			// 打印搜索结果url
			LOGGER.debug("app info results urls: {}", page.getTargetRequests());
		}

		// 获取信息	Index.shtml
		if (!page.getUrl().toString().contains("/more/") && 
				!page.getUrl().toString().contains("Index.shtml") &&
				page.getUrl().regex("http://android\\.tgbus\\.com/soft.*|http://android\\.tgbus\\.com/game.*")
				.match()) {
			Html html = page.getHtml();
			Apk apk = PageProTgbus_Detail.getApkDetail(page);

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
