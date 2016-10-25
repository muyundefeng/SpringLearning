package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PagePro360_Detail;
import com.appCrawler.pagePro.apkDetails.PageProSoGou_Detail;
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
 * 搜狗市场[中国] app搜索抓取
 * url:http://app.sogou.com/search?title=MT
 * 29
 * @version 1.0.0
 */
public class PageProSoGou implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSoGou.class);

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
    	try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
      
            List<String> urlList = page.getHtml().links().regex("http://app\\.sogou\\.com/.*").all();
          

    		for (String temp : urlList) {
    			if(PageProUrlFilter.isUrlReasonable(temp))				
    				page.addTargetRequest(temp);
    		}

        // 获取信息
        if (page.getUrl().regex("http://app.sogou.com/detail/.*").match()) {
            Html html = page.getHtml();
			Apk apk = PageProSoGou_Detail.getApkDetail(page);
			
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
