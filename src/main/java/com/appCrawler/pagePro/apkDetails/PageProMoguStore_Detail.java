package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 *	蘑菇市场[中国]
 * url:http://www.mogustore.com/
 * 时间：2015年12月28日10:09:47
 * @version 1.0.0
 */
public class PageProMoguStore_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProMoguStore_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='sub_title']/h1/text()").toString();
        String appDownloadUrl = html.xpath("//li[@class='l down']/a/@href").toString();
        String appVersion = html.xpath("//div[@class='sub_title']/ul/li[1]/text()").toString(); 
        String appVenderName = html.xpath("//div[@class='sub_title']/ul/li[3]/text()").toString(); 
        String appUpdateDate = html.xpath("//div[@class='sub_title']/ul/li[5]/text()").toString(); 
        String appDownloadedTime = html.xpath("//div[@class='sub_title']/ul/li[6]/text()").toString().replace(",", "");   
        String appSize = html.xpath("//div[@id='app_down']/ul/li[1]/text()").toString();        
        String osPlatform = html.xpath("//div[@id='app_down']/ul/li[2]/text()").toString();
        if(appSize.contains(" "))
        {
        	appSize=appSize.replace(" ", "");
        }
        String appType = null;
        String appDescription = html.xpath("//div[@class='app_txt']/div/text()").get();
        List<String> appScreenshot = html.xpath("//div[@id='app_pic']//img/@src").all();
        String appTag = null;
        String appCategory =html.xpath("//h2/a[3]/text()").toString();

        String appCommentUrl = null;
        String appComment = null;
        	System.out.println("appName="+appName);
     		 System.out.println("appDetailUrl="+appDetailUrl);
     		 System.out.println("appDownloadUrl="+appDownloadUrl);
     		 System.out.println("osPlatform="+osPlatform);
     		 System.out.println("appVersion="+appVersion);
     		 System.out.println("appSize="+appSize);
     		 System.out.println("appUpdateDate="+appUpdateDate);
     		 System.out.println("appType="+appType);
     		 System.out.println("appVenderName="+appVenderName);
     		 System.out.println("appDownloadedTime="+appDownloadedTime);
     		 System.out.println("appDescription="+appDescription);
     		 System.out.println("appTag="+appTag);
     		 System.out.println("appScreenshot="+appScreenshot);
     		 System.out.println("appCategory="+appCategory);

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppDownloadTimes(appDownloadedTime);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
            apk.setAppVenderName(appVenderName);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, appDownloadedTime, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
