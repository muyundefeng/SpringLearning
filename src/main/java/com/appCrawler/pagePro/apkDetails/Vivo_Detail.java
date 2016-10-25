package com.appCrawler.pagePro.apkDetails;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
/**
 * vivo  http://zs.vivo.com.cn/app.php
 * Play #176
 * @author DMT
 */

public class Vivo_Detail {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Vivo_Detail.class);
	public static List<Apk> getApkDetail(Page page){
		//System.out.println(page.getHtml().toString());
		List<Apk> apks = new LinkedList<Apk>();
					
		for(int j=1;j<22;j++){
			String appCategory = page.getHtml().xpath("//div[@id='needApp']/div["+j+"]/span/text()").toString();;			//app的应用类别 
		for(int i=1;;i++){
			
			String selectable = page.getHtml().xpath("//div[@id='needApp']/ul["+j+"]/li["+i+"]").toString();
			if(selectable == null) 
				break;	
			String appDetailUrl = page.getUrl().toString();
			String appName = page.getHtml().xpath("//div[@id='needApp']/ul["+j+"]/li["+i+"]/div[2]/text()").toString();
			String appSize = page.getHtml().xpath("//div[@id='needApp']/ul["+j+"]/li["+i+"]/div[3]/text()").toString();
			String appDownloadedTime = getTrueTimes(page.getHtml().xpath("//div[@id='needApp']/ul["+j+"]/li["+i+"]/div[4]/text()").toString());
			String appDownloadUrl = page.getHtml().xpath("//div[@id='needApp']/ul["+j+"]/li["+i+"]/div[5]/a/@href").toString();
			System.out.println("appName=" + appName);
			
			System.out.println("appDetailUrl=" + appDetailUrl);
			
			System.out.println("appDownloadUrl=" + appDownloadUrl);
			
			System.out.println("appSize=" + appSize);
		
			System.out.println("appDownloadedTime=" + appDownloadedTime);
			System.out.println("appCategory=" + appCategory);
			System.out.println();
			
			if(appName != null && appDownloadUrl != null){
			Apk apk =new Apk(appName,null,appDownloadUrl,null ,null,appSize,null,null,null);
			apk.setAppDownloadTimes(appDownloadedTime);		
			apk.setAppCategory(appCategory);
			apks.add(apk);		
			
			
			}
		
		}	
		}
		
	
	return apks;
	
	}
	
	public static String getTrueTimes(String appDownloadedTime){
		String times = "";
		 float f = 0;
		if(appDownloadedTime == null) 
			return null;
		if(!appDownloadedTime.contains("+"))
			return appDownloadedTime.substring(0,appDownloadedTime.indexOf("次")-1);
		if(appDownloadedTime.contains("万")){
			times=appDownloadedTime.substring(0,appDownloadedTime.indexOf("万"));
			try{
				 f= Float.parseFloat(times);
				}catch(Exception e){}
		}
		float full = f*10000;
		int time=(int)full;
		return String.valueOf(time);
		
		
	}
	
}
