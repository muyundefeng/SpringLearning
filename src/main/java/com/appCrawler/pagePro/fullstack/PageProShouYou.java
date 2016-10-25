package com.appCrawler.pagePro.fullstack;


import com.appCrawler.pagePro.apkDetails.PageProShouYou_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 手游网[中国] app搜索抓取
 * url:http://search.shouyou.com/online?keyword=ME&type=1
 *	网站页面里会带有搜索关键词的链接，需要过滤掉
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
        try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        // 获取搜索页面
        if (page.getUrl().regex("http://a\\.shouyou\\.com/list-0-0-3-0-1-0-0\\.html\\?page.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接
            List<String> urlList = page.getHtml().links("//ul[@class='gb-list3 games-list2 games-list2-ex']").regex("http://a\\.shouyou\\.com/info.*").all();
            // 获取分页链接
            List<String> urlList2 = page.getHtml().links("//div[@class='pager-box']").regex("http://a\\.shouyou\\.com/list-0-0-3-0-1-0-0\\.html\\?page.*").all();;
          
            urlList.addAll(urlList2);
            
            Set<String> cacheSet = Sets.newHashSet();
    		cacheSet.addAll(urlList);
    		for(String url : cacheSet){
    			if(PageProUrlFilter.isUrlReasonable(url)){
    				page.addTargetRequest(url);
    			}
    		}
            
            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://online\\.shouyou\\.com/info.*").match() || page.getUrl().regex("http://a\\.shouyou\\.com/info.*").match()|| page.getUrl().regex("http://newgame\\.shouyou\\.com/info.*").match()) {
			Apk apk = PageProShouYou_Detail.getApkDetail(page);			
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
