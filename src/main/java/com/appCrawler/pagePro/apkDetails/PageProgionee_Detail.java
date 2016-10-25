package com.appCrawler.pagePro.apkDetails;

import com.alibaba.fastjson.JSON;
//import com.appCrawler.utils.HttpClientLib;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 金立游戏 app搜索抓取
 * url:http://game.gionee.com/Front/Game/detail/?id=245&cku=2118633346_null&action=visit&object=gamedetail245&intersrc=category100_new_gid245
 *
 * @version 1.0.0
 */
public class PageProgionee_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProgionee_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='game_intro']/h4/text()").toString();
        String appVersion = StringUtils.substringAfter(html.xpath("//p[@class='sum']/span[3]/text()").toString(),"版本：");
        String appDownloadUrl = html.xpath("//p[@class='btn_area']/a[2]/@href").toString();
        String osPlatform = html.xpath("//ul[@class='intro_list']/li[3]/text()").get();
        	osPlatform = osPlatform.replace("适应系统：", "");
        String appSize = StringUtils.substringAfter(html.xpath("//p[@class='sum']/span[2]/text()").toString(),"大小：");
        String appUpdateDate = html.xpath("//ul[@class='intro_list']/li[2]/text()").get();
        appUpdateDate = appUpdateDate.replace("更新日期：", "");
        String appType = null;

        String appDescription = html.xpath("//div[@class='intro_con']/text()").get();
        List<String> appScreenshot = html.xpath("//ul[@class='slide_pic clearfix']/li/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//p[@class='sum']/span[1]/text()").toString();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = null;

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
