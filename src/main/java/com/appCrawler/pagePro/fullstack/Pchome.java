package com.appCrawler.pagePro.fullstack;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aatouch_Detail;
import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Pchome_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.SinglePageDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * Pchome[中国] http://www.pchome.net/
 * Pchome #56
 * @author tianlei
 */


public class Pchome implements PageProcessor{

	Site site = Site.me().setCharset("gbk").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Apk process(Page page) {
	
		Set<String> cacheSet = Sets.newTreeSet();
		String nextPage = null;	
		List<String> nextUrl = null;
		if("http://www.pchome.net/".equals(page.getUrl().toString())){
			cacheSet.add("http://download.pchome.net/android/list-377-1-1.html");
		}else{			
			List<String> urlList=page.getHtml().xpath("//dl[@class='softs']/dd/h5/a/@href").all();
			cacheSet.addAll(urlList);	
			nextPage = page.getHtml().xpath("//div[@class='page']").toString();		
			if(nextPage != null){
				nextUrl = getNextUrl(nextPage);
			}				
			if(nextUrl != null){		
				cacheSet.addAll(nextUrl);
				System.out.println(nextUrl);
			}
				
		}

		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				String urltmp = url.replace("detail", "down");
				page.addTargetRequest(urltmp);
			}
			
		}

		//提取页面信息
		if(page.getUrl().regex("http://download.pchome.net/mobile.*").match()){
	
			
			Apk apk = Pchome_Detail.getApkDetail(page);
			
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
	
	private static List<String> getNextUrl(String str){
       	List<String> tmp = new ArrayList<String>();
		String regex="<a href=\"([^\"]+)\" target=\"_self\">";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        while(matcher.find()){        	
        	tmp.add(matcher.group(1).toString());      	
        }
    	return tmp;   	
    }
	
}
