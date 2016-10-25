package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Muzhiwan_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 拇指玩 http://www.muzhiwan.com/ Muzhiwan #106
 * url_detail 的标签提取有两种情况 gamelist pt10 pb20 pl10
 * @author DMT
 */

public class Muzhiwan implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Muzhiwan.class);

	public Apk process(Page page) {

		page.addTargetRequest("http://www.muzhiwan.com/category/25/new-0-0-88.html");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://www.muzhiwan.com/")) {
			page.addTargetRequest("http://www.muzhiwan.com/category/24/new-0-0-1.html");
		}
		if (page.getUrl().toString()
				.equals("http://www.muzhiwan.com/category/24/new-0-0-1.html")) {
			List<String> url_category = page.getHtml()
					.links("//div[@class='w170 fl cate_nav']/ul[1]").all();
			page.addTargetRequests(url_category);
		}
		if (page.getUrl()
				.regex("http://www\\.muzhiwan\\.com/category/\\d+/new-0-0-\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.muzhiwan\\.com/category/\\d+/")
						.match()) {
			
			// System.out.println(page.getHtml().toString());
			List<String> url_page = page.getHtml()
					.links("//p[@class='paging']").all();
			List<String> url_detail1 = page.getHtml()
					.links("//ul[@class='biglist clearfix mt20']").all();// 此标签的提取应使用程序打印出来的，不能使用浏览器中打开的
			List<String> url_detail2 = page.getHtml()
					.links("//ul[@class='gamelist pt10 pb20 pl10']").all();
			// for (String string : url_detail) {
			// System.out.println(string);
			// }
			url_page.addAll(url_detail1);
			url_page.addAll(url_detail2);
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for (String temp : cacheSet) {
				temp = temp.toLowerCase();
				if (!temp.contains("?action=common&opt=downloadstat")
						&& PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
		}

		// 提取页面信息
		else if (page.getUrl().regex("http://www\\.muzhiwan\\.com/.+\\.html")
				.match()) {
			Apk apk = Muzhiwan_Detail.getApkDetail(page);

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
