package com.appCrawler.pagePro.apkDetails;
import java.util.ArrayList;
/**
 * 玉米助手手机app
 * 渠道编号:381
 * 伪造主页:http://www.99danji.com/az/5090/
 */
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.utils.Json2Object;


public class Danji99_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Danji99_Detail.class);
	public static Apk getApkDetail(String infos){
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
		System.out.println(infos);
		appName=Json2Object.ExtraInfo1(infos, "name").toString();
		appName=appName.substring(1,appName.length()-1);
		appUpdateDate=Json2Object.ExtraInfo1(infos,"updateDate").toString();
		appUpdateDate=appUpdateDate.substring(1,appUpdateDate.length()-1);
		appSize=Json2Object.ExtraInfo1(infos, "fileSize").toString();
		appSize=appSize.substring(1,appSize.length()-1);
		appDownloadedTime=Json2Object.ExtraInfo1(infos, "downloadCount").toString();
		appDownloadedTime=appDownloadedTime.replace("人安装", "");
		appDownloadedTime=appDownloadedTime.substring(1,appDownloadedTime.length()-1);
		appDownloadUrl=Json2Object.ExtraInfo1(infos, "downloadUrl").toString();
		appDownloadUrl=appDownloadUrl.substring(1,appDownloadUrl.length()-1);
		appCategory=Json2Object.ExtraInfo1(infos, "category").toString();
		appCategory=appCategory.substring(1,appCategory.length()-1);
		appVersion=Json2Object.ExtraInfo1(infos, "versionName").toString();
		appVersion=appVersion.substring(1,appVersion.length()-1);
		String raw=Json2Object.ExtraInfo1(infos, "detail").toString();
		String temp[]=infos.split(",\"detail\":");
		String temp1[]=temp[1].split(",\"id\"");
		String tString="["+temp1[0]+"]";
		
		List<String> des=Json2Object.ExtraInfo1(tString,"description");
		appDescription=des.get(0);
		List<String> pics=Json2Object.ExtraInfo1(tString,"screenShots");
		String pic=(pics.get(0).replace("[", "")).replace("]", "");
		List<String> picList=new ArrayList<String>();
		String temp2[]=pic.split(",");
		for(int i=0;i<temp2.length;i++)
		{
			picList.add(temp2[i]);
		}
		if(appDownloadedTime.contains("万"))
		{//1690000.0
			try{
				appDownloadedTime=Integer.parseInt(appDownloadedTime.replace("万", ""))*10000+"";
			}
			catch(Exception e)
			{
				appDownloadedTime=null;
			}
			
//			if(appDownloadedTime.contains("E"))
//			{
//				appDownloadedTime=null;
//			}
//			else{
//				if(appDownloadedTime.contains("."))
//				{
//					appDownloadedTime=appDownloadedTime.split(".")[0];
//				}
//			}
		}
		appScrenshot=picList;
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
}
