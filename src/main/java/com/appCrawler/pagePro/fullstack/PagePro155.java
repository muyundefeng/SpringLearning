package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro155_Detail;
import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
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
 * 手游天下[中国] app搜索抓取
 * url:http://android.155.cn/search.php?kw=MT&index=soft
 *
 * @version 1.0.0
 */
public class PagePro155 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro155.class);

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

        try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(page.getUrl().toString().equals("http://android.155.cn/game/"))
        	page.addTargetRequest("http://android.155.cn/netgame/");
        
        // 获取遊戲索引頁面及每個索引的apk詳情鏈接
        if (page.getUrl().regex("http://android\\.155\\.cn/game/.*").match()
        	&& !page.getUrl().regex("http://android\\.155\\.cn/game/[0-9]+\\.html").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());
            // 获取分页链接
            List<String> urlList = page.getHtml().links("//div[@class='list_page']").regex("http://android\\.155\\.cn/game/index.*").all();
            // 获取详细链接
            List<String> urlList2 = page.getHtml().links("//div[@class='sof_r_center']").regex("http://android\\.155\\.cn/game/[0-9]+\\.html").all();
            urlList.addAll(urlList2);
           
    		for (String temp : urlList) {
    			if(PageProUrlFilter.isUrlReasonable(temp) && !temp.contains("http://android.155.cn/download.php?"))				
    				page.addTargetRequest(temp);
    		}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }
        //获取網遊索引頁面及每個索引的apk詳情鏈接
        else if(page.getUrl().regex("http://android.155.cn/netgame/").match()
        		&&  !page.getUrl().regex("http://wy\\.155\\.cn/spec[0-9]+/").match()){
        	 LOGGER.debug("match success, url:{}", page.getUrl());
             // 获取分页链接
             List<String> urlList = page.getHtml().links("//div[@class='list_page']").regex("http://android\\.155\\.cn/netgame/index.*").all();
             // 获取详细链接，以及分页链接
             List<String> urlList2 = page.getHtml().links("//div[@class='on_cent_left list_dl3']").regex("http://wy\\.155\\.cn/spec[0-9]+/").all();
             urlList.addAll(urlList2);
     		for (String temp : urlList) {
     			if(PageProUrlFilter.isUrlReasonable(temp) && !temp.contains("http://wy.155.cn/download.php?"))				
     				page.addTargetRequest(temp);
     		}

             // 打印搜索结果url
             LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        	
        		
        }

        // 获取信息
        if (page.getUrl().regex("http://android\\.155\\.cn/game/[0-9]+\\.html").match()
        	|| page.getUrl().regex("http://wy\\.155\\.cn/spec[0-9]+/").match()) {
     
		Apk apk = PagePro155_Detail.getApkDetail(page);
		
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
