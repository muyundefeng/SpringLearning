package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Kliton_Detail;
import com.appCrawler.pagePro.apkDetails.Vimoo_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #179 Kliton
 * 
 * http://www.kliton.com/download/l3_1/index.html
 * 
 * @author DMT
 *
 */
public class Kliton implements PageProcessor {

	Site site = Site.me().setCharset("gb2312")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Kliton.class);

	public Apk process(Page page) {
		if (page.getUrl().toString()
				.equals("http://www.kliton.com/download/l3_1/index.html")) {
			page.addTargetRequest("http://www.kliton.com/download/l18_1/index.html");
			page.addTargetRequest("http://www.kliton.com/download/l26_1/index.html");
		}

		if (page.getUrl()
				.regex("http://www\\.kliton\\.com/download/l18_\\d+/index\\.html")
				.match()
				|| page.getUrl()
						.regex("http://www\\.kliton\\.com/download/l26_\\d+/index\\.html")
						.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//div[@class='list_content']")
					.regex("http://www\\.kliton\\.com/download/.+").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page_nav']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		if (page.getUrl().regex("http://www\\.kliton\\.com/download/.*")
				.match()) {

			Apk apk = Kliton_Detail.getApkDetail(page);

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
