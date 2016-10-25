package com.appCrawler.pagePro.fullstack;


import com.appCrawler.pagePro.apkDetails.PageProCncrk_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 起点下载[中国] app搜索抓取

 * id:13
 * @version 1.0.0
 */
public class PageProCncrk implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCncrk.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setCharset("UTF-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        if("http://www.cncrk.com/anzhuo/".equals(page.getUrl().toString()))
        {
        	//System.out.println(page.getRawText());
        	//System.out.println(page.getHtml());
        	//String html=SinglePageDownloader.getHtml("http://www.cncrk.com/shouji/r_17_1.html");
        	//Html html1=Html.create(html);
        	//System.out.println(html1);
        	page.addTargetRequest("http://www.cncrk.com/shouji/r_17_1.html");
        	page.addTargetRequest("http://www.cncrk.com/shouji/r_18_1.html");
        	return null;
        }
        if (page.getUrl().regex("http://www.cncrk.com/shouji/.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());

            //System.out.println(page.getRawText());
            // 获取详细链接，以及分页链接
            List<String> urlList = page.getHtml().links("//ul[@class='ul-pic-w4 ul-pic--w4 tab-1 tab-2']").all();
            List<String> urlList2 = page.getHtml().links("//div[@class='pages']").all();
            //System.out.println(urlList);
            page.addTargetRequests(urlList2);
            page.addTargetRequests(urlList);
        }

        // 获取信息
        if (page.getUrl().regex("http://www.cncrk.com/downinfo/*").match() ) {
            //Html html = page.getHtml();
           // System.out.println(html);
			Apk apk = PageProCncrk_Detail.getApkDetail(page);
			
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
