package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PagePro97sky_Detail;
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
 * 97sky[中国] app搜索抓取
 * url:http://www.97sky.cn/search?type=0&keyword=qq
 *
 * @version 1.0.0
 */
public class PagePro97sky implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro97sky.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
            setSleepTime(PropertiesUtil.getInterval());
    /**
     * 分页连接缓存，防止重复抓取
     */
    private Set<String> pageCache = Sets.newHashSet();

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://www.97sky.cn/search\\?type=0&sid=0&page=\\d[1]&keyword=.*").match()
    ||page.getUrl().regex("http://www.97sky.cn/search\\?type=0&keyword=.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> apps = page.getHtml().links("//li[@class='ppInCont']").all();
            apps.addAll(page.getHtml().links("//div[@class='ppPage']").all());


            Set<String> sets = Sets.newHashSet(apps);
            for (String url : sets) {
                if (PageProUrlFilter.isUrlReasonable(url)) {
                    page.addTargetRequest(url);
                }
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.97sky\\.cn/soft/.*").match()) {
        	return PagePro97sky_Detail.getApkDetail(page);

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
