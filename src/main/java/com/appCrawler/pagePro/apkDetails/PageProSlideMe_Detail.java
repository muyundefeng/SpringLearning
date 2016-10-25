package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * SlideME[美国] app搜索抓取
 * url:http://slideme.org/applications/MT
 *
 * @version 1.0.0
 */
public class PageProSlideMe_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSlideMe_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='node node-mobileapp node-page']/h1/text()").toString();
        String appVersion = html.xpath("//div[@class='node node-mobileapp node-page']/h1/span[@class='version']/text()").toString();
        	   appVersion = appVersion.replace("v", "");
        String appDownloadUrl = html.xpath("//div[@class='download-button']/a/@href").toString();
        String osPlatform = null;
        String appSize = null;
        String appUpdateDate = getUpdateString(html.xpath("//div[@class='submitted']/text()").toString());
        String appType = null;

        String appDescription = html.xpath("//div[@class='content']/p[1]/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='field-items']/div/a/@href").all();
        String appTag = null;
        String appCategory = html.xpath("//li[@class='category']/a/text()").toString();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//li[@class='downloads']/text()").get();;
        String appVenderName = html.xpath("//div[@class='submitted']/a/text()").toString();
        
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
    
    private static String getUpdateString(String appUpdateDate){
    	String[] info = appUpdateDate.split(" ");
//    	for (String string : info) {
//			System.out.println(string);
//		}
    	String yearString = info[5];
    	String monthString = info[3];
    	switch (info[3]) {
		case "January": monthString="01"; break;
		case "Febrary": monthString="02"; break;
		case "March": monthString="03"; break;
		case "April": monthString="04"; break;
		case "May": monthString="05"; break;
		case "June": monthString="06"; break;
		case "July": monthString="07"; break;
		case "August": monthString="08"; break;
		case "September": monthString="09"; break;
		case "October": monthString="10"; break;
		case "November": monthString="11"; break;
		case "December": monthString="12"; break;
		default:
			break;
		}
    	String dayString = info[4].replace(",", "");
    	appUpdateDate = yearString+"-"+monthString+"-"+dayString;
    	return appUpdateDate;
    	
    }
}
