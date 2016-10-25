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
import com.appCrawler.pagePro.apkDetails.Xyapp_Detail;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.Json2Object;

/**
 * xy手游
 * 本渠道为手机客户端app,返回数据格式为json数据格式
 * 渠道编号:380
 * url地址为:
 * http://apk.interface.xyzs.com/apk/1.7.0/search/result?p=1!%!%!%keyword=*#*#*#!%!%!%deviceimei=a65cb6375b2559ab32ace5c879409e37!%!%!%clientversion=18
 */
public class Xyapp implements PageProcessor{
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
		if(page.getUrl().regex("http://apk\\.interface\\.xyzs\\.com/apk/1\\.7\\.0/search/result\\?.*").match()){
			List<Apk> apkList=new ArrayList<Apk>();
			String json=SinglePageDownloader.getHtml(page.getUrl().toString());
			//对json进行相关处理
			//jsons.add(json);
			String json2[]=json.split("\"result\":");
			System.out.println(json2[1]);
			String json3[]=json2[1].split(",\"info\":");
			//String string=json3[0].substring(2,json3[0].length()-1);
			List<String> packageNames=Json2Object.ExtraInfo1(json3[0],"f_packagename");
			//构造详情页的url链接
			for(String packageName:packageNames)
			{
				String appDetailUrl="http://apk.interface.xyzs.com/apk/1.7.0/app?packagename="+packageName+"&deviceimei=a65cb6375b2559ab32ace5c879409e37&clientversion=18";
				String json6=SinglePageDownloader.getHtml(appDetailUrl);
				String json4[]=json6.split("apps");
				//System.out.println(json4[1]);
				String json5[]=json4[1].split("gifts");
				//System.out.println(json3[0]);
				String JSON="["+json5[0].substring(2,json5[0].length()-2)+"]";
				apkList.add(Xyapp_Detail.getApkDetail(JSON));
			}
			return apkList;
		}
		return null;
	}

}
