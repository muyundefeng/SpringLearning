package com.appCrawler.pagePro.apkDetails;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.appCrawler.utils.HttpClientLib;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * hiapk[中国] app搜索抓取
 * url:http://apk.hiapk.com/search?key=mt&pid=0
 *
 * @version 1.0.0
 */
public class PageProHiApk_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProHiApk_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@id='appSoftName']/text()").toString();
        String appVersion = StringUtils.substringBetween(appName, "(", ")");
        String appDownloadUrl = html.xpath("//a[@id='appInfoDownUrl']/@href").toString();
        String osPlatform = html.xpath("//span[@class='font14 detailMiniSdk d_gj_line left']/text()").get();
        String appSize = html.xpath("//span[@id='appSize']/text()").get();
        String appUpdateDate = html.xpath("//div[@class='code_box_border']/div[9]/span[2]/text()").get();
        String appType = null;
        String appDescription = html.xpath("//pre[@id='softIntroduce']/text()").get();
        List<String> appScreenshot = html.xpath("//ul[@id='screenImgUl']/li/a/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='detail_tip']/a[2]/text()").get();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;
        String appVenderName = html.xpath("//div[@class='code_box_border']/div[2]/span[2]/text()").toString();

 //       String apiId = StringUtils.substringBetween(html.get(), "id=\"hidAppId\" value=\"", "\"");
  //      String url = "http://apk.hiapk.com/web/api.do?qt=1701&id=%s&pi=%s&ps=10";
 //       String url1 = String.format(url, apiId, 1);

//        List<String> list = Lists.newArrayList(url1);
//        int pageNum = JSON.parseObject(new HttpClientLib().getUrlRespHtml(url1)).getInteger("total")/10 + 1;
//        if (pageNum > 1) {
//            for (int i = 2; i < pageNum; i++) {
//                list.add(String.format(url, apiId, i));
//            }
//        }
        //appCommentUrl = StringUtils.join(list, ",");

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
}
