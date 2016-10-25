package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 机锋app搜索抓取
 * url:http://apk.gfan.com/search?q=keyword
 *
 * @version 1.0.0
 */
public class PageProgfanstore_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProgfanstore_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/h4[@class='curr-tit']/text()").toString().replace("&nbsp;", " ");
        String appVersion = StringUtils.substringAfterLast(html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/div[@class='app-infoAintro']/div[@class='app-info']/p[1]/text()").toString(), "：");
        String appVenderName = StringUtils.substringAfterLast(html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/div[@class='app-infoAintro']/div[@class='app-info']/p[2]/text()").toString(), "：");
        String appDownloadUrl = html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-left']/div[@class='app-view-bt']/a/@href").toString();
        String osPlatform = StringUtils.substringAfterLast(html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/div[@class='app-infoAintro']/div[@class='app-info']/p[6]/text()").toString(), "：");
        String appSize = StringUtils.substringAfterLast(html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/div[@class='app-infoAintro']/div[@class='app-info']/p[4]/text()").toString(), "：");
        String appUpdateDate = StringUtils.substringAfterLast(html.xpath("//div[@class='app-content clearfix']/div[@class='app-descr clearfix']/div[@class='descr-right']/div[@class='app-infoAintro']/div[@class='app-info']/p[3]/text()").toString(), "：");
        String appType = null;

        String appDescription = html.xpath("//div[@class='app-intro']/text()").get();
        List<String> appScreenshot = html.xpath("//ul[@id='appPhotos']/li/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//h3[@class='curr-site']/a[2]/text()").get();
        String appCommentUrl = null;
        String appComment = null;//html.xpath("//ul[@class='comment_list']").get();
        String dowloadNum = null;
        
        
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
            System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			System.out.println("appVenderName="+appVenderName);
			//System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			System.out.println("appScrenshot="+appScreenshot);
			System.out.println("appCategory="+appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
