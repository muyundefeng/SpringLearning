package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 大家玩 app搜索抓取
 * url:http://android.dajiawan.com/
 *id:182
 * @version 1.0.0
 */
public class PageProDjw_Detail{

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProDjw_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//dt[@class='g_name']/span/text()").toString();       
        String appVersion = null;
        String appDownloadUrl = html.xpath("//dd[@class='xiazai']/span/a/@href").toString();
        String osPlatform = null;
        String appSize = html.xpath("//div[@class='info_m']/ul/li[3]/span/text()").get();
        String appUpdateDate =  html.xpath("//div[@class='info_m']/ul/li[2]/span/text()").get();
        String appType =  html.xpath("//div[@class='info_m']/ul/li[6]/span/text()").get();

        String appDescription = html.xpath("//dl[@class='a2_info']/dd/p[2]/text()").get();
        List<String> appScreenshot = html.xpath("//dl[@class='a2_info']/dd/div/a/img/@src").all();
        String appTag = null;
        String appCategory =  html.xpath("//div[@class='info_m']/ul/li[1]/span/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//div[@class='info_m']/ul/li[7]/span/text()").get();
        
        String nameString = appName;
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
        
        
        
        

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
