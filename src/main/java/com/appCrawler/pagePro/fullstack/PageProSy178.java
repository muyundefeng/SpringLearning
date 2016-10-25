package com.appCrawler.pagePro.fullstack;

import com.appCrawler.pagePro.apkDetails.PageProSy178_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.util.List;
import java.util.Set;

/**
 * 搜狐应用中心[中国] app搜索抓取
 * url:http://shouyou.178.com/201506/227151016032.html
 * 
 * @version 1.0.0
 */
public class PageProSy178 implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSy178.class);

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
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (page.getUrl()
				.regex("http://shouyou\\.178\\.com/list/android_\\d+\\.html")
				.match()
				|| page.getUrl().toString().equals("http://shouyou.178.com/list/android.html")
				) {// 获取所有分类页
			List<String> urlList = page.getHtml()
					.links("//div[@class='tab-content']")
					.regex("http://shouyou\\.178\\.com/\\d+/\\d+\\.html").all();
			List<String> pageList = page.getHtml()
					.links("//div[@class='page']").all();

			Set<String> cacheSet = Sets.newHashSet();
			cacheSet.addAll(urlList);
			cacheSet.addAll(pageList);
			for (String url : cacheSet) {
				if (PageProUrlFilter.isUrlReasonable(url)) {
					page.addTargetRequest(url);
				}
			}

		}

        // 获取信息	http://shouyou.178.com/201509/235877987757.html
        if (page.getUrl().regex("http://shouyou\\.178\\.com/\\d+/\\d+\\.html").match()) {
			Apk apk = PageProSy178_Detail.getApkDetail(page);
			
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
