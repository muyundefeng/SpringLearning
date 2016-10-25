package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProSinaTech_Detail;
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
 * 新浪手机软件[中国] app搜索抓取
 * url:http://down.tech.sina.com.cn/3gsoft/iframelist.php?classid=0&keyword=QQ&tag=&osid=&order=&page=2
 *
 * @version 1.0.0
 */
public class PageProSinaTech implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSinaTech.class);

    private static int flag=1;
    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
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
        if (page.getUrl().regex("http://down\\.tech\\.sina\\.com\\.cn/3gsoft/iframelist\\.php.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().links("//div[@class='blk_tw clearfix']/").all();
            List<String> pages=page.getHtml().links("//div[@class='pagebox']/span").all();
            System.out.println(pages);
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
                if (PageProUrlFilter.isUrlReasonable(url))
					{
                    page.addTargetRequest(url);
                }
            }

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://down\\.tech\\.sina\\.com\\.cn/3gsoft/download.*").match()) {
        	return PageProSinaTech_Detail.getApkDetailForFullStack(page);


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
