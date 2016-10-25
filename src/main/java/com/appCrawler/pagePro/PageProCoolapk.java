package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProCncrk_Detail;
import com.appCrawler.pagePro.apkDetails.PageProCoolapk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
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
 * url:http://www.coolapk.com/search?q=qq
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

        // 获取搜索页面
        if (page.getUrl().regex("http://www\\.coolapk\\.com/search\\?q=.*").match()||page.getUrl().regex("http://www\\.coolapk\\.com/apk/search\\?q=.*").match()||page.getUrl().regex("http://www\\.coolapk\\.com/game/search\\?q=.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取应用详细链接，以及分页链接
            List<String> urlList = page.getHtml().links("//div[@id='apkSearchList']").regex("http://www\\.coolapk\\.com/apk/.*").all();
            urlList.addAll(page.getHtml().links("//ul[@class='media-list ex-card-app-list']").regex("http://www\\.coolapk\\.com/apk/.*").all());
            urlList.addAll(page.getHtml().links("//ul[@class='pagination']").regex("http://www\\.coolapk\\.com/apk/search\\?q=.*").all());
            
         // 获取游戏详细链接，以及分页链接
            urlList.addAll(page.getHtml().links("//div[@id='gameSearchList']").regex("http://www\\.coolapk\\.com/game/.*").all());
            urlList.addAll(page.getHtml().links("//ul[@class='media-list ex-card-app-list']").regex("http://www\\.coolapk\\.com/game/.*").all());
            urlList.addAll(page.getHtml().links("//ul[@class='pagination']").regex("http://www\\.coolapk\\.com/game/search\\?q=.*").all());
            
            Set<String> cacheSet = Sets.newHashSet();
    		cacheSet.addAll(urlList);

    				for (String temp : cacheSet) {
    					if(PageProUrlFilter.isUrlReasonable(temp)&& !temp.contains("http://www.coolapk.com/dl?pn="))
    								page.addTargetRequest(temp);
    				}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.coolapk\\.com/apk|game/.*").match()) {
        	return PageProCoolapk_Detail.getApkDetail(page);
        }

		return null;
    }

    /**
     * get the site settings
     *
     * @return site
     * @see us.codecraft.webmagic.Site
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
