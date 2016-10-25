package com.appCrawler.pagePro.apkDetails;
/*
 * 详情页和下载页不在同一个页面里
 * 需要再单独下载一个页面获取其他信息
 * http://www.5253.com/game/45256.html
 * http://www.5253.com/game/45256/download.html*/
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

public class PagePro5253_Detail {

	 private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro520apk_Detail.class);

	    public static Apk getApkDetail(Page page){
	    	 // 获取dom对象
	    	String pageNumber = StringUtils.substringBetween(page.getUrl().toString(), "/game/", ".html");
            System.out.println(pageNumber);
	    	Html html = Html.create(SinglePageDownloader.getHtml("http://www.5253.com/game/"+pageNumber+"/download.html","GET",null));

            
            // 找出对应需要信息
            String appDetailUrl = page.getUrl().toString();
            String appName = html.xpath("//div[@class='game-txtinfo']/h3/text()").toString();
            String temp = html.xpath("//div[@class='info']/div[@class='clear'][1]/p[2]/text()").get();
            String tempq = html.xpath("//div[@class='info']/div[@class='clear'][1]/p[1]/text()").get();
            String appVersion = StringUtils.substringBetween(temp, "：", "更");
//            String downloadUrl=html.xpath("//div[@class='dwl-btn']/a[@class='az']/@href").toString();
//            Html html1 = Html.create(SinglePageDownloader.getHtml(downloadUrl,"GET",null));
            
            String appDownloadUrl = html.xpath("//div[@class='info']/div[@class='clear'][2]/a[@class='btn tag2']/@data-url").toString();
           // appDownloadUrl=appDownloadUrl==null?html.xpath("//div[@class='info']/div[@class='clear'][2]/a[@class='btn tag2']/@data-url").get();
            String osPlatform =  StringUtils.substringAfter(html.xpath("//div[@class='dtab-cont dtab-cont-az']/p/text()").get(), "系统要求：");
            String appSize = StringUtils.substringBetween(tempq, "：", "下");
            String appUpdateDate = StringUtils.substringAfterLast(temp, "：");           
            String appType = null;

            String appDescription = page.getHtml().xpath("//div[@class='game-introduce']/div/p[1]/text()").get();
            List<String> appScreenshot = page.getHtml().xpath("//div[@class='pic-box']//img/@src").all();
            String appTag = StringUtils.join(html.xpath("//div[@class='app_right']/p[3]/a/text()").all(), ",");
            String appCategory = html.xpath("//div[@class='g-info']/span[3]/text()").get();
            String appCommentUrl = null;
            String appComment = null;
            String dowloadNum = html.xpath("//div[@class='info']/div[@class='clear'][1]/p[1]/span/text()").get();
           // System.out.println("des"+appDescription+"shot"+appScreenshot);
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
//    			System.out.println("appVenderName="+appVenderName);
//    			System.out.println("appDownloadedTime="+appDownloadedTime);
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

