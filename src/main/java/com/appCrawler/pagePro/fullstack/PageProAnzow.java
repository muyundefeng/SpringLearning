package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
import com.appCrawler.pagePro.apkDetails.PageProAnzow_Detail;
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

/**
 * 安卓软件园(3gyu)[中国] app搜索抓取
 * url:http://www.anzow.com/Search.shtml?stype=anzow&q=DOTA
 * 10
 * @version 1.0.0
 */
public class PageProAnzow implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProAnzow.class);

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
        
    		try{
    			Thread.sleep(5000);
    		}
    		catch(Exception e){
    			
    		}
            List<String> urlList = page.getHtml().links().regex("http://www\\.anzow\\.com/.*").all();
           
    		for (String temp : urlList) {
    			if(PageProUrlFilter.isUrlReasonable(temp) && !temp.contains("http://www.anzow.com/download_app.shtml?files_id="))				
    				page.addTargetRequest(temp);
    		}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        

        // 获取信息
        if (page.getUrl().regex("http://www.anzow.com/download/.*").match()) {        
			Apk apk = PageProAnzow_Detail.getApkDetail(page);
			
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
