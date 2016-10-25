package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 酷安网 app搜索抓取
 * url:http://www.coolapk.com/apk/com.google.android.googlequicksearchbox
 * id:185
 * @version 1.0.0
 */
public class PageProCoolapk_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCoolapk_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='media-body']/h1/text()").toString();
        String appVersion = html.xpath("//div[@class='media-body']/h1/small/text()").toString();
        String appDownloadUrl = StringUtils.substringBetween(page.getHtml().toString(), "var apkDownloadUrl = \"", "\";");
       // appDownloadUrl="http://www.coolapk.com"+appDownloadUrl;
      //  System.out.println("appDownloadUrl="+appDownloadUrl);
        appDownloadUrl = GetTrueDownloadUrlForCoolapk.getDownloadUrlForCoolapk(page.getUrl().toString());
        System.out.println("appDownloadUrlTrue"+appDownloadUrl);
        String info0=html.xpath("//div[@class='media-intro ex-apk-view-intro']/span[2]/text()").toString();
        String osPlatform = StringUtils.substringBetween(info0, "，","，");
        String appSize = StringUtils.substringBefore(info0, "，");
        String appUpdateDate = StringUtils.substringAfterLast(info0, "，");
        	appUpdateDate = appUpdateDate.replace("更新", "");
        String appType =null;

        String appDescription = html.xpath("//div[@class='ex-card-content]/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='ex-screenshot-thumb-carousel']/img/@src").all();
        String appTag = null;
        String appCategory = null;
        String appCommentUrl = null;
       // String appComment = html.xpath("//div[@id='comment']").get();
        String dowloadNum =StringUtils.substringBetween(html.xpath("//div[@class='media-intro ex-apk-view-intro']/span[1]/text()").toString(),"，","次下载");

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
         //   apk.setAppCommentUrl(appCommentUrl);
        //    apk.setAppComment(appComment);
            apk.setAppDownloadTimes(dowloadNum);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{},appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot,appDescription);

        return apk;
    }
	
//	private static String getTrueDownloadUrl(String urlString){
//			String location = null;
//			try {  
//	            String url = "http://www.coolapk.com/dl?pn=de.nullgrad.glimpse&v=MTQ5MTk&h=d6680788nt98d7"; 
//	            url = urlString;
//	            System.out.println("访问地址:" + url);  
//	            URL serverUrl = new URL(url);  
//	            HttpURLConnection conn = (HttpURLConnection) serverUrl  
//	                    .openConnection();  
//	            conn.setRequestMethod("GET");  
//	            // 必须设置false，否则会自动redirect到Location的地址  
//	            conn.setInstanceFollowRedirects(false);  	  
//	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");  
//	            conn.addRequestProperty("Accept-Encoding", "gzip, deflate, sdch");  
//	            conn.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.8"); 
//	            conn.addRequestProperty("Cookie", "SESSID=c72025ea_55c718d54de3c1_63420718_1439111381_3176; Hm_lvt_7132d8577cc4aa4ae4ee939cd42eb02b=1439111382; Hm_lpvt_7132d8577cc4aa4ae4ee939cd42eb02b=1439865300");
//	            conn.addRequestProperty("Host", "www.coolapk.com");  
//	            conn.addRequestProperty("Proxy-Connection", "keep-alive");  
//	            conn.addRequestProperty("User-Agent",  
//	                    "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36");  
//	            conn.connect();  
//	            location = conn.getHeaderField("Location");  
//	  
//	        } catch (Exception e) {  
//	            e.printStackTrace();  
//	        }  
//			return location;
//	}
}
