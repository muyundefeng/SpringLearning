package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Androidmi_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;

/**
 * Androidmi http://www.Androidmi.com/
 * Androidmi #204
 * @author DMT
 * 
 */

public class Androidmi implements PageProcessor{

	Site site = Site.me().setCharset("utf-8").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());
	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);
	public Apk process(Page page) {
		if("http://www.androidmi.com/".equals(page.getUrl().toString()))
		{
			page.addTargetRequest("http://www.androidmi.com/games/index.html");
			return null;
		}
	    String nexturl=null;
		//System.out.println(page.getHtml().toString());
		List<String> urls =page.getHtml().xpath("//div[@class='block']///p[1]/a/@href").all() ;		
		nexturl=page.getHtml().xpath("//div[@class='text-c mg_t20']").toString();
		if(nexturl!=null){
			nexturl=getLastUrl(nexturl);
		}
		
		Set<String> cacheSet = Sets.newHashSet();
		if(page.getUrl().toString().equals("http://www.androidmi.com/games/index.html")){
			cacheSet.add("http://www.androidmi.com/apps/index.html");
			cacheSet.add("http://www.androidmi.com/html/Theme/");
		}
		if(nexturl!=null){
		cacheSet.add(nexturl);
		}
		
		cacheSet.addAll(urls);
		
		for(String url : cacheSet){
			if(PageProUrlFilter.isUrlReasonable(url)){
				page.addTargetRequest(url);
			}
		}
		
	
		//提取页面信息  http://www.androidmi.com/android-98-9233/
		if(page.getUrl().regex("http://www\\.androidmi\\.com/android-.*").match() &&
		 !page.getUrl().regex("http://www\\.androidmi\\.com/android-25.*").match()	){			
			Apk apk = Androidmi_Detail.getApkDetail(page);		
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
	
	private static String getLastUrl(String str){
    	String tmp=null;    	
    	//href="/games/2.html" class="a1">下一页</a></div>
		String regex="href=\"([^\"]+)\" class=\"a1\".*>下一页</a>";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
	
}
