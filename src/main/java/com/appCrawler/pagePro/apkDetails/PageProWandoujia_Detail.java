package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 /**
 * * 豌豆荚官方[中国] app搜索抓取
 * url:http://www.wandoujia.com/search?key=MT&source=apps
 * 评论网址：
 * http://apps.wandoujia.com/api/v1/comments/primary?packageName=com.sesame.dwgame.xiyou.ky
 * 只需修改后面的包名即可。
 * @version 1.0.0
 */
public class PageProWandoujia_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProMM10086_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='app-info']/p[@class='app-name']/span/text()").toString();
        String appVersion = html.xpath("//dl[@class='infos-list']/dd[4]/text()").get();
        String appDownloadUrl = html.xpath("//div[@class='download-wp']/a[1]/@href").toString();
        String osPlatform = html.xpath("//dl[@class='infos-list']/dd[5]/text()").get();
        String appSize = html.xpath("//dl[@class='infos-list']/dd[1]/text()").get();
        		appSize = appSize.replace(" ", "");
        String appUpdateDate = getFormatedUpdateDateString(html.xpath("//dl[@class='infos-list']/dd[3]/time/@datetime").get());
        String appType = null;

        String appDescription = usefulInfo(html.xpath("//div[@itemprop='description']").get());
        List<String> appScreenshot = html.xpath("//div[@class='overview']/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='crumb']/div[@class='second']/a/span/text()").get();
        String appCommentUrl = "http://apps.wandoujia.com/api/v1/comments/primary?packageName=" + StringUtils.substringBetween(appDownloadUrl, "apps/", "/download");;
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
//			System.out.println("appVenderName="+appVenderName);
//			System.out.println("appDownloadedTime="+appDownloadedTime);
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
    
    private static String getFormatedUpdateDateString(String appUpdateDate){
		//Sep 14, 2014 	Aug 25, 2015	May 21, 2015	Feb 12, 2015	Jul 28, 2015	Jun 30, 2015	Apr 7, 2015
		//Mar 20, 2015	Jun 7, 2014		Oct 27, 2015	Dec 21, 2014	Nov 5, 2015
		String day = StringUtils.substringBetween(appUpdateDate, " ", ",");
		String year = StringUtils.substringAfterLast(appUpdateDate, ", ");
		String month = "";
		String temp = StringUtils.substringBefore(appUpdateDate, " ");
		switch (temp) {
		case "Jan":month="01";			
			break;
		case "Feb":month="02";			
		break;
		case "Mar":month="03";			
		break;
		case "Apr":month="04";			
		break;
		case "May":month="05";			
		break;
		case "Jun":month="06";			
		break;
		case "Jul":month="07";			
		break;
		case "Aug":month="08";			
		break;
		case "Sep":month="09";			
		break;
		case "Oct":month="10";			
		break;
		case "Nov":month="11";			
		break;
		case "Dec":month="12";			
		break;
		default:
			break;
		}
		return year+"-"+month+"-"+day;
	}
	
	public static void main(String[] args){
	System.out.println(getFormatedUpdateDateString("Aug 25, 2015"));
}
}
