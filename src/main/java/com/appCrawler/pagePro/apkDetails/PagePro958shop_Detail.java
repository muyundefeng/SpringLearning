package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 百信众联[中国] app搜索抓取
 * url:http://www.958shop.com/apk/search.aspx?wd=MT
 *
 * @version 1.0.0
 */
public class PagePro958shop_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro958shop_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='soft-summary clearfix']/h1/strong/text()").toString();
        if(appName == null) return null;
        String appVersion = null;
        if(appName.contains("v")) 
        	{
        		appVersion = appName.split("v")[1];
        		appName = appName.split("v")[0];
        	}
        else if(appName.contains("V")) {
        	appVersion = appName.split("V")[1];
        	appName = appName.split("V")[0];
        }
        //String appDownloadUrl = StringUtils.substringBetween(html.get(), "window.location.href=\"", "\";");
        String appDownloadUrl = html.xpath("//div[@class='down-button-box h-down-box']/a/@href").toString();
        String osPlatform = html.xpath("//div[@class='soft-summary clearfix']/ul[@class='soft-infor']/li[3]/text()").toString();
        String appSize = html.xpath("//div[@class='soft-summary clearfix']/ul[@class='soft-infor']/li[1]/text()").toString();
        String appUpdateDate = html.xpath("//div[@class='soft-summary clearfix']/ul[@class='soft-infor']/li[7]/text()").toString();
        	if(appUpdateDate!=null)
        		appUpdateDate = appUpdateDate.replace("月", "-").replace("年", "-").replace("日", "");
        String appType = null;

        String appDescription = html.xpath("//div[@class='summary-text']/p/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='screenshot-out']/table/tbody/tr/td/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='nav-path']/a[4]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//div[@class='soft-summary clearfix']/ul[@class='soft-infor']/li[6]/text()").toString();;
        if(dowloadNum != null) dowloadNum = dowloadNum.replace(",", "").replace("次", "").replace("\n", "");
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
            System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			//System.out.println("appVenderName="+appVenderName);
			//System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			//System.out.println("appScrenshot="+appScrenshot);
			System.out.println("appCategory="+appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
