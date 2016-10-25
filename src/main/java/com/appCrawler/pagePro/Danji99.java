package com.appCrawler.pagePro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.appCrawler.pagePro.apkDetails.Apk3310_Detail;
import com.appCrawler.pagePro.apkDetails.Danji99_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.Json2Object;

/**
 * 玉米助手手机app
 * 渠道编号:381
 * http://android.ymzs.com/search/?keyword=*#*#*#&page=###
 */
public class Danji99 implements PageProcessor{
	Site site = Site.me().setCharset("utf-8").setRetryTimes(2).setSleepTime(3);
	@Override
	public Apk process(Page page) {
	
			return null;
		
	}

	//@Override
	public Site getSite() {
		return site;
	}

	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		if(page.getUrl().regex("http://android\\.ymzs\\.com/search/\\?keyword=.*").match()){
			String url=page.getUrl().toString();
			System.out.println(url+"****");
			String section[]=url.split("&");
			List<Apk> apkList=new ArrayList<Apk>();
			for(int i=1;;i++)
			{
				//重新构造url
				String newUrl=section[0]+"&page="+i;
				String json1=SinglePageDownloader.getHtml(newUrl);
				if(json1.contains("pkgName"))
				{
					String temp1[]=json1.split("\"list\":");
					String str=temp1[1].substring(0,temp1[1].length()-2);
					List<String> packageNames=Json2Object.ExtraInfo1(str,"pkgName");
					for(String packageName:packageNames)
					{
						//构造app详情url地址
						String json2=SinglePageDownloader.getHtml("http://android.ymzs.com/detail/"+packageName+"?userId=&imei=866329024603181");
						String temp2[]=json2.split("\"Data\":");
						String str2="["+temp2[1].substring(0,temp2[1].length()-1)+"]";
						//List<String> packageNames=Json2Object.ExtraInfo1(str2,"iconUrl");
						//System.out.println(packageNames);
						apkList.add(Danji99_Detail.getApkDetail(str2));
					}
				}
				else{
					break;
				}
			
			}
			return apkList;
		}
		return null;
	}

}
