package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro7xz_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 七匣子[中国] app搜索抓取
 * url:http://www.7xz.com/ng/search_MT_0_0_rate_1.html
 * id:42
 * @version 1.0.0
 */
public class PagePro7xz implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro7xz.class);

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
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
        if(page.getUrl().toString().equals("http://www.7xz.com/")){
        	page.addTargetRequest("http://www.7xz.com/apps?sort=rate");
        	page.addTargetRequest("http://www.7xz.com/softs/search");
        	return null;
        	
        }
        
        
        
        // 获取搜索页面  索引分类1 http://www.7xz.com/xinyou/1.html   索引分类2 http://www.7xz.com/apps/1?sort=created
        if (page.getUrl().regex("http://www\\.7xz\\.com/softs/.*").match()
        		|| page.getUrl().regex("http://www\\.7xz\\.com/apps/\\d+\\?sort=rate").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().links("//ul[@class='row gamelist clearfix mg10']").regex("http://www\\.7xz\\.com/.*/").all();
            
            List<String> pageList = page.getHtml().links("//ul[@class='pagination']").all();
            
            List<String> apps=page.getHtml().xpath("//ul[@class='game-index']/li/a/@href").all();
            
            
            urlList.addAll(pageList);
            urlList.addAll(apps);
            
            Set<String> cacheSet = Sets.newHashSet();
    		cacheSet.addAll(urlList);

    		for (String temp : cacheSet) {   //http://www.7xz.com/ndl?text=xianguozhi&add=/apps/
    			if(PageProUrlFilter.isUrlReasonable(temp)
    					&& !temp.contains("http://www.7xz.com/apk?")
    					&& !temp.contains("http://www.7xz.com/ndl?"))
    						page.addTargetRequest(temp);
    			}

            // 打印搜索结果url
            LOGGER.debug("app info results urls: {}", page.getTargetRequests());
        }
        
        

        // 获取信息
        if (page.getUrl().regex("http://www\\.7xz\\.com/.*").match()) {
            Apk apk = PagePro7xz_Detail.getApkDetail(page);

            page.putField("apk", apk);
            if (page.getResultItems().get("apk") == null) {
                page.setSkip(true);
            }
        }
        else {
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
