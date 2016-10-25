package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProPc6_Detail;
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
 * PC6安卓网[中国] app搜索抓取
 * url:http://www.pc6.com/android/465_1.html,http://www.pc6.com/andyx/466_1.html
 *
 * @version 1.0.0
 */
public class PageProPc6 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProPc6.class);

    private static int flag=1;
    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("gb2312").setRetryTimes(2).setSleepTime(3);

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());
 //       System.out.println(page.getHtml().toString());

        // 获取搜索页面 http://s.pc6.com/cse/search?s=12026392560237532321&entry=1&ie=gbk&q=qq
        if (page.getUrl().regex("http://s\\.pc6\\.com/cse/search\\?.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());
           // System.out.println(page.getHtml().toString());
            // 获取详细链接，以及分页链接  http://www.pc6.com/softview/SoftView_67879.html
            List<String> urlList = page.getHtml().links("//div[@class='content-main']").all();
   //         urlList.addAll(page.getHtml().links("//div[@class='tsp_nav']").all());
            List<String> pages=page.getHtml().xpath("//div[@id='pageFooter']/a/@href").all();
            if(flag==1)
            {
            	if(pages.size()>4)
            	{
            		for(int i=0;i<4;i++)
            		{
            			page.addTargetRequest(pages.get(i));
            		}
            	}
            	else{
            		page.addTargetRequests(pages);
            	}
            	flag++;
            }
            
            Set<String> sets = Sets.newHashSet(urlList);
            for (String url : sets) {
                if (PageProUrlFilter.isUrlReasonable(url) && !url.contains("down.asp")) {
                    page.addTargetRequest(url);
                }
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        //String appName = page.getHtml().xpath("//div[@class='fixed']/h1/text()").toString();
        // 获取信息
        if (page.getUrl().regex("http://www\\.pc6\\.com/azyx/.*").match()||page.getUrl().regex("http://www\\.pc6\\.com/az/.*").match()||page.getUrl().regex("http://www\\.pc6\\.com/ku/.*").match()) {
        	return PageProPc6_Detail.getApkDetail(page);

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
		return null;
	}
}
