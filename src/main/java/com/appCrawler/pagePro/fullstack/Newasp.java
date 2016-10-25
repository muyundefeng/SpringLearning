package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Newasp_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 新云网络 http://www.newasp.net/ Newasp #116
 * 
 * @author DMT
 */

public class Newasp implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {

		if (page.getUrl().toString()
				.equals("http://www.newasp.net/android/list-427-1.html")) {// 添加所有的分类
			List<String> urls = page
					.getHtml()
					.links("//div[@class='lstcatbox']")
					.regex("http://www\\.newasp\\.net/android/list-\\d{3}-1\\.html")
					.all();
			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urls);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}
		}
		if(page.getUrl().regex("http://www\\.newasp\\.net/android/list-\\d{3}-\\d+\\.html").match()){
			List<String> url_page = page.getHtml()
					.links("//div[@class='pagebox']").all();

			List<String> url_detail = page.getHtml()
					.links("//div[@class='soft_list_box']").all();

			url_page.addAll(url_detail);

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for (String temp : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			
			}
		}
		// 提取页面信息
		if (page.getUrl()
				.regex("http://www\\.newasp\\.net/android/\\d+\\.html")
				.match()) {

			Apk apk = Newasp_Detail.getApkDetail(page);

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
