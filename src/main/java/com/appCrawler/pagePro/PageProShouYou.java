package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProShouYou_Detail;
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
 * 手游网[中国] app搜索抓取
 * url:http://search.shouyou.com/online?keyword=ME&type=1
 *
 * @version 1.0.0
 */
public class PageProShouYou implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProShouYou.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
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
        if (page.getUrl().regex("http://search.shouyou.com/online\\?*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().xpath("//div[@class='result-classes']/ul/li/div/div[@class='classes-list-c2']/p/a[@class='and']/@href").all();
            urlList.addAll(page.getHtml().links("//div[@class='pager']/ul/li").all());

            Iterator<String> iter = Sets.newHashSet(urlList).iterator();
            while (iter.hasNext()) {
            	String url = iter.next();
            	if(url.matches("http://a\\.shouyou\\.com/info.*"))
            		{
            		url= StringUtils.substringAfterLast(url, "/");            		
            		url="http://newgame.shouyou.com/"+url;
            		}
            	//System.out.println("url"+url);
            	if(PageProUrlFilter.isUrlReasonable(url))
           		page.addTargetRequest(url);
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

     // 获取信息
        if (page.getUrl().regex("http://online\\.shouyou\\.com/info.*").match() || page.getUrl().regex("http://a\\.shouyou\\.com/info.*").match()|| page.getUrl().regex("http://newgame\\.shouyou\\.com/info.*").match()) {
        	return PageProShouYou_Detail.getApkDetail(page);			

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
