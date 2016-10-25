package com.appCrawler.pagePro.apkDetails;

import java.io.ByteArrayInputStream;
import us.codecraft.webmagic.Page;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
/*
 * 
 */
public class VivioMarket_Detail2 {

	//public static void Prse(String id) throws Exception{
//	public static Apk getApkDetail(String id) throws Exception{
//			String xml="http://appstore.bbk.com/port/package/?app_version=401&id="+id+"&need_comment=1&content_complete=1&cs=1";
//			System.out.println(SinglePageDownloader.getHtml(xml));
//			List<String> appScrenshot=matchString(SinglePageDownloader.getHtml(xml));
//			String str=ProcessAttr(SinglePageDownloader.getHtml(xml));
//			InputStream inputStream = new ByteArrayInputStream(fillXml(str).getBytes());
//			SAXReader saxReader = new SAXReader();
//			Reader read = new InputStreamReader(inputStream,"utf-8");
//			Document document = saxReader.read(read); 
//			Element root = document.getRootElement();
//			List list  = root.elements("Package");
//			for(Iterator i = list.iterator();i.hasNext();) {
//				Element resourceitem = (Element) i.next();
//				String appName= resourceitem.element("title_zh").getText();
//				String appVenderName =resourceitem.element("developer").getText();
//				String appDownloadedTime=resourceitem.element("download_count").getText();
//				String rawString = resourceitem.element("size").getText();
//				String appSize=rawString+"KB";
//				String appVersion=resourceitem.element("version_name").getText();
//				String appDownloadUrl=resourceitem.element("download_url").getText();
//				String appUpdateDate=resourceitem.element("upload_time").getText();
//				String appDescription=resourceitem.element("introduction").getText();
//				
//				if(appName != null && appDownloadUrl != null){
//					Apk apk = new Apk(appName,null,appDownloadUrl,null ,appVersion,appSize,appUpdateDate,null,null);
////					Apk(String appName,String appMetaUrl,String appDownloadUrl,String osPlatform ,
////							String appVersion,String appSize,String appTsChannel, String appType,String cookie){	
//					apk.setAppDownloadTimes(appDownloadedTime);
//					apk.setAppVenderName(appVenderName);
//					//apk.setAppTag(appTag);
//					apk.setAppScreenshot(appScrenshot);
//					apk.setAppDescription(appDescription);
//					Page page=new Page();
//					page.putField("apk", apk);
//					
//					System.out.println(appName);
//					System.out.println(appVenderName);
//					System.out.println(appDownloadedTime);
//					System.out.println(appSize);
//					System.out.println(appVersion);
//					System.out.println(appDownloadUrl);
//					System.out.println(appUpdateDate);
//					System.out.println(appDescription);
//					System.out.println(appScrenshot);
//					return apk;
//			}
//		}
//			return null;
//
//	}
//	
//		public static List<String> matchString(String str) throws Exception
//		{
//			int index=str.indexOf("<ScreenshotList>");
//			int endIndex=str.indexOf("</ScreenshotList>");
//			String Temp=str.substring(index+1, endIndex);
//			Temp=Temp.replaceAll("<screenshot>", "");
//			Temp=Temp.replaceAll("</screenshot>", ";");
//			String []urlArray=Temp.split(";");
//			List<String> list=new ArrayList<String>();
//			for(int i=0;i<urlArray.length;i++)
//			{
//				list.add(getText(urlArray[i]));
//			}
//			return list;
//			
//		}
//		public static String getText(String a)
//		{
//			int startIndex = 0;
//			int endIndex=0;
//			for(int i=0;i<a.length();i++)
//			{
//				if(a.charAt(i)=='[')
//				{
//					startIndex=i;
//				}
//				if(a.charAt(i)==']')
//				{
//					endIndex=i;
//					break;
//				}
//			}
//			return a.substring(startIndex+1,endIndex);
//		}
//		
//		public static String ProcessAttr(String temp)
//		{
//			int index=temp.indexOf("Package");
//			int endIndex=0;
//			for(int i=index;;i++)
//			{
//				if(temp.charAt(i)=='>')
//				{
//					endIndex=i;
//					break;
//				}
//			}
//			temp=temp.replace(temp.substring(index,endIndex),"Package");
//			return temp;
//			//System.out.println(temp);
//		}
//		
//		public static String fillXml(String temp)
//		{
//			temp=temp.replace("<Package>", "<Package><Package>");
//			temp=temp.replace("</Package>", "</Package></Package>");
//			return temp;
//		}
//	
}