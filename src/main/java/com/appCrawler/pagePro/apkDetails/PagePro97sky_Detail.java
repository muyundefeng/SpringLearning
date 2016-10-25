package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * 97sky[中国] app搜索抓取
 * url:http://www.97sky.cn/search?type=0&keyword=qq
 *
 * @version 1.0.0
 */
public class PagePro97sky_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro97sky_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='ppdpTitleIn']/h1/text()").toString();
        String appVersion = null;
        String nameString = appName;
        String appDownloadUrl=null;
        if(nameString != null && nameString.contains("V"))
		{
			appName=nameString.substring(0,nameString.indexOf("V")-1);
			appVersion = nameString.substring(nameString.indexOf("V")+1,nameString.length());
		}
		else if(nameString != null && nameString.contains("v"))
		{
			appName=nameString.substring(0,nameString.indexOf("v")-1);
			appVersion = nameString.substring(nameString.indexOf("v")+1,nameString.length());
		}
		else if(nameString != null && nameString.contains("."))
		{
			appName=nameString.substring(0,nameString.indexOf(".")-1);
			appVersion = nameString.substring(nameString.indexOf(".")-1,nameString.length());
		}
		else 
		{
			appName = nameString;
			appVersion = null;
		}
        if(appVersion!=null&&appVersion.contains("安卓版"))
        {
        	appVersion=appVersion.replace("安卓版", "");
        }
        String htmlString=null;
        if(appDetailUrl.contains("soft"))
        {
        	htmlString=SinglePageDownloader.getHtml(appDetailUrl.replace("soft", "softdown"));
        	Html html1=Html.create(htmlString);
        	List<String> appDownloadUrl1 = html1.xpath("//div[@class='ppSxIn']/a/@href").all();
              
              if(appDownloadUrl1!=null)
              {
              	for(String str:appDownloadUrl1)
              	{
              		if(str!=null&&str.contains("apk")||str.contains("zip"))
              		{
              			appDownloadUrl=str;
              		}
              	}
              	//appDownloadUrl=null;
              }
        }
        
      
        String osPlatform = html.xpath("//ul[@class='ppdpList']/li[2]/span/text()").toString();
        String appSize = html.xpath("//ul[@class='ppdpList']/li[1]/span/text()").toString();
        String appUpdateDate =html.xpath("//ul[@class='ppdpList']/li[4]/span/text()").toString();
        String appType = null;
        List<String> appScreenshot=new ArrayList<String>();
        String appDescription = html.xpath("//div[@class='ddDetaCont']/text()").get();
        List<String> appScreenshot0 = html.xpath("//div[@id='focus']/ul/li/a/img/@src").all();
        for(String temp:appScreenshot0){
			if(!temp.startsWith("http://www.97sky.cn")){
				temp="http://www.97sky.cn"+temp;
			}
        	appScreenshot.add(temp);
        }
       
        String appTag = null;
        String appCategory = html.xpath("//div[@class='ppSite']/a[3]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl && null != osPlatform && osPlatform.contains("Android")) {
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
//    		System.out.println("appVenderName="+appVenderName);
//    		System.out.println("appDownloadedTime="+appDownloadedTime);
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
}
