package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PagePro7xz_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.deser.FromStringDeserializer;
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
 * 七匣子[中国] app搜索抓取
 * url:http://www.7xz.com/ng/search_MT_0_0_rate_1.html
 *
 * @version 1.0.0
 */
public class PagePro7xz implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro7xz.class);

    private static int flag=1;
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
        if (page.getUrl().regex("http://www\\.7xz\\.com/search\\.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> apps = page.getHtml().xpath("//ul[@class='game-index']/li/a/@href").all();
            System.out.println(apps);
            List<String> pages = page.getHtml().xpath("//ul[@class='pagination']/li/a/@href").all();
            System.out.println(pages);
            if(flag==1)
            {
            	if(pages.size()>5)
            	{
            		for(int i=0;i<5;i++)
            		{
            			page.addTargetRequest(pages.get(i));
            		}
            	}
            	else{
            		page.addTargetRequests(pages);
            	}
            	flag++;
            }
            Set<String> cacheSet = Sets.newHashSet();
    		cacheSet.addAll(apps);

    				for (String temp : cacheSet) {
    					if(PageProUrlFilter.isUrlReasonable(temp)
    						&& !temp.contains("http://www.7xz.com/apk?pkg")
    						&& !temp.contains("http://www.7xz.com/ndl?show"))
    								page.addTargetRequest(temp);
    				}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

     // 获取信息
        if (page.getUrl().regex("http://www\\.7xz\\.com/.*").match()) {
        	return PagePro7xz_Detail.getApkDetail(page);

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
