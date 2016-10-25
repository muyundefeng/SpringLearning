package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Zol_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * zol手机应用 http://sj.zol.com.cn/mobilesoft/ Zol #99 修改索引页 2015年11月5日10:58:52
 * 
 * @author DMT
 */

public class Zol implements PageProcessor {

	Site site = Site.me().setCharset("gb2312")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Zol.class);

	public Apk process(Page page) {
		page.addTargetRequest("http://sj.zol.com.cn/detail/35/34547.shtml");
		if (page.getUrl().toString()
				.equals("http://sj.zol.com.cn/mobilesoft/page_1.html")) {
			page.addTargetRequest("http://sj.zol.com.cn/android_game/page_1.html");
			page.addTargetRequest("http://sj.zol.com.cn/android_app/page_1.html");
		}
		if (page.getUrl()
				.regex("http://sj\\.zol\\.com\\.cn/android_game/page_\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://sj\\.zol\\.com\\.cn/android_app/page_\\d+\\.html")
						.match()) {
			List<String> url_page = page.getHtml()
					.links("//div[@class='page-box']").all();

			List<String> url_detail = page.getHtml()
					.links("//ul[@class='soft-list clearfix']").all();

			url_page.addAll(url_detail);

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(url_page);
			for (String temp : cacheSet) {
				if (!temp.contains("http://sj.zol.com.cn/down.php?softid=")
						&& PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
			}

		}
		// 提取页面信息
		// 正则匹配有问题，http://sj\\.zol\\.com\\.cn/.+/ 能够匹配到不是以/结尾的，因此修改为如下形式
		if (page.getUrl().toString().endsWith("/")
				|| page.getUrl()
						.regex("http://sj\\.zol\\.com\\.cn/detail/\\d+/\\d+\\.shtml")
						.match()) {
			Apk apk = Zol_Detail.getApkDetail(page);
			
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
