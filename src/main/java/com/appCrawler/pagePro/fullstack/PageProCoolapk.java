package com.appCrawler.pagePro.fullstack;



import com.appCrawler.pagePro.apkDetails.PageProCoolapk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.http.Header;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 酷安网 app搜索抓取
 * url:http://www.coolapk.com/
 * id:185
 * @version 1.0.0
 */
public class PageProCoolapk implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCoolapk.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

    	try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	page.addTargetRequest("http://www.coolapk.com/game/sh.lilith.dgame.s360");
		if (page.getUrl().toString().equals("http://www.coolapk.com/")) {
			page.addTargetRequest("http://www.coolapk.com/apk/?p=1");// 添加应用索引

		}

		if (page.getUrl()
				.regex("http://www\\.coolapk\\.com/apk/\\?p=\\d+")
				.match()
				) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//ul[@class='media-list ex-card-app-list']")
					.regex("http://www\\.coolapk\\.com/apk/.+").all();
			List<String> pageList = page.getHtml()
					.links("//ul[@class='pagination']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

		
        // 获取信息	http://www.coolapk.com/apk/cn.com.pcauto.android.browser
        if(page.getUrl().regex("http://www\\.coolapk\\.com/apk/[a-z\\.]+").match()
        		|| page.getUrl().regex("http://www\\.coolapk\\.com/game/[a-z\\.]+").match()) {
			Apk apk = PageProCoolapk_Detail.getApkDetail(page);
			
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
			}
		}else{
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
