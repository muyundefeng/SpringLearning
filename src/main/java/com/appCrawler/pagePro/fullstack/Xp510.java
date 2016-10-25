package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Jz5u_Detail;
import com.appCrawler.pagePro.apkDetails.Xp510_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Xp手机之家  http://www.xp510.com/
 * Xp510 #274
 * @author tianlei
 */


public class Xp510 implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
		Set<String> cacheSet = Sets.newHashSet();
        String nextUrl=null;
		String nextPage=null;
        List<String> urlList=page.getHtml().xpath("//div[@class='ct2']/a/@href").all();
		nextPage = page.getHtml().xpath("//div[@class='page-green']").toString();
		if(nextPage!= null)
		{
		nextUrl = getNextUrl(nextPage);		 
		}

        if(nextUrl != null){
        	cacheSet.add(nextUrl);
        }
		cacheSet.addAll(urlList);
		for(String url : cacheSet){

		   if(PageProUrlFilter.isUrlReasonable(url)&&!url.contains("download")){
				page.addTargetRequest(url);
			}
		}
		//提取页面信息
		if(page.getUrl().regex("http://www.xp510.com/shouji/android/。*").match()){		
			Apk apk = Xp510_Detail.getApkDetail(page);		
			page.putField("apk", apk);
			if(page.getResultItems().get("apk") == null){
				page.setSkip(true);
				}
			}
		else{
			page.setSkip(true);
			}
		return null;
	}
	@Override
	public List<Apk> processMulti(Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Site getSite() {
		return site;
	}
	
	private static String getNextUrl(String str){
    	String tmp=null;
		String regex="<a href=\"(.*)\" class=\"a1\">下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
 
        while(matcher.find()){
        	
        	tmp=matcher.group(1).toString();
        	
        }
    	return tmp;   	
    }
	
}
