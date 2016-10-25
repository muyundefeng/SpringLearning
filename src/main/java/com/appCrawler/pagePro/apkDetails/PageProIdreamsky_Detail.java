package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.LinkedList;
import java.util.List;

/**
 * idreamsky 
 * url:http://www.idreamsky.com/games/index/page/1
 * id:48
 * @version 1.0.0
 */
public class PageProIdreamsky_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProIdreamsky_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//p[@class='game-name']/strong/text()").toString();
        String appDownloadUrl = html.xpath("//a[@class='dl-btn']/@href").toString();
        String appCategory = html.xpath("//p[@class='crumbs']/a[2]/text()").get();
        
        String appSize = html.xpath("//div[@class='base']/p[3]/text()").toString().replace("游戏大小：", "");
        String dowloadNum = html.xpath("//div[@class='base']/p[6]/span/text()").toString().replace("万", "0000");
        String appVersion = null;
        List<String> appScreenshot2 = html.xpath("//div[@class='pic-list']//img/@src").all();
        List<String> appScreenshot = new LinkedList<String>();
        for (String string : appScreenshot2) {
			appScreenshot.add("http://www.idreamsky.com"+string);
		}
        String appDescription = html.xpath("//div[@class='t-info]/p[2]/text()").get();
        
        String osPlatform = null;
        
        String appUpdateDate =null;
        String appType = null;

        
        
        String appTag = null;
        
        String appCommentUrl = null;
             

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
