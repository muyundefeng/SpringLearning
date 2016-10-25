package com.appCrawler.pagePro;

import com.appCrawler.pagePro.apkDetails.PageProDowng_Detail;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.Iterator;
import java.util.List;

/**
 * 绿色软件站 app搜索抓取
 * url:http://www.baidu.com/s?si=www.downg.com!%!%!%cl=3!%!%!%ct=2097152!%!%!%tn=baidulocal!%!%!%word=*#*#*#
 * 搜索结果页面打开很慢  2015年7月21日20:22:03
 * @version 1.0.0
 */
public class PageProDowng implements PageProcessor {

    // 日志管理对象
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProDowng.class);

    // 定义网站编码，以及间隔时间
    Site site = Site.me().setRetryTimes(2).setSleepTime(3);

    /**
     * process the page, extract urls to fetch, extract the data and store
     *
     * @param page
     */
    @Override
    public Apk process(Page page) {
        LOGGER.debug("crawler url: {}", page.getUrl());

        // 获取搜索页面
        if (page.getUrl().regex("http://www.baidu.com/s.*").match()) {
            LOGGER.debug("match success, url:{}", page.getUrl());
            		
            List<String> apps=page.getHtml().links("//td[@class='td']").all();
            List<String> pages=page.getHtml().links("//ol").all();
            page.addTargetRequests(apps);
            page.addTargetRequests(pages);
           
        }

        // 获取信息
        if (page.getUrl().regex("http://www\\.downg\\.com/soft/.*").match()) {
            // 获取dom对象
//            Html html = page.getHtml();
//
//            // 找出对应需要信息
//            String appDetailUrl = page.getUrl().toString();
//            String appName = html.xpath("//div[@class='cp software-info']/div[@class='cp-top']/h3/text()").toString();
//            String appVersion = null;
//            String appDownloadUrl = html.xpath("//ul[@class='download-list']/li[3]/a/@href").get();
//            String osPlatform = StringUtils.substringAfterLast(html.xpath("//ul[@class='clearfix software-infolist']/li[@class='span2'][1]/text()").get(), "：");
//            String appSize = StringUtils.substringAfterLast(html.xpath("//ul[@class='clearfix software-infolist']/li[4]/text()").get(), "：");
//            String appUpdateDate = StringUtils.substringAfterLast(html.xpath("//ul[@class='clearfix software-infolist']/li[2]/text()").get(), "：");
//            String downloadNum = null;
//            String appDesc = html.xpath("//div[@class='cp software-desc']/div[@class='cp-main']/text()").get();
//            String appType = null != appDownloadUrl ? StringUtils.substringAfterLast(appDownloadUrl, ".") : null;
//
//            LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, appDesc:{}", appName, appVersion, appDownloadUrl, appSize, appType, osPlatform, appUpdateDate, appDesc);
//
//            if (null != appName && null != appDownloadUrl) {
//                Apk apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
//                apk.setAppDescription(appDesc);

                return PageProDowng_Detail.getApkDetail(page);
            
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

    public static void main(String[] args) {
    }
}
