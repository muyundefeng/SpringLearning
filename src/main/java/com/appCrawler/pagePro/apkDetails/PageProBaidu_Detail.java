package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 百度[中国] app搜索抓取
 * url:http://shouji.baidu.com/s?wd=QQ&data_type=app&f=header_all%40input
 *
 * @version 1.0.0
 */
public class PageProBaidu_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProBaidu_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
        Html html = page.getHtml();

        // 找出对应需要信息
        String appDetailUrl = page.getUrl().toString();
        String appName = html.xpath("//div[@class='content-right']/h1/span/text()").toString();
        String appVersion = StringUtils.substringAfterLast(html.xpath("//div[@class='detail']/span[@class='version']/text()").toString(), "版本: ");
        String appDownloadUrl = html.xpath("//div[@class='area-download']/a/@href").toString();
        String osPlatform = null;
        String appSize = StringUtils.substringAfterLast(html.xpath("//div[@class='detail']/span[@class='size']/text()").toString(), "大小: ");
        String appUpdateDate = null;
        String appType = null;
        String appVenderName = html.xpath("//div[@class='origin-wrap']/a/text()").toString();
        
        String appDescription = html.xpath("//p[@class='content content_hover']/text()").get();
        List<String> appScreenshot = html.xpath("//div[@class='screenshot-container']/ul/li/img/@src").all();
        String appTag = null;
        String appCategory = html.xpath("//div[@class='nav']/span[3]/a/text()").get();
        String appCommentUrl = null;
        String appComment = html.xpath("//div[@id='h_d']").get();
        String dowloadNum = StringUtils.substringAfterLast(html.xpath("//div[@class='detail']/span[@class='download-num']/text()").toString(), ": ");
		dowloadNum = dowloadNum.replace("万", "0000");
		if(dowloadNum!=null){
        	if(dowloadNum.contains("亿")){
        		dowloadNum = dowloadNum.replace("亿", "");
        		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
        		nf.setGroupingUsed(false);  
        		int dowloadNum1 = (int)(Float.parseFloat(dowloadNum)*10000);
        		//System.out.println(dowloadNum1);
        		dowloadNum = nf.format(dowloadNum1)+"0000";
        	}
        	if(dowloadNum.contains("万")){
        		dowloadNum = dowloadNum.replace("万", "");
        		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
        		nf.setGroupingUsed(false);  
        		float dowloadNum1 = Float.parseFloat(dowloadNum)*10000 ;
        		dowloadNum = nf.format(dowloadNum1)+"";
        	}
        }
        /*在服务器上运行这部分代码时一直报错，暂时注释掉
        List<String> comList = Lists.newArrayList();
        String urlTemplate = "http://shouji.baidu.com/comment?action_type=getCommentList&groupid=%s&pn=%s";
        String url = String.format(urlTemplate, groupid, "1");

        comList.add(url);

        try {
            // 检查是否还有下一页
            String comtent = EntityUtils.toString(new HttpClientLib().getUrlReponse(url).getEntity());
            String pages = StringUtils.substringBetween(comtent, "<input type=\"hidden\" name=\"totalpage\" value=\"", "\"");
            if (StringUtils.isNotEmpty(pages)) {
                for (int i = 2; i < Integer.valueOf(pages); i++) {
                    comList.add(String.format(urlTemplate, groupid, i));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }finally{

        }

        appCommentUrl = StringUtils.substringBetween(comList.toString(), "[", "]");*/

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
//            System.out.println("appName="+appName);
//			System.out.println("appDetailUrl="+appDetailUrl);
//			System.out.println("appDownloadUrl="+appDownloadUrl);
//			System.out.println("osPlatform="+osPlatform);
//			System.out.println("appVersion="+appVersion);
//			System.out.println("appSize="+appSize);
//			System.out.println("appUpdateDate="+appUpdateDate);
//			System.out.println("appType="+appType);
//			System.out.println("appVenderName="+appVenderName);
//			System.out.println("appDescription="+appDescription);
//			System.out.println("appTag="+appTag);
//			System.out.println("appScrenshot="+appScreenshot);
//			System.out.println("appCategory="+appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);

        return apk;
    }
}
