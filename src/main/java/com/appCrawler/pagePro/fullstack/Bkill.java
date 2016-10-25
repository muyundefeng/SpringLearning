package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Bkill_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 必杀客 http://www.bkill.com Bkill #136
 * 
 * @author DMT
 */

public class Bkill implements PageProcessor {

	Site site = Site.me().setCharset("gb2312")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Bkill.class);

	public Apk process(Page page) {
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if (page.getUrl()
				.regex("http://www\\.bkill\\.com/download/list-9-\\d+\\.html")
				.match()) {

			List<String> urlList = page.getHtml()
					.links("//div[@class='clsList']/dl")
					.regex("http://www.bkill.com/download/[0-9]+\\.html")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page']")
					.regex("http://www\\.bkill\\.com/download/list-9-\\d+\\.html")
					.all();

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
		if (page.getUrl().regex("http://www.bkill.com/download/[0-9]+\\.html")
				.match()) {

			Apk apk = Bkill_Detail.getApkDetail(page);

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
