package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 第一手机 app搜索抓取
 * url:http://www.1mobile.tw/index.php?c=search.json&keywords=MT
 *
 * @version 1.0.0
 */
public class PagePro1Mobile_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro1Mobile_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//h1[@class='d_app_title']/text()").toString();
        String appVersion = StringUtils.substringAfterLast(appName, " ");
        String appDownloadUrl = html.xpath("//p[@class='down_box']/a/@href").toString();
        String osPlatform = null;
        String appSize = null;
        String appUpdateDate = html.xpath("//span[@class='icon_time']/text()").get();
        
        String appType = null;

        String appDescription = html.xpath("//div[@class='detail_box']/text()").get();
        List<String> appScreenshot = html.xpath("//ul[@class='d_slider_list fl clearfix']/li/a/img/@src").all();
        String appTag = null;
        String appCategory = null;
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//span[@class='icon_donwloads']/text()").get();

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
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
    
    private static String getFormatUpdateDate(String updateString){
    	//updateString=11-04-2015
    	if(updateString == null) return null;
    	String yearString = StringUtils.substringAfterLast(updateString, "-");
    	String monthString = StringUtils.substringBefore(updateString, "-");
    	String dayString = StringUtils.substringBetween(updateString, "-", "-");
    	return yearString+"-"+monthString+"-"+dayString;
    	
    }
    public static void main(String[] args){
    	System.out.println(getFormatUpdateDate("11-04-2015"));
    }
}
