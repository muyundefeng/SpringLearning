package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 安卓中文网(tgbus)[中国] app搜索抓取
 * url:http://a.tgbus.com/game/, http://a.tgbus.com/soft/
 *
 * @version 1.0.0
 */
public class PageProTgbus_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProTgbus_Detail.class);

    public static Apk getApkDetail(Page page){
    	//System.out.println("in this");
        // 获取dom对象
        Html html = page.getHtml();

//        String appId = html.xpath("//div[@class='sontxt fl']/strong/a/@href").toString();
//
//        // 找出对应需要信息
//        String appDetailUrl = page.getUrl().toString();
//        String appName = html.xpath("//div[@class='son_lf fl']/h2/strong/text()").toString();
//        String versionTemp = html.xpath("//div[@class='son_lf fl']/h2/em/text()").toString();
//        String appVersion = null != versionTemp ? versionTemp.replace("【", "").replace("】", "") : null;
//        String appDownloadUrl = String.format("http://a.tgbus.com/download/%s/1", StringUtils.substringBetween(appId, "item-", "/"));
//        String osPlatform = null;
//        String appSize = html.xpath("//div[@class='sontxt fl']/ol/li[5]/text()").toString();
//        String appUpdateDate = html.xpath("//div[@class='sontxt fl']/ol/li[7]/text()").toString();
//        String appType = null;
//
//        String appDescription = html.xpath("//div[@class='son_fx'][3]/text()").get();
//        List<String> appScreenshot = html.xpath("//tr[@class='portal-item-screenshots']/td/a/img/@src").all();
//        String appTag = null;
//        String appCategory = html.xpath("//p[@class='dqwz']/b/a[3]/text()").get();
//        String appCommentUrl = null;
//        String appComment = null;
//        String dowloadNum = null;
        String nameString = html.xpath("//div[@class='arc_660_bt']/p/text()").toString();
        String appName =null;
        String appVersion = null;
        if(nameString != null && nameString.contains("V"))
		{
			appName=nameString.substring(0,nameString.indexOf("V")-1);
			appVersion = nameString.substring(nameString.indexOf("V")+1,nameString.length());
		}
		else if(nameString != null && nameString.contains("v"))
		{
			appName=nameString.substring(0,nameString.indexOf("v")-1);
			appVersion = nameString.substring(nameString.indexOf("v")+1,nameString.length());
		}
		else if(nameString != null && nameString.contains("."))
		{
			appName=nameString.substring(0,nameString.indexOf(".")-1);
			appVersion = nameString.substring(nameString.indexOf(".")-1,nameString.length());
		}
		else 
		{
			appName = nameString;
			appVersion = null;
		}
        String appDetailUrl = page.getUrl().toString();
        String appDownloadUrl = html.xpath("//p[@align='center']/a/@href").toString();
        String appVenderName = StringUtils.substringAfter(html.xpath("//div[@class='arc_660_bt']/span/em[1]/text()").toString(), "作者：");
        String appUpdateDate =  StringUtils.substringAfter(html.xpath("//div[@class='arc_660_bt']/span/em[3]/text()").toString(), "发布时间：").replace("/", "-");
        List<String> appScreenshot = html.xpath("//div[@class='ct f14 fx']/div[1]//img/@src").all();
        String appDescription = html.xpath("//div[@class='ct f14 fx']/p[1]/text()").toString();
        String osPlatform = null;
        String appSize = null;
        String appType = null;
        String appComment = null;
        String appCommentUrl = null;
        String dowloadNum = null;
        String appCategory = null;
        String appTag =null;
	

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
