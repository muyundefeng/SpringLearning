package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * PC6安卓网[中国] app搜索抓取
 * url:http://www.pc6.com/android/465_1.html,http://www.pc6.com/andyx/466_1.html
 *
 * @version 1.0.0
 */
public class PageProPc6_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProPc6_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();

        String appVersion = StringUtils.substringAfter(page.getHtml().xpath("//div[@class='fixed']/p[3]/i[2]/text()").get(), "：");
        if(appVersion!=null)
        {
        	appVersion = appVersion.replace("v", "").replace("V", "");
        }
        String appName = page.getHtml().xpath("//div[@class='fixed']/h1/text()").toString();
        String appDownloadUrl = page.getHtml().xpath("//ul[@class='ul_Address']/script").toString();
        if (null != appDownloadUrl) {
            //<script type="text/javascript"> _downInfo={Address:"/cx/QQQingLv.pc6.apk",TypeID:"45",SoftLinkID:"253387",SoftID:"91225",Special:"0"}</script>
            appDownloadUrl = appDownloadUrl.split("\\_downInfo=\\{Address\\:").length > 1 ? appDownloadUrl.split("\\_downInfo=\\{Address\\:")[1] : null;
            if(appDownloadUrl != null){
                appDownloadUrl = appDownloadUrl.split(",TypeID:")[0];
                appDownloadUrl = appDownloadUrl.replace("\"", "");
            }
            appDownloadUrl = "http://a3wt.pc6.com" + appDownloadUrl;
        }
        //System.out.println(page.getHtml().toString());
        String osPlatform = StringUtils.substringAfter(page.getHtml().xpath("//div[@class='fixed']/p[3]/i[7]/text()").get(), "：");
        String appSize = StringUtils.substringAfter(page.getHtml().xpath("//div[@class='fixed']/p[3]/i[4]/text()").get(), "：");
        String appUpdateDate = StringUtils.substringAfter(page.getHtml().xpath("//div[@class='fixed']/p[3]/i[3]/text()").get(), "：");
        appUpdateDate = appUpdateDate.replace("/", "-");
        String appType = null;

        String appDescription = usefulInfo(html.xpath("//div[@id='content']").toString());
        List<String> appScreenshot = html.xpath("//div[@class='dimg']//img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//p[@class='seat']/a[3]/text()").get();
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
