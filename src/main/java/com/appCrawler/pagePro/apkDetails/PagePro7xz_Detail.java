package com.appCrawler.pagePro.apkDetails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * 七匣子[中国] app搜索抓取
 * url:http://www.7xz.com/ng/search_MT_0_0_rate_1.html
 *
 * @version 1.0.0
 */
public class PagePro7xz_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PagePro7xz_Detail.class);

    public static Apk getApkDetail(Page page){
        // 获取dom对象
    	if(page.getUrl().toString().contains("games/view"))
    	{
	        Html html = page.getHtml();
	
	        // 找出对应需要信息
	        String appDetailUrl = page.getUrl().toString();
	        String appName = html.xpath("//div[@class='fl c_g_l_c']/h1/text()").toString();
	        String appVersion1 = html.xpath("//div[@class='fl c_g_l_c']/p[1]/text()").toString();
	        String appUpdateDate = null;
	        String osPlatform=null;
	        if(appVersion1!=null)
	        {
	        	String temp[]=appVersion1.split("：");
	        	appUpdateDate=temp[temp.length-1];
	        	osPlatform=temp[1].split("及以上")[0];
	        }
	        String raw=page.getHtml().xpath("//div[@class='fl c_g_l_c']/div[1]/text()").toString();
	        String appSize=null;
	        String appVersion=null;
	        if(raw!=null)
	        {
	        	String temp[]=raw.split("：");
	        	appSize=temp[2].replace("评分", "");
	        	  if(appSize.contains(" "))
	              {
	              	appSize=appSize.replace(" ", "");
	              }
	        	appVersion=temp[1].replace("大小", "");
	        }
	        String appDownloadUrl = html.xpath("//p[@class='sprit down_android mt_30']/a/@href").toString();
	//        String osPlatform = html.xpath("//table[@class='table']/tbody/tr[2]/td[2]/text()").get();
	//        String appSize = html.xpath("//table[@class='table']/tbody/tr[3]/td[4]/text()").get();
	//        String appUpdateDate = null;
	        String appType = null;
	
	        String appDescription = html.xpath("//p[@class='lc']/text()").toString();
	        List<String> appScreenshot = html.xpath("//ul[@class='poster-list position_r']/li/a/img/@src").all();
	//        String appTag = StringUtils.join(html.xpath("//p[@class='tag1']/a/text()").all(), ",");
	//        String appCategory = html.xpath("//div[@class='row positon']/div/a[2]/text()").get();
	//        String appCommentUrl = null;
	//        String appComment = null;
	//        String dowloadNum = null;
	
	        Apk apk = null;
	        if (null != appName && null != appDownloadUrl) {
	            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
	            apk.setAppDescription(appDescription);
	            apk.setAppScreenshot(appScreenshot);
	           // apk.setAppCommentUrl(appCommentUrl);
	           // apk.setAppComment(appComment);
	            //apk.setAppDownloadTimes(dowloadNum);
	           // apk.setAppCategory(appCategory);
	           // apk.setAppTag(appTag);
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
				//System.out.println("appTag="+appTag);
				System.out.println("appScrenshot="+appScreenshot);
				//System.out.println("appCategory="+appCategory);
	        }
	
	//        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
	//                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
	//                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);
	
	        return apk;
    	}
    	else{
    		if(page.getUrl().toString().contains("softs/view/"))
    		{
    			String appDetailUrl = page.getUrl().toString();
    			String appName=page.getHtml().xpath("//div[@class='g-b1-right']/h1/text()").toString();
    			String raw=page.getHtml().xpath("//div[@class='g-b1-right']/div[1]/text()").toString();
    			String appVersion=null;
    			String appSize=null;
    			if(raw!=null)
    			{
    				String temp[]=raw.split("：");
    				appVersion=temp[1].replace("大小", "");
    				appSize=temp[2];
    			}
    			String raw1=page.getHtml().xpath("//div[@class='g-b1-right']/p/text()").toString();
    			String osPlatform=null;
    			String appUpdateDate=null;
    			if(raw1!=null)
    			{
    				String temp1[]=raw1.split("：");
    				osPlatform=temp1[1].replace("更新日期", "");
    				appUpdateDate=temp1[2];
    			}
    			String appDownloadUrl=page.getHtml().xpath("//p[@class='sprit down_android']/a/@href").toString();
    			String appDescription=page.getHtml().xpath("//div[@class='col-md-12 mt10']/text()").toString();
    			appDescription=usefulInfo(appDescription);
    			String appType=null;
    			 List<String> appScreenshot = page.getHtml().xpath("//ul[@class='poster-list']/li/a/img/@original").all();
    			 Apk apk = null;
    		        if (null != appName && null != appDownloadUrl) {
    		            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
    		            apk.setAppDescription(appDescription);
    		            apk.setAppScreenshot(appScreenshot);
    		           // apk.setAppCommentUrl(appCommentUrl);
    		           // apk.setAppComment(appComment);
    		            //apk.setAppDownloadTimes(dowloadNum);
    		           // apk.setAppCategory(appCategory);
    		           // apk.setAppTag(appTag);
    		            System.out.println("appName="+appName);
    					System.out.println("appDetailUrl="+appDetailUrl);
    					System.out.println("appDownloadUrl="+appDownloadUrl);
    					System.out.println("osPlatform="+osPlatform);
    					System.out.println("appVersion="+appVersion);
    					System.out.println("appSize="+appSize);
    					System.out.println("appUpdateDate="+appUpdateDate);
    					//System.out.println("appType="+appType);
    					//System.out.println("appVenderName="+appVenderName);
    					//System.out.println("appDownloadedTime="+appDownloadedTime);
    					System.out.println("appDescription="+appDescription);
    					//System.out.println("appTag="+appTag);
    					System.out.println("appScrenshot="+appScreenshot);
    					//System.out.println("appCategory="+appCategory);
    		        }
    		
    		//        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
    		//                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
    		//                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);
    		
    		        return apk;
    		}
    		else{

    			String appDetailUrl = page.getUrl().toString();
    			String appName=page.getHtml().xpath("//div[@class='c_rq_l']/div[2]/p[1]/a/text()").toString();
//    			String raw=page.getHtml().xpath("//div[@class='g-b1-right']/div[1]/text()").toString();
//    			String appVersion=null;
//    			String appSize=null;
//    			if(raw!=null)
//    			{
//    				String temp[]=raw.split("：");
//    				appVersion=temp[1].replace("大小", "");
//    				appSize=temp[2];
//    			}
//    			String raw1=page.getHtml().xpath("//div[@class='g-b1-right']/p/text()").toString();
//    			String osPlatform=null;
//    			String appUpdateDate=null;
//    			if(raw1!=null)
//    			{
//    				String temp[]=raw.split("：");
//    				osPlatform=temp[1].replace("更新日期", "");
//    				appUpdateDate=temp[2];
//    			}
    			String appDownloadUrl=page.getHtml().xpath("//div[@class='f1']/a/@href").toString();
    			String appDescription=page.getHtml().xpath("//p[@class='mt_20 l_h_24']/text()").toString();
    			if(appDescription!=null)
    			{
    				appDescription=usefulInfo(appDescription);
    			}
				String appType=null;
    			 List<String> appScreenshot = page.getHtml().xpath("//ul[@class='poster-list']/li/a/img/@original").all();
    			 Apk apk = null;
    		        if (null != appName && null != appDownloadUrl) {
    		            apk = new Apk(appName, appDetailUrl, appDownloadUrl, null, null, null, null, null != appType ? appType : "APK");
    		            apk.setAppDescription(appDescription);
    		            apk.setAppScreenshot(appScreenshot);
    		           // apk.setAppCommentUrl(appCommentUrl);
    		           // apk.setAppComment(appComment);
    		            //apk.setAppDownloadTimes(dowloadNum);
    		           // apk.setAppCategory(appCategory);
    		           // apk.setAppTag(appTag);
    		            System.out.println("appName="+appName);
    					System.out.println("appDetailUrl="+appDetailUrl);
    					System.out.println("appDownloadUrl="+appDownloadUrl);
//    					System.out.println("osPlatform="+osPlatform);
//    					System.out.println("appVersion="+appVersion);
//    					System.out.println("appSize="+appSize);
//    					System.out.println("appUpdateDate="+appUpdateDate);
    					//System.out.println("appType="+appType);
    					//System.out.println("appVenderName="+appVenderName);
    					//System.out.println("appDownloadedTime="+appDownloadedTime);
    					System.out.println("appDescription="+appDescription);
    					//System.out.println("appTag="+appTag);
    					System.out.println("appScrenshot="+appScreenshot);
    					//System.out.println("appCategory="+appCategory);
    		        }
    		
    		//        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
    		//                        ", appScreenhost:{}, appCommentUrl:{}, appComment:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
    		//                appSize, appType, osPlatform, appUpdateDate, dowloadNum, appTag, appCategory, appScreenshot, appCommentUrl, appComment, appDescription);
    		
    		        return apk;
    		}
    	}
    }
    
    private static String usefulInfo(String allinfoString)
	{
		String info = null;
		while(allinfoString.contains("<"))
			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
		info = allinfoString.replace("\n", "").replace(" ", "");
		return info;
	}
//	 private static JSONObject getMap(){//获取所有下载链接的格式
//	
//	String line="";
//	String sourcefile="";
//	try {				
//		URL url=new URL("http://www.cr173.com/inc/SoftLinkType.js");		
//		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//		 while ((line = reader.readLine()) != null){
//			 	sourcefile=sourcefile+line;
//			 	
//			}
//		 System.out.println(sourcefile);				
//	} catch (Exception e) {
//	}	
//	JSONObject  jasonObject = JSONObject.fromObject(sourcefile.substring(sourcefile.indexOf("{"), sourcefile.indexOf("}")+1));
//
//	return jasonObject;
//}

}
