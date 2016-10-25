package com.appCrawler.pagePro;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import com.appCrawler.pagePro.apkDetails.PageProDjw_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

/**
 * 大家玩 app搜索抓取
 * url:http://android.dajiawan.com/
 *id:182
 * @version 1.0.0
 */
public class PageProDjw implements PageProcessor{

	 // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProDjw.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://android\\.dajiawan\\.com/.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            List<String> urlList = page.getHtml().links("//div[@class='game_left']/dl/dt").all();
            urlList.addAll(page.getHtml().links("//div[@class='page']").all());
            Set<String> cacheSet = Sets.newHashSet();
    		cacheSet.addAll(urlList);

    				for (String temp : cacheSet) {
    					if(PageProUrlFilter.isUrlReasonable(temp))
    								page.addTargetRequest(temp);
    				}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://android\\.dajiawan\\.com/game|soft|online.*").match()) {
        	return PageProDjw_Detail.getApkDetail(page);
			
			
		}

		return null;
   }

    /**
     * get the site settings
     *
     * @return site
     * @see us.codecraft.webmagic.Site
     */

    public Site getSite() {
        return site;
    }


	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
}

