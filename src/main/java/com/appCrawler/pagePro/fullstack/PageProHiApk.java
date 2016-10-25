package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProHiApk_Detail;
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
 * hiapk[中国] app搜索抓取
 * url:http://apk.hiapk.com/search?key=mt&pid=0
 * id:46
 * @version 1.0.0
 */
public class PageProHiApk implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProHiApk.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
    setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *id:46
     * @param page
     */
    @Override
    public Apk process(Page page) {
    	page.addTargetRequest("http://apk.hiapk.com/appinfo/com.StudioOnMars.CSPortable");
    	try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        LOGGER.debug("crawler url: {}", page.getUrl());
        if(page.getUrl().toString().equals("http://apk.hiapk.com/")){
        	page.addTargetRequest("http://apk.hiapk.com/games?sort=5&pi=1");
        	page.addTargetRequest("http://apk.hiapk.com/apps?sort=5&pi=1");
        	page.setSkip(true);
        }
        
        
        // 获取搜索页面
        if (page.getUrl().regex("http://apk\\.hiapk\\.com/apps\\?sort=5&pi=\\d+").match()
        		||page.getUrl().regex("http://apk\\.hiapk\\.com/games\\?sort=5&pi=\\d+").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().links("//ul[@id='appSoftListBox']").all();
            List<String> urlList2 = page.getHtml().links("//div[@class='page_box']").all();
            urlList.addAll(urlList2);
    		for (String temp : urlList) {
    			if(PageProUrlFilter.isUrlReasonable(temp) && !temp.contains("/appdown/"))				
    				page.addTargetRequest(temp);
    		}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }

        // 获取信息
        if (page.getUrl().regex("http://apk\\.hiapk\\.com/appinfo/.*").match()) {
            
			Apk apk = PageProHiApk_Detail.getApkDetail(page);
			
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
