package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 玩家网[中国] app搜索抓取
 * url:http://down.cngba.com/mobile/android/
 * id:183
 * @version 1.0.0
 */
public class PageProCngba_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCngba_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='page_location']/text()").toString();
        System.out.println("appName"+appName);
        String appVersion = null;
        String appDownloadUrl = html.xpath("//div[@class='down_url_box']/dl/dt[1]/a[1]/@href").toString();
        String osPlatform = html.xpath("//div[@class='rightcolumn']/div[@id='softinfo']/ul/li[4]/text()").toString();
        String appSize = html.xpath("//div[@class='rightcolumn']/div[@id='softinfo']/ul/li[1]/text()").toString();
        String appUpdateDate = html.xpath("//div[@class='rightcolumn']/div[@id='softinfo']/ul/li[7]/text()").toString();
        String appType = null;

        String appDescription = html.xpath("//div[@id='softintro]/text()").get();
        List<String> appScreenshot = html.xpath("//div[@id='Pic_show']/div/a/img/@src").all();
        String appTag = StringUtils.join(html.xpath("//div[@class='rightcolumn']/div[@id='softinfo']/ul/li[8]/a/text()").all(), ",");
        String appCategory = html.xpath("//div[@class='currentnav']/a[3]/text()").get();
        String appCommentUrl = null;
       // String appComment = html.xpath("//div[@id='comment']").get();
        String dowloadNum = null;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCommentUrl(appCommentUrl);
        //    apk.setAppComment(appComment);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{},appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot,appDescription);

        return apk;
    }
}
