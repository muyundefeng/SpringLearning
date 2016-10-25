package com.appCrawler.pagePro.fullstack;


import com.appCrawler.pagePro.apkDetails.PageProOnlineDown_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

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
 * 华军软件园[中国] app搜索抓取
 * ID：23
 * 需要两次请求
 * @version 1.0.0
 */
public class PageProOnlineDown implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProOnlineDown.class);

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
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        // 获取搜索页面      http://www.onlinedown.net/sort/195_1.htm
            LOGGER.debug("match success, url:{}", page.getUrl());
         if(page.getUrl().regex("http://www\\.onlinedown\\.net/sort.*").match()){
            	List<String> urlList1 = page.getHtml().links("//dl[@class='sel_item']").all();
            	List<String> urlList2 = page.getHtml().links("//div[@id='app_page']").all();
            	List<String> urlList_appDetail = page.getHtml().links("//div[@class='left_box']").all();
            	for (String string : urlList_appDetail) {
            		if(string.contains("/soft/")){
                		
                		urlList1.addAll(urlList2);
                	
                		urlList1.addAll(urlList_appDetail);
                	}
					
				}
            	
            	
            Set<String> sets = Sets.newHashSet(urlList1);
            for (String url : sets) {
                if (PageProUrlFilter.isUrlReasonable(url)) {
                    page.addTargetRequest(url);
                }
            }
            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
            }
            
               
            if (page.getUrl().regex("http://www\\.onlinedown\\.net/soft/.*").match()) {
            
    			Apk apk = PageProOnlineDown_Detail.getApkDetail(page);
    			
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