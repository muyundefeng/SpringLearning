package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 手游天下[中国] app搜索抓取
 * url:http://android.155.cn/search.php?kw=MT&index=soft
 *
 * @version 1.0.0
 */
public class PagePro155_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro155_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();
        Apk apk = null;
        if (page.getUrl().regex("http://android\\.155\\.cn/soft|game/.*").match()){
        
        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='left_icon']/h1/text()").toString();
        if(appName == null) return null;
        String appVersion = html.xpath("//div[@class='left_icon']/h2/text()").toString();
        		appVersion = appVersion.replace("v", "").replace("V", "");
        String appDownloadUrl = html.xpath("//div[@class='bottom_down']/a/@href").toString();
        String osPlatform = null;
        String appSize = html.xpath("//div[@class='xinxi_center']/p[3]/span/text()").toString();
        String appUpdateDate = html.xpath("//div[@class='xinxi_center']/p[7]/span/text()").toString();
        String appType = null;

        String appDescription = html.xpath("//div[@class='jianjie_cent']/p/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='snapShotCont']/div/img/@src").all();
        String appTag = null;
        String appCategory = null;
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//div[@class='xinxi_center']/p[4]/span/text()").toString();;
        		dowloadNum = dowloadNum.replace(" ", "");
        String appVenderName = html.xpath("//div[@class='xinxi_center']/p[8]/span/text()").toString();
        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
        appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

      
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
			System.out.println("appScrenshot="+appScreenshot);
			System.out.println("appCategory="+appCategory);
        }
        }
        else if( page.getUrl().regex("http://wy\\.155\\.cn/.*").match()){
        	 String appDetailUrl = page.getUrl().toString();
             String appName = html.xpath("//div[@class='cent1_left']/h1/text()").toString();
             if(appName == null) return null;
             String appVersion = null;
             String appCategory = StringUtils.substringAfter(html.xpath("//ul[@class='c1_ul']/li[1]/text()").toString(), "：");
             String osPlatform = StringUtils.substringAfter(html.xpath("//ul[@class='c1_ul']/li[4]/text()").toString(), "：");           
             String dowloadNum = StringUtils.substringAfter(html.xpath("//ul[@class='c1_ul']/li[5]/text()").toString(), "："); 
             String appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@class='c1_ul']/li[6]/text()").toString(), "：");
             String appDescription = usefulInfo(html.xpath("//div[@class='jj_cent show']").toString());
             List<String> appScreenshot = html.xpath("//ul[@class='jietu_ul']//img/@src").all();
             String appVenderName = html.xpath("//div[@class='c1_kefu']/text()").toString();           
             String appDownloadUrl = html.xpath("//div[@class='c1_bot']/a/@href").toString();
             
             String appSize = null;
             String appType = null;
             String appTag = null; 
             String appCommentUrl = null;
             String appComment = null;
            
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
     			System.out.println("appScrenshot="+appScreenshot);
     			System.out.println("appCategory="+appCategory);
             }
             LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                     ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
             appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);       	
        }
        
              
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
