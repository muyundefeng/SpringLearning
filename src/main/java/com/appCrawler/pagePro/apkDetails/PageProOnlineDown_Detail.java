package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * 华军软件园[中国] app搜索抓取
 * url:http://search.newhua.com/search_list.php?searchname=MT&searchsid=6&app=search&controller=index&action=search&type=news
 *
 * @version 1.0.0
 */
public class PageProOnlineDown_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProOnlineDown_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='appinfo']/div[@class='app_name']/h2/span[1]/text()").toString();
        if(appName == null) return null;
        String appVersion = html.xpath("//div[@class='appinfo']/div[@class='app_other']/ul/li[2]/text()").toString();
        	    appVersion = appVersion.replace(" ", "").replace("V", "").replace("v", "");
        String appDownloadUrl = html.xpath("//div[@class='appinfo']/div[@class='download']/a[1]/@href").toString();
        	appDownloadUrl=getDownloadUrl(appDownloadUrl);
        String osPlatform = null;
        String appSize = html.xpath("//div[@class='appinfo']/div[@class='app_other']/ul/li[4]/div/text()").toString();
        String appUpdateDate = html.xpath("//div[@class='appinfo']/div[@class='app_other']/ul/li[8]/div/text()").toString();
        String appType = null;

        String appDescription = usefulInfo(html.xpath("//div[@itemprop='description']").toString());
        List<String> appScreenshot = html.xpath("//div[@id='slide08']/ul/li/img/@src").all();
        String appTag = null;
        String appCategory = StringUtils.substringAfterLast(html.xpath("//div[@class='mj_info font-f-yh']/ul/li[6]/text()").toString(), "：");;
        if(appCategory == null) appCategory = html.xpath("//div[@class='breadcrumb']/a[4]/text()").toString();
        String appCommentUrl = "http://newhua.duoshuo.com/api/threads/listPosts.json?container_url=" + page.getUrl().get();
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
        

        // 更新下载地址
//        if(page.getUrl().regex("http://www\\.onlinedown\\.net/softdown/*").match()) {
//            for (Apk apk_new : resSet) {
//                if (apk_new.getAppDownloadUrl().equals(page.getUrl().get())) {
//                    // 获取真实的url
//                    String url = page.getHtml().xpath("//div[@class='down-menu']/a[1]/@href").get();
//                    apk_new.setAppDownloadUrl(url);
//
//                    LOGGER.debug(apk_new.getAppName() + " real download url : {}", url);
//
//                    return apk_new;
//                }
//            }
//        }
        
   
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
    
    private static String getDownloadUrl(String downloadUrlString){   	  
    	String sourcefile="";
    	String lines = "";
    	try {		
			URL url=new URL(downloadUrlString);		
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			 while ((lines = reader.readLine()) != null){
				 	sourcefile=sourcefile+lines;
				 	
				}
		//	 System.out.println(sourcefile);				
		} catch (Exception e) {
		}
    	Html html = new Html(sourcefile);
    	String url = html.xpath("//div[@class='down-menu']/a[1]/@href").get();
    	return url;
    }
}
