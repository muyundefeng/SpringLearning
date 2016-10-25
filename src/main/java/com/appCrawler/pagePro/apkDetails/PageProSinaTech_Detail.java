package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新浪手机软件[中国] app搜索抓取
 * url:http://down.tech.sina.com.cn/3gsoft/iframelist.php?classid=0&keyword=QQ&tag=&osid=&order=&page=2
 *
 * @version 1.0.0
 */
public class PageProSinaTech_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProSinaTech_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='b_cmon']/h1/text()").toString();
        String appVersion = StringUtils.substringAfter(appName, " ");
        String appDownloadUrl = html.xpath("//div[@class='share']/a/@href").toString();
        String osPlatform = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[6]/text()").get(), "：");
        String appSize = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[2]/p[2]/text()").get(), "：");
        String appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[3]/p[1]/text()").get(), "：");
        String appType = null;

        String appDescription = html.xpath("//div[@class='zcblk']/p[2]/text()").get();
        List<String> appScreenshot = null;
        String appTag = null;
        String appCategory = html.xpath("//div[@class='blkBreadcrumb']/a[2]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = StringUtils.substringAfterLast(html.xpath("//ul[@class='zcwords  clearfix']/li[7]/text()").get(), "：");;

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
    
    public static Apk getApkDetailForFullStack(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='tit_01']/h2/text()").toString();
        String appVenderName = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[1]/p[2]/text()").toString(),"：");
        
        String appVersion = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[2]/p[1]/text()").toString(),"：");
        		appVersion = appVersion.replace("V", "").replace("v", "");
        String appSize = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[2]/p[2]/text()").toString(),"：");
        String appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[3]/p[1]/text()").toString(),"：");
        String appTag = html.xpath("//ul[@class='zcwords  clearfix']/li[4]/a/text()").all().toString();
        String dowloadNum = StringUtils.substringAfterLast(html.xpath("//ul[@class='zcwords  clearfix']/li[7]/text()").get(), "：");;
        String osPlatform = StringUtils.substringAfter(html.xpath("//ul[@class='zcwords  clearfix']/li[6]/text()").get(), "：");
        if(osPlatform!=null&&!osPlatform.contains("Android"))
        {
        	appName=null;
        }
        //提取ip地
        //page.setUrl(url);
        
        String ipUrl = "http://sinastorage.com/?extra&op=selfip.js&cb=downWithIp";
        Map<String, String> map = new HashMap<String,String>();
        map.put("Referer", appDetailUrl);
        map.put("Upgrade-Insecure-Requests", "1");
        String string = SinglePageDownloader.getHtml(ipUrl,"GET",map);
        System.out.println(string);
        String ip = string.replace("downWithIp('", "").replace("')", "");
        
        String appDownloadUrl = html.xpath("//div[@class='share']/a/@href").toString();
        System.out.println(ip);
        appDownloadUrl = appDownloadUrl.replace(";", "") + "&ip="+ip;
        appDownloadUrl =  appDownloadUrl.replace(";", "");
       
        String appType = null;
        String appDescription = usefulInfo(html.xpath("//div[@class='zcblk']").get());
        List<String> appScreenshot = null;
        
        String appCategory = html.xpath("//div[@class='blkBreadcrumb']/a[2]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        
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
    		System.out.println("appVenderName="+appVenderName);
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
