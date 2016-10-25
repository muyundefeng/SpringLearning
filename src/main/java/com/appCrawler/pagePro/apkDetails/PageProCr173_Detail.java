package com.appCrawler.pagePro.apkDetails;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.PageProUrlFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 西西软件园[中国] app搜索抓取
 * url:http://so.cr173.com/?keyword=mt&searchType=youxi
 *
 * @version 1.0.0
 */
public class PageProCr173_Detail {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PageProCr173_Detail.class);

    public static Apk getApkDetail(JSONObject jsonObject,Page page){
        // 获取dom对象
        Html html = page.getHtml();

//        // 找出对应需要信息
//        String appVersion = null;
//        String appType = null;
//        String appDownloadUrl = null;
//        String osPlatform= null;
//        String appSize= null;
//        String appUpdateDate= null;
//        String appDetailUrl = page.getUrl().toString();
//        String appName =html.xpath("//div[@class='mj_yyjs font-f-yh']/text()").get();;
//        String appDescription =null;
//        String downladInfo = html.xpath("//ul[@class='ul_Address']/script").toString();
//        String appCategory=null;        
//        List<String> appScreenshot = html.xpath("//div[@id='mj_tu_1']/img/@src").all();
//        String appTag = null;
//        appCategory = StringUtils.substringAfterLast(html.xpath("//div[@class='mj_info font-f-yh']/ul/li[6]/text()").toString(), "：");
//        String appCommentUrl = null;
//      //  String appComment = html.xpath("//div[@id='h_d']").get();
//        String dowloadNum = null;
//        
//        if(html.xpath("//dl[@id='big_tit']/dt/h1/text()").toString()!=null){
//        	appName=html.xpath("//dl[@id='big_tit']/dt/h1/text()").toString();
//            //downladInfo = html.xpath("//ul[@class='ul_Address']/script").toString();
//            osPlatform = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[1]/text()").toString(), ": ");
//            appSize = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[3]/text()").toString(), ": ");
//            appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[4]/text()").toString(), ": ");
//        }
//        else if(html.xpath("//h1[@id='softtitle']/text()").toString()!=null){
//            appName = html.xpath("//h1[@id='softtitle']/text()").toString();
//            //appVersion=StringUtils.substringAfterLast(appName, " ");
//            osPlatform = StringUtils.substringAfter(html.xpath("//div[@class='c_soft_info']/ul/li[8]/text()").toString(), ": ");
//            appSize = StringUtils.substringAfter(html.xpath("//div[@class='c_soft_info']/ul/li[1]/text()").toString(), ": ");           
//            appUpdateDate = StringUtils.substringAfter(html.xpath("//div[@class='c_soft_info']/ul/li[2]/text()").toString(), ": ");
//            appDescription =html.xpath("//div[@id='content']/p[1]/text()").get();
//            appScreenshot=html.xpath("//a[@class='pic_0']/img/@src").all();
//        }
//        else if(html.xpath("//dl[@class='d-info']/dt/h1/text()").toString()!=null){
//        	appName = html.xpath("//dl[@class='d-info']/dt/h1/text()").toString();
//            osPlatform = html.xpath("//dl[@class='d-info']/dd[1]/p[2]/strong/text()").toString();
//            appSize =  html.xpath("//dl[@class='d-info']/dd[2]/p[1]/strong/text()").toString();
//            appUpdateDate = html.xpath("//dl[@class='d-info']/dd/p[1]/text()").toString();
//            appDescription = html.xpath("//div[@class=game-info']/p[1]/text()").get();
//        }
//        else{
//        	appName = html.xpath("//div[@class='info']/h1/a/text()").toString();	
//        	osPlatform = html.xpath("////div[@class='info']/ul/li[8]/text()").toString();
//            appSize =  html.xpath("////div[@class='info']/ul/li[1]/text()").toString();
//            appUpdateDate = html.xpath("////div[@class='info']/ul/li[9]/text()").toString();
//            appDescription = html.xpath("//div[@class=game-info']/p[1]/text()").get();
//            appCategory=html.xpath("//div[@class=game-info']/p[2]/text()").get();
//        }
//        // 处理下载url
//        String typeId = StringUtils.substringBetween(downladInfo, "TypeID:\"", "\",");
//        if (null != urlMap) {
//            String urlInfo = urlMap.get("siteId_" + typeId);
//            if (StringUtils.isNotEmpty(urlInfo)) {
//                appDownloadUrl = StringUtils.substringBetween(urlInfo, "||", ",") + StringUtils.substringBetween(downladInfo, "Address:\"", "\"");
//                if(!appDownloadUrl.endsWith("apk"))
//                	appDownloadUrl=null;
//                		
//            }
//        }

        
      // 找出对应需要信息
      String appVersion = null;
      String appType = null;
      String appDownloadUrl = null;
      String osPlatform= null;
      String appSize= null;
      String appUpdateDate= null;
      String appDetailUrl = page.getUrl().toString();
      String appName =null;
      String appDescription =null;
      String appCategory=null;        
      List<String> appScreenshot = null;
      String appTag = null;
      appCategory = null;
      String appCommentUrl = null;
      String appDownloadTimes = null;
      String appVenderName = null;
      String nameString = null;
      if(page.getUrl().toString().contains("/azyx/")//游戏详情页 种类1  http://www.cr173.com/azyx/81024.html
    		  && html.xpath("//dl[@id='big_tit']/dt/h1/text()").toString()!=null){
    	  nameString=html.xpath("//dl[@id='big_tit']/dt/h1/text()").toString();
          appCategory = html.xpath("//div[@id='navhover']/a[3]/text()").toString();
          osPlatform = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[1]/text()").toString(), ": ");
          appSize = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[3]/text()").toString(), ": ");
          appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[4]/text()").toString(), ": ");
          		appUpdateDate = appUpdateDate.replace("/", "-");
          appVenderName = StringUtils.substringAfter(html.xpath("//ul[@id='gmcfg']/li[6]/text()").toString(), ": ");
    	  appDescription = usefulInfo(html.xpath("//div[@class='content_top']").toString());
    	  appScreenshot = html.xpath("//div[@class='screenshots']//img/@src").all();
    	  appDownloadUrl = getDownloadUrl(jsonObject,html.xpath("//ul[@class='ul_Address']/script").toString());
    	 if(appDownloadUrl!=null&&!appDownloadUrl.endsWith(".apk"))
    	 {
    		 appDownloadUrl=null;
    	 }
    	 if(appDownloadUrl!=null&&appDownloadUrl.contains("//"))
    	 {
    		 appDownloadUrl=appDownloadUrl.replace("//", "/");
    		 appDownloadUrl=appDownloadUrl.replace("http:/", "http://");
    	 }
    	  System.out.println("appName="+appName);
 		 System.out.println("appDetailUrl="+appDetailUrl);
 		 System.out.println("appDownloadUrl="+appDownloadUrl);
 		 System.out.println("osPlatform="+osPlatform);
 		 System.out.println("appVersion="+appVersion);
 		 System.out.println("appSize="+appSize);
 		 System.out.println("appUpdateDate="+appUpdateDate);
 		 System.out.println("appType="+appType);
 		 System.out.println("appVenderName="+appVenderName);
 		// System.out.println("appDownloadedTime="+appDownloadedTime);
 		 System.out.println("appDescription="+appDescription);
 		 System.out.println("appTag="+appTag);
 		 System.out.println("appScreenshot="+appScreenshot);
 		 System.out.println("appCategory="+appCategory);
    	  
      }
      else if(page.getUrl().toString().contains("/azyx/")){ //游戏详情页 种类2  http://www.cr173.com/azyx/148706.html    	
    	  nameString = html.xpath("//dl[@class='d-info']/dt/h1/text()").toString();    	  
    	  if(nameString == null ) return null;
    	  appCategory = html.xpath("//div[@class='d_link']/p/a[3]/text()").toString();
    	  appUpdateDate = html.xpath("//dl[@class='d-info']/dd[1]/p[1]/text()").toString();
    	  appSize = html.xpath("//dl[@class='d-info']/dd[2]/p[1]/strong/text()").toString();
    	  appDescription = usefulInfo(html.xpath("//div[@class='game-info']").toString());
    	  appScreenshot = html.xpath("//ul[@class='maincell']//img/@src").all();
    	  List<String> urlList = html.links("//div[@class='game-info']").all();
    	  for (String temp : urlList) {
    		  if(PageProUrlFilter.isUrlReasonable(temp))
					page.addTargetRequest(temp);
    	  }
    	 appDownloadUrl = getDownloadUrl(jsonObject,html.xpath("//div[@class='lt-cont down']//ul/script").toString());
    	 if(appDownloadUrl!=null&&!appDownloadUrl.endsWith(".apk"))
     	 {
     		 appDownloadUrl=null;
     	 }
    	 System.out.println("appName="+appName);
 		 System.out.println("appDetailUrl="+appDetailUrl);
 		 System.out.println("appDownloadUrl="+appDownloadUrl);
 		 System.out.println("osPlatform="+osPlatform);
 		 System.out.println("appVersion="+appVersion);
 		 System.out.println("appSize="+appSize);
 		 System.out.println("appUpdateDate="+appUpdateDate);
 		 System.out.println("appType="+appType);
 		 System.out.println("appVenderName="+appVenderName);
 		// System.out.println("appDownloadedTime="+appDownloadedTime);
 		 System.out.println("appDescription="+appDescription);
 		 System.out.println("appTag="+appTag);
 		 System.out.println("appScreenshot="+appScreenshot);
 		 System.out.println("appCategory="+appCategory);
      }else{//软件详情页
    	  nameString = html.xpath("//h1[@id='softtitle']/text()").toString();
    	  appSize = StringUtils.substringAfter(html.xpath("//ul[@class='info']/li[1]/text()").toString(),":");
    	  			appSize = appSize.replace(" ", "");
    	  appUpdateDate = StringUtils.substringAfter(html.xpath("//ul[@class='info']/li[2]/text()").toString(),":");
    	  			appUpdateDate = appUpdateDate.replace(" ", "");
    	  appVenderName = StringUtils.substringAfter(html.xpath("//ul[@class='info']/li[4]/text()").toString(),":");
    	  appDescription = usefulInfo(html.xpath("//div[@data-ride='mfolder']").toString());
    	  appScreenshot = html.xpath("//div[@id='screen_show']//img/@src").all();
    	  appDownloadUrl = getDownloadUrl(jsonObject,html.xpath("//ul[@class='ul_Address']/script").toString());
    	  if(appDownloadUrl!=null&&!appDownloadUrl.endsWith(".apk"))
     	 {
     		 appDownloadUrl=null;
     	 }
    	  System.out.println("appName="+appName);
 		 System.out.println("appDetailUrl="+appDetailUrl);
 		 System.out.println("appDownloadUrl="+appDownloadUrl);
 		 System.out.println("osPlatform="+osPlatform);
 		 System.out.println("appVersion="+appVersion);
 		 System.out.println("appSize="+appSize);
 		 System.out.println("appUpdateDate="+appUpdateDate);
 		 System.out.println("appType="+appType);
 		 System.out.println("appVenderName="+appVenderName);
 		// System.out.println("appDownloadedTime="+appDownloadedTime);
 		 System.out.println("appDescription="+appDescription);
 		 System.out.println("appTag="+appTag);
 		 System.out.println("appScreenshot="+appScreenshot);
 		 System.out.println("appCategory="+appCategory);
      }
      
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
        appVersion = appVersion.replaceAll("[\\u4e00-\\u9fa5]", "").replace(" ", "");
        appName = appName.replace(" ", "");

        Apk apk = null;
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScreenshot);
            apk.setAppCommentUrl(appCommentUrl);
            apk.setAppDownloadTimes(appDownloadTimes);
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
   		// System.out.println("appDownloadedTime="+appDownloadedTime);
   		 System.out.println("appDescription="+appDescription);
   		 System.out.println("appTag="+appTag);
   		 System.out.println("appScreenshot="+appScreenshot);
   		 System.out.println("appCategory="+appCategory);
        }

        LOGGER.debug("name:{}, version: {}, url:{}, size: {}, appType: {}, os: {}, date:{}, downlaodNum:{}, , appTag:{}, appCategory:{}" +
                        ", appScreenhost:{}, appDescription:{}", appName, appVersion, appDownloadUrl,
                appSize, appType, osPlatform, appUpdateDate, appDownloadTimes, appTag, appCategory, appScreenshot,  appDescription);

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
    
    private static String getDownloadUrl(JSONObject jsonObject,String url){
    	//System.out.println(url);
    	if(url == null) return null;   	
    	if(jsonObject == null) return null;
        String appDownloadUrl = null;
      String typeId = StringUtils.substringBetween(url, "TypeID:\"", "\",");
      if (null != jsonObject) {
    	  if(jsonObject.get("siteId_"+typeId) ==null)
    		  return StringUtils.substringBetween(url, "Address:\"", "\"");
          String urlInfo = jsonObject.get("siteId_"+typeId).toString();
  //        System.out.println("urlInfo="+urlInfo);
          if (StringUtils.isNotEmpty(urlInfo)) {
              appDownloadUrl = StringUtils.substringBetween(urlInfo, "||", ",") + StringUtils.substringBetween(url, "Address:\"", "\"");
              if(!appDownloadUrl.endsWith("apk"))
              	appDownloadUrl=StringUtils.substringBetween(url, "Address:\"", "\"");
              		
          }
      } 
     // System.out.println(appDownloadUrl);
    	return appDownloadUrl;
    }
    
   
  

}
