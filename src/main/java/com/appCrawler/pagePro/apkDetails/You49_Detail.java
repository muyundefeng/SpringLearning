package com.appCrawler.pagePro.apkDetails;




import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;

import java.util.List;
import java.util.Map;

/**
 * 49游 http://m.49you.com/daquan/
 * 渠道编号：324
 * @author DMT
 * 所需要的详细信息在保存在json格式的文件。
 */

public class You49_Detail {


    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(You49_Detail.class);

    public static Apk getApkDetail(Map<String,Object> map){
    	Apk apk = null;
		String appName = null;				//app名字
		String appDetailUrl = null;			//具体页面url
		String appDownloadUrl = null;		//app下载地址
		String osPlatform = null ;			//运行平台
		String appVersion = null;			//app版本
		String appSize = null;				//app大小
		String appUpdateDate = null;		//更新日期
		String appType = null;				//下载的文件类型 apk？zip？rar？
		String appVenderName = null;			//app开发者  APK这个类中尚未添加
		String appDownloadedTime=null;		//app的下载次数
		String appDescription = null;		//app的详细描述
		List<String> appScrenshot = null;			//app的屏幕截图
		String appTag = null;				//app的应用标签
		String appCategory = null;			//app的应用类别 
        // 找出对应需要信息
        appName = unicode2Str(map.get("game_name").toString());
        appDownloadUrl=map.get("android_down_link").toString();
       // appDownloadUrl=appDownloadUrl.replaceAll("\\","");
        appDescription=map.get("description").toString();
        appDescription=unicode2Str(appDescription);
       // String appScrenshot1=map.get("game_image").toString();
        //System.out.println(appScrenshot1);
        appSize =map.get("size").toString()+"MB";
        appVersion=map.get("version").toString();

        System.out.println("appName="+appName);
		System.out.println("appDetailUrl="+appDetailUrl);
		System.out.println("appDownloadUrl="+appDownloadUrl);
		System.out.println("appVersion="+appVersion);
		System.out.println("appSize="+appSize);
		System.out.println();
        
        if (null != appName && null != appDownloadUrl) {
            apk = new Apk(appName, appDetailUrl, appDownloadUrl, osPlatform, appVersion, appSize, appUpdateDate, null != appType ? appType : "APK");
            apk.setAppDescription(appDescription);
            apk.setAppScreenshot(appScrenshot);
            //apk.setAppCommentUrl(appCommentUrl);
            apk.setAppDownloadTimes(appDownloadedTime);
           // apk.setAppComment(appComment);
            apk.setAppCategory(appCategory);
            apk.setAppTag(appTag);
        }

        return apk;
    }
    //将unicode码转化为字符串
    public static String unicode2Str(String str) {
        StringBuffer sb = new StringBuffer();
        String[] arr = str.split("\\\\u");
        int len = arr.length;
        sb.append(arr[0]);
        for(int i=1; i<len; i++){
            String tmp = arr[i];
            char c = (char)Integer.parseInt(tmp.substring(0, 4), 16);
            sb.append(c);
            sb.append(tmp.substring(4));
        }
        return sb.toString();
    }
    
}
