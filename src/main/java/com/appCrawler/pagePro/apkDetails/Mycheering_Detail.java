package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
/**
 * 青橙游戏助手 网站主页:http://apps.mycheering.com/ 
 * 渠道编号:365 
 * 相关的app信息保存在json格式的文件之中
 */

public class Mycheering_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mycheering_Detail.class);
	public static Apk getApkDetail(Map map){
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
		appName = map.get("ae").toString();	
		appVenderName=map.get("aj").toString();
		appDescription=map.get("aq").toString();
		appDownloadUrl=map.get("aa").toString();
		appCategory=map.get("e").toString();
		//String rawStr=map.get("av").toString();
		appScrenshot=ExtraPic(map.get("av").toString());
		appScrenshot=ExtraPic(map.get("av").toString());
		if(appName != null && appDownloadUrl != null){
			apk = new Apk(appName,appDetailUrl,appDownloadUrl,osPlatform ,appVersion,appSize,appUpdateDate,appType,null);
			apk.setAppDownloadTimes(appDownloadedTime);
			apk.setAppVenderName(appVenderName);
			apk.setAppTag(appTag);
			apk.setAppScreenshot(appScrenshot);
			apk.setAppDescription(appDescription);
			apk.setAppCategory(appCategory);
			System.out.println("appName="+appName);
			System.out.println("appDetailUrl="+appDetailUrl);
			System.out.println("appDownloadUrl="+appDownloadUrl);
			System.out.println("osPlatform="+osPlatform);
			System.out.println("appVersion="+appVersion);
			System.out.println("appSize="+appSize);
			System.out.println("appUpdateDate="+appUpdateDate);
			System.out.println("appType="+appType);
			System.out.println("appVenderName="+appVenderName);
			System.out.println("appDownloadedTime="+appDownloadedTime);
			System.out.println("appDescription="+appDescription);
			System.out.println("appTag="+appTag);
			System.out.println("appScrenshot="+appScrenshot);
			System.out.println("appCategory="+appCategory);
						
		}
		else return null;
		
		return apk;
	}
	
//	private static String usefulInfo(String allinfoString)
//	{
//		String info = null;
//		while(allinfoString.contains("<"))
//			if(allinfoString.indexOf("<") == 0) allinfoString = allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//			else if(allinfoString.contains("<!--")) allinfoString = allinfoString.substring(0,allinfoString.indexOf("<!--")) + allinfoString.substring(allinfoString.indexOf("-->")+3,allinfoString.length());
//			else allinfoString = allinfoString.substring(0,allinfoString.indexOf("<")) + allinfoString.substring(allinfoString.indexOf(">")+1,allinfoString.length());
//		info = allinfoString.replace("\n", "").replace(" ", "");
//		return info;
//	}
	
	private static List<String> ExtraPic(String str)
	{
		List<String> returnList=new ArrayList<String>();
		String picStr[]=str.split("}");
		//System.out.println(picStr[1]);
		for(int i=1;i<picStr.length-1;i++)
		{
			String temp[]=i==1?picStr[i].split(","):picStr[i].substring(1).split(",");
			for(int t=1;t<temp.length;t++)
			{
				int j=0;
				while(true)
				{
					//System.out.println(temp[t]);
					if(temp[t].charAt(j)=='=')
						break;
					j++;
				}
				returnList.add(temp[t].substring(j+1));
			}
		}
	return returnList;
//	List<String> returnList=new ArrayList<String>();
//	ObjectMapper objectMapper=new ObjectMapper();
//	//System.out.println(str+"/////");
//	try {
//		List<LinkedHashMap<String, Object>> list = objectMapper.readValue(str, List.class);
//         System.out.println(list.size()+"");
//         for (int j = 0; j < list.size(); j++) {
//             Map<String, Object> map = list.get(j);
//             System.out.println((String) map.get("b3")+"*****");
//             System.out.println( map.get("b4"));
//             returnList.add((String)map.get("b3"));
//             returnList.add((String)map.get("b4"));
//         }
//	}
//         catch (Exception e) {
//			// TODO: handle exception
//		}
	}
}
