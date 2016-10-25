package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import com.app.saveDB.Persistent.svaeInfoToData;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.myApks;
import us.codecraft.webmagic.selector.Html;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

/**
 * 应用宝[中国] app搜索抓取
 * url:http://android.myapp.com/myapp/searchAjax.htm?kw=%E6%8D%95%E9%B1%BC%E8%BE%BE%E4%BA%BA&pns=&sid=
 *
 * @version 1.0.0
 */
public class PageProMyapp_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProMyapp_Detail.class);
    private static int count = 0;
    private static List<myApks> list = new ArrayList<myApks>();
	private static final int COUNT = 6000;
    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='det-name-int']/text()").toString();
        String appVersion = html.xpath("//div[@class='det-othinfo-data']/text()").get();
        	appVersion = appVersion.replace("V", "");
        String appDownloadUrl = html.xpath("//a[@class='det-down-btn']/@data-apkurl").toString();
        System.out.println(appName);
        System.out.println(appDownloadUrl);
        String isAd = html.xpath("//div[@class='com-container']/@flag").toString();
        System.out.println(isAd);
        int a = Integer.parseInt(isAd);
        int ad = a&3;
       // if(ad == 1 ){
        	if(count!=COUNT&&appName!=null&&appDownloadUrl!=null){
        		myApks apks = new myApks(appName, appDownloadUrl);
        		list.add(apks);
        		count++;
        		LOGGER.info("app number is"+count);
        	}
        	if(count==COUNT){
        		ObjectMapper objectMapper = new ObjectMapper();
        		try {
					String json = objectMapper.writeValueAsString(list);
					System.out.println(json);
					svaeInfoToData.writeToFile("/home/lisheng/data/info2.txt", json);
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.exit(0);
        	}
        	//CrawlerToRedisBridge.receiveData(appName, appDownloadUrl);
        //}
        String osPlatform = null;
        String appSize = html.xpath("//div[@class='det-size']/text()").toString();
        String appUpdateDate = html.xpath("//div[@id='J_ApkPublishTime']/@data-apkPublishTime").get();
        long minutes= 1443498964;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date = sdf.format(new Date(minutes*1000L));
    	//System.out.println(date); 
    	appUpdateDate = date.toString();
    	
        String appType = null;

        String appDescription = html.xpath("//div[@class='det-app-data-info']/text()").get();
        List<String> appScreenshot = html.xpath("//span[@id='J_PicTurnImgBox']/div/img/@data-src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='det-type-box']/a/text()").toString();
        String appCommentUrl = null;
        String appComment = null;
        String dowloadNum = html.xpath("//div[@class='det-ins-num']/text()").toString().replace("下载", "");
        String appVenderName = html.xpath("//div[@class='det-othinfo-container J_Mod']/div[6]/text()").toString();

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
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
    
//    public static void main(String[] args){
//    	long minutes= 1443498964;
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String date = sdf.format(new Date(minutes*1000L));
//    	System.out.println(date);
//    }
}
