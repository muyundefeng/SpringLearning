package com.appCrawler.pagePro.apkDetails;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

/**
 * 微博应用中心 app搜索抓取
 * url:http://app.sina.com.cn/
 * ID：180
 * @version 1.0.0
 */

public class PageProAppSina_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProAppSina_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='avCard']/h1/text()").toString();
        String appVersion = StringUtils.substringAfter(html.xpath("//ul[@class='avInfoList']/li[2]/text()").toString(),"版本：");
        String appDownloadUrl = html.xpath("//div[@class='avAction']/a[1]/@href").toString();
        String osPlatform = StringUtils.substringAfter(html.xpath("//ul[@class='avInfoList']/li[4]/text()").toString(),"系统要求：");
        String appSize =StringUtils.substringAfter(html.xpath("//ul[@class='avInfoList']/li[1]/text()").toString(),"大小：");   
        String appUpdateDate =StringUtils.substringAfter(html.xpath("//ul[@class='avInfoList']/li[3]/text()").toString(),"更新时间：");
        String appType = null;

        String appDescription = html.xpath("//p[@id='description_p']/text()").toString();
        List<String> appScreenshot = html.xpath("//ul[@class='avcList']/li/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='mHd']/h3/a[3]/text()").toString();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//span[@class='downValue']/text()").toString();

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}

