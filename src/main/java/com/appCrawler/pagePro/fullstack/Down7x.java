package com.appCrawler.pagePro.fullstack;


import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appCrawler.pagePro.apkDetails.Aawap_Detail;
import com.appCrawler.pagePro.apkDetails.Down7x_Detail;
import com.appCrawler.utils.PropertiesUtil;
import com.google.common.collect.Sets;

import us.codecraft.webmagic.Apk;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.PageProUrlFilter;
/**
 * 7喜  http://www.7xdown.com/downlist/r_9_1.html
 * Down7x #212
 * @author DMT
 */


public class Down7x implements PageProcessor{

	Site site = Site.me().setCharset("gb2312").setRetryTimes(PropertiesUtil.getRetryTimes()).
			setSleepTime(PropertiesUtil.getInterval());

	private Logger LOGGER = LoggerFactory.getLogger(Apk3.class);

	public Apk process(Page page) {
	   if("http://www.7xdown.com/".equals(page.getUrl().toString()))
	   {
		   page.addTargetRequest("http://www.7xdown.com/downlist/r_9_1.html");
		   return null;
	   }
	   if(page.getUrl().regex("http://www\\.7xdown\\.com/downlist/r_9_\\d+\\.html").match())
	   {
		   List<String> apks=page.getHtml().xpath("//ul[@class='ul-down']/li/a/@href").all();
		   List<String> pages=page.getHtml().xpath("//div[@class='pages pages3']/table/tbody/tr/td[2]/a/@href").all();
		   apks.addAll(pages);
		   for(String url:apks)
		   {
			   if(PageProUrlFilter.isUrlReasonable(url))
			   {
				   page.addTargetRequest(url);
			   }
		   }
	   }
//		if(info!=null)
//		{
//		nexturl=getLastUrl(info);		
//		System.out.println(nexturl);
//		}
//		Set<String> cacheSet = Sets.newHashSet();
//		if(nexturl!=null){
//			cacheSet.add(nexturl);
//		}
//		
//			cacheSet.addAll(urls);
//		
	
//		for(String url : cacheSet){
//			if(PageProUrlFilter.isUrlReasonable(url)){
//				page.addTargetRequest(url);
//			}
//		}
		
	
		//提取页面信息
		if(page.getUrl().regex("http://www\\.7xdown\\.com/downinfo/\\d+\\.html").match()){
	
			
			Apk apk = Down7x_Detail.getApkDetail(page);
			
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
    	//href="http://www.7xdown.com/downlist/r_9_2.html"><font face="webdings" color="red" title="Next Page">
    	String regex="<a href=\"([^\"]*)\"><font face=\"webdings\" color=\"red\" title=\"Next Page\"";
		Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(str); 
        if(matcher.find()){        	
        	tmp=matcher.group(1).toString();      	
        }
    	return tmp;   	
    }
}
