package com.appCrawler.pagePro.fullstack;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Android173Sy_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * #169 手游世界http://android.173sy.com/ 可以通过伪造下载链接来构造下载链接
 * http://android.173sy.com/download/downloadapk.php?id=13425&s=1
 * 将id后的参数修改成相应的apk的id就好
 * 
 * @author DMT
 *
 */

public class Android173Sy implements PageProcessor {

	Site site = Site.me().setCharset("utf-8")
			.setRetryTimes(PropertiesUtil.getRetryTimes())
			.setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (page.getUrl().toString().equals("http://android.173sy.com")) {
			page.addTargetRequest("http://android.173sy.com/games/clickgames.php?t=0&s=0&p=1");// 添加单击手游索引
			page.addTargetRequest("http://android.173sy.com/games/netgames.php?s=0&p=1");// 添加网络手游索引

		}

		if (page.getUrl()
				.regex("http://android\\.173sy\\.com/games/clickgames\\.php\\?t=0&s=0&p=\\d+")
				.match()
				|| page.getUrl()
						.regex("http://android\\.173sy\\.com/games/netgames\\.php\\?s=0&p=\\d+")
						.match()) {// 获取所有分类页
			List<String> urlList = page.getHtml().links("//div[@id='d_now']/ul").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page tac pt_13']")
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

		// 提取页面信息 http://android.173sy.com/games/detail.php?gid=14322
		if (page.getUrl().regex("http://android.173sy.com/games/detail.*")
				.match()) {

			Apk apk = Android173Sy_Detail.getApkDetail(page);

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
