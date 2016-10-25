package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 *安族网 app搜索抓取
 * url:http://m.apkzu.com/
 *
 * @version 1.0.0
 */
public class PageProapkzu_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProapkzu_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='gamexx']/p/span[1]/text()").toString();
        String appVersion = null;
        String appDownloadUrl = html.xpath("//div[@class='gamexx']/a/@href").toString();
        String osPlatform = null;
        String appSize = StringUtils.substringAfter(html.xpath("//div[@class='gamexx']/p/span[3]/text()").toString(),"大小： ") ;
        String appUpdateDate =null;
        String appType = null;
        String appDescription = html.xpath("//div[@class='gamejs']/p[2]/text()").get();
        List<String> appScreenshot = html.xpath("//ul[@class='shot-ul clearfix']/li/img/@data-original").all();
        String appTag = null;
        String appCategory =StringUtils.substringAfter(html.xpath("//div[@class='gamexx']/p/span[2]/text()").toString(),"类型：") ;

        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl && appDownloadUrl.contains("down/apk.html")) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
