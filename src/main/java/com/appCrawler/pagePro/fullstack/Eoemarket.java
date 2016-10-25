package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Eoemarket_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * 优亿市场[中国] http://partner.eoemarket.com/qq/bibei/index/ Eoemarket #666
 * 该网站下载apk时电脑必须安装类似于应用包之类的手机管理软件，点击下载apk后电脑自动启动手机管理软件进行下载 不过可以构造下载链接，但是太过复杂
 * ·通过抓包获取的下载
 * ：http://d2.eoemarket.com/app0/17/17896/apk/721873.apk?channel_id=401
 * ·页面中存在的链接1：http://c11.eoemarket.com/app0/17/17896/icon/721873.png
 * ·页面中存在的链接2：http
 * ://download.eoemarket.com/app?id=17896%26client_id=146%26channel_id
 * =401%26track=pc_qq_show_index_app17896_2 通过后两个链接拼接成下载链接
 * 
 * @author buildhappy&DMT
 */

public class Eoemarket implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Eoemarket.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://partner.eoemarket.com/qq")) {
			page.addTargetRequest("http://partner.eoemarket.com/qq/categories/index/category_id/2?&pageNum=1");// 添加游戏索引
			page.addTargetRequest("http://partner.eoemarket.com/qq/categories/index/category_id/1?&pageNum=1");// 添加应用索引

		}
//http://partner.eoemarket.com/qq/categories/index/category_id/1?&pageNum=4
		if (page.getUrl().regex("http://partner\\.eoemarket\\.com/qq/categories/index/category_id/2\\?&pageNum=\\d+")
				.match()
				|| page.getUrl()
						.regex("http://partner\\.eoemarket\\.com/qq/categories/index/category_id/1\\?&pageNum=\\d+")
						.match()) {// 获取所有分类页	
			List<String> urlList = page.getHtml()
					.links("//ul[@class='mainlist rlist']")
					.regex("http://partner\\.eoemarket\\.com/qq/show/index/appId/\\d+")
					.all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='pageinner']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url) ) {
					page.addTargetRequest(url);
				}
			}

		}

		if (page.getUrl()
				.regex("http://partner\\.eoemarket\\.com/qq/show/index/appId/\\d+")
				.match()) {

			Apk apk = Eoemarket_Detail.getApkDetail(page);

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
