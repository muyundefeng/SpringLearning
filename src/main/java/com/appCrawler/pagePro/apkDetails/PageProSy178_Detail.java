package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 搜狐应用中心[中国] app搜索抓取
 * url:http://shouyou.178.com/201506/227151016032.html
 * 
 * @version 1.0.0
 */
public class PageProSy178_Detail {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageProSy178_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='t1']/h1/text()").toString();      
        String appVersion = null;
        String appDownloadUrl = html.xpath("//div[@class='clearfix t2']/a[2]/@href").toString();
        String osPlatform = null; 
        		//html.xpath("//div[@class='clearfix inf']/p[2]/text()").toString();
      //  System.out.println("osPlatform"+osPlatform);
        String appSize = html.xpath("//div[@class='clearfix inf']/p[1]/text()").toString();
        	appSize = StringUtils.substringAfter(appSize, "大小：");
        String appUpdateDate = null;
        String appType = null;
        String appDescription = html.xpath("//div[@class='hidden']/p/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='hidden']/span/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='clearfix inf']/p[2]/text()").toString();
        	appCategory = StringUtils.substringBetween(appCategory, "类型：", "系统");
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = StringUtils.substringBetween(html.xpath("//div[@class='gy_02']/ul/li[1]/text()").toString(), "下载： ", "  ");;
        String appVenderName = html.xpath("//div[@class='clearfix inf']/p[2]/text()").toString();
        	appVenderName = StringUtils.substringBetween(appVenderName, "开发商：", "类型");
        
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
