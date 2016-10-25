package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.OppoApkPagePro_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #171 oppo手机软件：http://store.nearme.com.cn/ 伪造下载链接:
 * http://store.nearme.com.cn/product/download.html?id=588252&from=1135_-1
 * 将id和from后的参数进行处理
 * 
 * @author DMT
 *
 */

public class OppoApkPagePro implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes()).setSleepTime(1000);

	private Logger LOGGER = LoggerFactory.getLogger(OppoApkPagePro.class);

	public Apk process(Page page) {

		//page.addTargetRequest("http://store.oppomobile.com/product/0002/355/086_7.html?from=1142_1");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://store.nearme.com.cn/")) {
			page.addTargetRequest("http://store.oppomobile.com/product/category/12_8_1.html");// 添加游戏索引
			page.addTargetRequest("http://store.oppomobile.com/product/category/12_7_1.html");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://store\\.oppomobile\\.com/product/category/12_8_\\d+\\.html")
				.match()
				|| page.getUrl()
						.regex("http://store.oppomobile.com/product/category/12_7_\\d+\\.html")
						.match()) {// 获取所有分类页
			List<String> urlList = page
					.getHtml()
					.links("//div[@class='list_content']")
					.regex("http://store\\.oppomobile\\.com/product/\\d+/\\d+/\\d+_\\d+\\.html\\?from=\\d+_\\d+")
					.all();
			List<String> pageList = page.getHtml()
					.links("//ul[@class='yiiPager']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		if (page.getUrl()
				.regex("http://store\\.oppomobile\\.com/product/\\d+/\\d+/\\d+_\\d+\\.html\\?from=\\d+_\\d+")
				.match()) {
			Apk apk = null;
			try {
				apk = OppoApkPagePro_Detail.getApkDetail(page);
			} catch (OutOfMemoryError e) {
				LOGGER.info("processing "+page.getUrl().toString()+" error");
				System.out.println(page.getUrl().toString());
				e.printStackTrace();
				System.exit(0);
			}
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
