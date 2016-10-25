package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 苏宁开放平台 app搜索抓取
 * url:http://app.suning.com/android/app/page?pack=cn.ufuns.petspeech
 * 
 * @version 1.0.0
 */
public class PageProSuning_Detail {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageProSuning_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='clearfix']/dl/dd/h3/text()").toString();
        String appVersion = html.xpath("//div[@class='versionselect']/a/span/text()").toString();
        String appDownloadUrl = html.xpath("//div[@class='app-dlbtn']/span[3]/a/@href").toString();
        String osPlatform = html.xpath("//dl[@class='detail-main clearfix']/dd/p[4]/span/text()").toString();
        String appSize = html.xpath("//dl[@class='detail-main clearfix']/dd/p[1]/span/text()").toString();
        String appUpdateDate =  html.xpath("//dd[@class='middle']/p[2]/span/text()").toString();
        String appType = null;
        String appDescription = html.xpath("//dl[@class='apps-intro clearfix']/dd/p/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='app-marquee-list']/ul/li/img/@src").all();
        String appTag = null;
        String appCategory = null;
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//dl[@class='detail-main clearfix']/dd/p[2]/span/text()").toString();
        String appVenderName = html.xpath("//div[@class='company']/span/text()").toString();
        
        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCommentUrl(appCommentUrl);
            apk.setAppComment(appComment);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
            apk.setAppVenderName(appVenderName);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
