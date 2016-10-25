package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 安卓软件园(3gyu)[中国] app搜索抓取
 * url:http://www.anzow.com/Search.shtml?stype=anzow&q=DOTA
 *
 * @version 1.0.0
 */
public class PageProAnzow_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProAnzow_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//dl[@class='down_info clear']/dd/div[@class='xiazai1'][1]/h1/text()").toString();
        String appCategory = html.xpath("//div[@class='crumbs fl']/a[3]/text()").get(); 
        String appSize = html.xpath("//dl[@class='down_info clear']/dd/dl/dt/ul/li[3]/text()").toString();
        String appVersion = html.xpath("//dl[@class='down_info clear']/dd/dl/dt/ul/li[4]/text()").toString();
        String osPlatform = html.xpath("//dl[@class='down_info clear']/dd/dl/dt/ul/li[6]/text()").toString();
        String appVenderName = html.xpath("//dl[@class='down_info clear']/dd/dl/dt/ul/li[8]/span/em/text()").toString();
        if(appVenderName != null && appVenderName.contains("作者") && appVenderName.contains("：") && !appVenderName.endsWith("："))
        	appVenderName = appVenderName.split("：")[1];
        else appVenderName = null;
        
        String appUpdateDate = html.xpath("//dl[@class='down_info clear']/dd/dl/dt/ul/li[9]/text()").toString();    
        String appDownloadUrl = html.xpath("//div[@class='contentdbtn']/a/@href").toString();
             
        String appType = null;
        String appDescription = html.xpath("//div[@class='down_intro']/text()").get();
        List<String> appScreenshot = html.xpath("//tr[@class='portal-item-screenshots']/td/img/@src").all();
        String appTag = StringUtils.join(html.xpath("//p[@class='keywords']/a/text()").all(), ",");
        
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;
        
//		System.out.println("appName="+appName);
//		System.out.println("appDetailUrl="+appDetailUrl);
//		System.out.println("appDownloadUrl="+appDownloadUrl);
//		System.out.println("osPlatform="+osPlatform);
//		System.out.println("appVersion="+appVersion);
//		System.out.println("appSize="+appSize);
//		System.out.println("appUpdateDate="+appUpdateDate);
//		System.out.println("appType="+appType);
//		
//		System.out.println("appDescription="+appDescription);
//		System.out.println("appTag="+appTag);
//		
//		System.out.println("appCategory="+appCategory);
//		
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
