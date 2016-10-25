package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 265G安卓网[中国] app搜索抓取
 * url:http://zhannei.baidu.com/cse/search?s=1307449979590049420&entry=1&ie=gbk&q=MT
 *
 * @version 1.0.0
 */
public class PagePro265g_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro265g_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='rightbox']/h1/text()").toString();
        String appVersion = StringUtils.substringAfter(html.xpath("//div[@class='leftbox']/p[1]/text()").get(), "：");
        String appDownloadUrl = html.xpath("//div[@class='leftbox']/a[@class='down_btn']/@href").toString();
        String osPlatform = StringUtils.substringAfter(html.xpath("//div[@class='leftbox']/p[7]/text()").get(), "：");
        String appSize = StringUtils.substringAfter(html.xpath("//div[@class='leftbox']/p[2]/text()").get(), "：");
        String appUpdateDate = StringUtils.substringAfter(html.xpath("//div[@class='leftbox']/p[6]/text()").get(), "：");
        String appType = null;

        String appDescription = usefulInfo(html.xpath("//div[@id='txtover']").toString());
        List<String> appScreenshot = html.xpath("//ul[@class='view_gameimgli']/li/a/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='cont_title cont_title3']/a[3]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
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
    		System.out.println("appScrenshot="+appScreenshot);
    		System.out.println("appCategory="+appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
    
    private static String usefulInfo(String allinfoString)
	{
	if(allinfoString == null) return null;
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
}
