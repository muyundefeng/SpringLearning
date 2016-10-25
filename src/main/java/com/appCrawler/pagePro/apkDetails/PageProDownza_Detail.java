package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 下载之家[中国] app搜索抓取
 * url:http://www.downza.cn/search?k=MT
 *
 * @version 1.0.0
 */
public class PageProDownza_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProDownza_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象	
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='fixed']/h1/text()").toString();
        String appDownloadUrl = html.xpath("//ul[@class='ul_Address']/li[1]/a/@href").toString();
        
        String appCategory = StringUtils.substringAfter(html.xpath("//div[@class='fixed']/p[3]/i[1]/text()").toString(),"：");       
        String appVersion = StringUtils.substringAfter(html.xpath("//div[@class='fixed']/p[3]/i[2]/text()").toString(),"：");  
        	appVersion = appVersion.replace("v", "").replace("V", "").replace(" ", "");
        String appUpdateDate = StringUtils.substringAfter(html.xpath("//div[@class='fixed']/p[3]/i[3]/text()").toString(),"：");  
        	appUpdateDate = appUpdateDate.replace("/", "-");
        String appSize = StringUtils.substringAfter(html.xpath("//div[@class='fixed']/p[3]/i[4]/text()").toString(),"：");    
        String osPlatform = StringUtils.substringAfter(html.xpath("//div[@class='fixed']/p[3]/i[7]/text()").toString(),"：");    
        
        String appDescription = usefulInfo(html.xpath("//div[@id='content']").toString());
        
        String appType = null;        
        List<String> appScreenshot = html.xpath("//div[@id='content']//img/@src").all();
        String appTag = null;        
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl && !appVersion.toLowerCase().contains("iphone")
        		&& !osPlatform.toLowerCase().contains("win") && !osPlatform.toLowerCase().contains("iphone")) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCommentUrl(appCommentUrl);
            apk.setAppComment(appComment);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
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
